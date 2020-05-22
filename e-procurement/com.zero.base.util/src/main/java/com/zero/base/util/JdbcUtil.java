package com.zero.base.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zero.base.pojo.database.DbType;
import com.zero.base.pojo.database.MetaDbInfo;
import com.zero.base.pojo.database.MetadataConstant;
import com.zero.base.pojo.database.SampleDataDto;

/**
 * @ClassName: JdbcUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author
 * @date 2016-12-12 下午2:22:42
 */
public class JdbcUtil {

	private static final Logger logger = LoggerFactory.getLogger(JdbcUtil.class);

	/**
	 * @Title: @Description: 根据不同数据库链接获取不同的数据库连接对象 @param @param
	 * dbInfo @param @param extract @param @return @param @throws Exception
	 * 参数说明 @return Connection 返回类型 @throws
	 */
	public static Connection getConnection(MetaDbInfo dbInfo) throws Exception {
		String driverName = null;
		String strConn = null;
		Connection conn = null;
		if (dbInfo != null) {
			if (dbInfo.getConn() == null) {
				logger.warn(dbInfo.toString());// 打印数据库连接信息
				if (dbInfo == null || dbInfo.getDbType() == null) {
					logger.error("数据库类型为空");
					return null;
				}
				System.out.println("dbInfo.getDbType()" + dbInfo.getDbType() + "------");
				DbType dbType = JdbcUtil.matchDbType(dbInfo.getDbType());
				/*
				 * if(MetadataConstant.DATABASE_HIVE.equals(dbType.getDbType())){
				 * MetaReader.getHiveConfig(); }
				 */
				if (dbType == null) {
					return null;
				}
				driverName = dbType.getDriverName();
				if (MetadataConstant.IS_CLUSTER.equals(dbInfo.getIsCluster())) {
					strConn = dbInfo.getConnStr();
				} else {
					strConn = matchUrl(dbInfo.getcName(), dbInfo.getDbType(), dbInfo.getDbIp(), dbInfo.getDbPort(),
							dbInfo.getDbSid(), dbInfo.getDesc());
				}
				String dbPass = dbInfo.getDbPass();

				if (StringUtils.isEmpty(dbPass)) {
					throw new Exception("密码为空");
				}
				DBDriverUtil.loadDriver(driverName);
				DriverManager.setLoginTimeout(5);
				conn = DriverManager.getConnection(strConn, dbInfo.getDbUsername(), dbInfo.getDbPass().trim());
			} else {
				conn = dbInfo.getConn();
				dbInfo.setDbType(getDbTypeByConn(conn));
			}
		}
		return conn;
	}

	public static DynamicDataSourcePool getConnectionPool(MetaDbInfo dbInfo) throws Exception {
		DynamicDataSourcePool dyPool = null;
		DbType dbType = matchDbType(dbInfo.getDbType());
		String driverName = dbType.getDriverName();
		String strConn;
		if (MetadataConstant.IS_CLUSTER.equals(dbInfo.getIsCluster())) {
			strConn = dbInfo.getConnStr();
		} else {
			strConn = matchUrl(dbInfo.getcName(), dbInfo.getDbType(), dbInfo.getDbIp(), dbInfo.getDbPort(),
					dbInfo.getDbSid(), dbInfo.getDesc());
		}
		dyPool = new DynamicDataSourcePool(driverName, strConn, dbInfo.getDbPass(), dbInfo.getDbUsername(),
				dbInfo.getDbType());
		return dyPool;
	}

	public static String getDbTypeByConn(Connection conn) throws SQLException {
		DatabaseMetaData dbmd;
		try {
			dbmd = conn.getMetaData();
			String dbName = dbmd.getDatabaseProductName();
			if ("MySQL".equals(dbName)) {
				return MetadataConstant.DATABASE_MYSQL;
			} else if ("Oracle".equals(dbName)) {
				return MetadataConstant.DATABASE_ORACLE;
			} else if ("PostgreSQL".equals(dbName)) {
				return MetadataConstant.DATABASE_POSTGRESQL;
			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new SQLException("获取数据库类型失败！", e);
		}
	}

	/**
	 * 返回匹配出的url
	 * 
	 * @param type 数据库类型
	 * @param ip   数据库ip或主机名
	 * @param host 端口号
	 * @param sid  数据库sid
	 * @return
	 */
	public static String matchUrl(String dbName, String type, String ip, String port, String sid, String desc) {
		String url = matchDbType(type).getConnectString();
		url = url.replace("{ip}", ip).replace("{port}", port);
		if (!StringUtils.isEmpty(port)) {
			if (type.contains(MetadataConstant.DATABASE_MYSQL)
					|| type.contains(MetadataConstant.DATABASE_GBASE8ACLUSTER)) {
				if (StringUtils.isBlank(sid)) {
					url = url.replace("{sid}", "");
					System.out.println("mysql数据库实例可以 为空");
				} else {
					url = url.replace("{sid}", sid);
				}
			} else {
				url = url.replace("{sid}", sid);
			}
			if (MetadataConstant.DATABASE_GBASE.equals(type)) {
				url = url.replace("{name}", dbName).replace("{config}", desc);
			}
			// 处理Mysql时间字段默认为0000-00-00 00:00:00
			if (MetadataConstant.DATABASE_MYSQL.equals(type)) {
				url = new StringBuilder(url).append("&zeroDateTimeBehavior=convertToNull").toString();
			}
		} else {
			url = url.split("@")[0] + "@" + ip;
		}
		return url;
	}

	/**
	 * 
	 * @Title: queryData @Description:
	 * 分页查询数据集，按前端要求组装字段集合columns、数据集合rows @param @param dbType @param @param
	 * sql @param @param row @param @param conn @param @return @param @throws
	 * Exception 参数说明 @return Map<String,Object> 返回类型 @throws
	 */
	@SuppressWarnings("resource")
	public static SampleDataDto queryData(SampleDataDto sampleDataDto, String dbType, String sql, int pageSize,
			int pageNo, Connection conn) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (StringUtil.isEmpty(sql)) {
			returnMap.put("columns", new ArrayList<String>());
			returnMap.put("rows", new ArrayList<Map<String, Object>>());
			returnMap.put("total", 0);
			return sampleDataDto;
		}
		// 获取数据库信息
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rstotal = null;
		String sqltotal = "";
		int sqlType = 1;// 默认Mysql
		int total = 0;
		try {
			if (pageSize != 0) {
				if (dbType.equals("04") || dbType.equals("13") || dbType.equals("12")) {// Mysql,gbase
					sqltotal = new StringBuilder("select count(1) total from (").append(pageSql(sql, dbType, 0, 0))
							.append(")" + " " + "as" + " " + "aa").toString();
				} else {
					sqltotal = new StringBuilder("select count(1) total from (").append(pageSql(sql, dbType, 0, 0))
							.append(")").toString();
					sqlType = 2;
				}
				ps = conn.prepareStatement(sqltotal);
				ps.setQueryTimeout(60);// 查询1分钟超时
				rstotal = ps.executeQuery();
				rstotal.next();
				rstotal.getInt("total");
				total = rstotal.getInt("total");
				// row.setTotal(total);
			}
			sql = pageSql(sql, dbType.toString(), pageSize, pageNo);// 分页处理后的语句
			ps = conn.prepareStatement(sql);
			// 设置参数
			ps.setQueryTimeout(60);// 查询1分钟超时
			rs = ps.executeQuery();
			// 列数
			int columnCount = rs.getMetaData().getColumnCount();
			List<String> topList = new ArrayList<String>();
			for (int i = sqlType; i <= columnCount; i++) {
				topList.add(rs.getMetaData().getColumnName(i).toUpperCase());
			}
			List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateTimeSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while (rs.next()) {
				Map<String, Object> rowValues = new HashMap<String, Object>();
				int j = 0;
				for (int i = sqlType; i <= columnCount; i++) {// 从位置2开始，不处理分页字段数据
					if (rs.getObject(i) instanceof java.sql.Date) {// update by khq
						java.sql.Date date = rs.getDate(i);
						rowValues.put(topList.get(j).toUpperCase(), date == null ? "" : sdf.format(date));
					} else if (rs.getObject(i) instanceof java.sql.Timestamp) {
						java.sql.Timestamp date = rs.getTimestamp(i);
						rowValues.put(topList.get(j).toUpperCase(), date == null ? "" : dateTimeSdf.format(date));
					} else {
						rowValues.put(topList.get(j).toUpperCase(), rs.getString(i));
					}
					j++;
				}
				rowList.add(rowValues);
			}
			returnMap.put("columns", topList);// 组装字段集合
			returnMap.put("rows", rowList);// 组装数据
			returnMap.put("total", total == 0 ? rowList.size() : total);
			sampleDataDto.setDatas(returnMap);
		} catch (Exception e) {
			logger.error("获取SQL结果集异常", e);
			throw new RuntimeException(e);
		} finally {
			try {
				if (pageSize != 0) {
					closeResultSet(rstotal);
				}
				closeDbObject(conn, ps, rs);
			} catch (Exception e) {
				logger.error("关闭数据库连接异常：", e);
			}

		}

		return sampleDataDto;
	}

	public static void closeDbObject(Connection connection, Statement statement, ResultSet resultSet)
			throws SQLException {
		closeResultSet(resultSet);
		closeStatement(statement);
		closeConnection(connection);
	}

	public static void closeResultSet(ResultSet resultSet) throws SQLException {
		if (resultSet != null) {
			// 关闭语句
			closeStatement(resultSet.getStatement());
			resultSet.close();
		}
	}

	public static void closeStatement(Statement statement) throws SQLException {
		if (statement != null) {
			statement.close();
		}
	}

	public static void closeConnection(Connection connection) throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

	public static String pageSql(String sql, String type, int pageSize, int pageNo) throws SQLException {
		if (pageSize != 0) {// 分页处理
			Integer startRowNum = pageNo, endRowNum = pageSize + pageNo;
			if (type.equals("05")) {
				StringBuffer startWhereSql = new StringBuffer();
				StringBuffer endWhereSql = new StringBuffer();
				if (startRowNum != null) {
					startWhereSql.append(" rn > " + startRowNum.intValue());
				}
				if (endRowNum != null) {
					endWhereSql.append(" rownum <=" + endRowNum.intValue());
				}
				if (endWhereSql.length() > 0) {
					sql = "select rownum rn,a.* from (" + sql + " ) a where " + endWhereSql.toString();
				}
				if (startWhereSql.length() > 0) {
					sql = "select * from (" + sql + " )  where " + startWhereSql.toString();
				}
			} else if (type.equals("01")) {
				int rows = (pageNo > 0 ? pageNo : 0) + pageSize;
				sql = "select * from (select row_number() over() as r," + sql.replaceFirst("select", "")
						+ ") where r between " + (pageNo > 0 ? pageNo : 0) + " AND " + rows;
				// 无分页
				// sql = sql + " fetch first "+row.getFetchSize()+"10 rows only";
			} else {
				sql = sql + " LIMIT " + pageSize + " OFFSET " + (pageNo > 0 ? pageNo : 0);
			}
		}
		return sql;
	}

	/**
	 * 关闭数据库链接（通用）
	 * 
	 * @param rs   记录集ResultSet
	 * @param conn 数据库链接Connection
	 */
	public static void closeDb(ResultSet rs, Connection conn, Statement st) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("释放资源失败", e);
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				logger.error("释放资源失败", e);
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("释放资源失败", e);
			}
		}
	}

	/**
	 * @Title: matchDbType @Description: 根据数据类型匹配数据库枚举 @param @param
	 * dbType @param @return 参数说明 @return DbType 返回类型 @throws
	 */
	public static DbType matchDbType(String dbType) {
		if (MetadataConstant.DATABASE_MYSQL.equals(dbType)) {
			return DbType.mysql;
		} else if (MetadataConstant.DATABASE_ORACLE9I.equals(dbType)) {
			return DbType.oracle;
		} else if (MetadataConstant.DATABASE_ORACLE.equals(dbType)) {
			return DbType.oracle;
		} else if (MetadataConstant.DATABASE_POSTGRESQL.equals(dbType)) {
			return DbType.postgresql;
		} else if (MetadataConstant.DATABASE_DB2.equals(dbType)) {
			return DbType.db2;
		} else if (MetadataConstant.DATABASE_DM.equals(dbType)) {
			return DbType.dm;
		} else if (MetadataConstant.DATABASE_SYBASEIQ.equals(dbType)) {
			return DbType.sybaseiq;
		} else if (MetadataConstant.DATABASE_GBASE.equals(dbType)) {
			return DbType.gbase;
		} else if (MetadataConstant.DATABASE_GBASE8ACLUSTER.equals(dbType)) {
			return DbType.gbase8acluster;
		} else if (MetadataConstant.DATABASE_KINGBASEES.equals(dbType)) {
			return DbType.kingbasees;
		} else if (MetadataConstant.DATABASE_HIVE.equals(dbType)) {
			return DbType.hive;
		} else if (MetadataConstant.DATABASE_SGRDB.equals(dbType)) {
			return DbType.mysql;
		} else if (MetadataConstant.DATABASE_HANA.equals(dbType)) {
			return DbType.hana;
		}
		return null;
	}

	/**
	 * 返回根据字段类型代码匹配出的字段类型名
	 * 
	 * @param typeCode 字段类型代码
	 * @return 根据字段类型代码匹配出的字段类型名
	 * @see Types
	 * @see DbContats#matchSqlType(int)
	 */
	public static String matchColumnTypeName(int typeCode) {
		switch (typeCode) {
		case Types.BIT:
			return "BIT";
		case Types.TINYINT:
			return "TINYINT";
		case Types.SMALLINT:
			return "SMALLINT";
		case Types.INTEGER:
			return "INTEGER";
		case Types.BIGINT:
			return "BIGINT";
		case Types.FLOAT:
			return "FLOAT";
		case Types.REAL:
			return "REAL";
		case Types.DOUBLE:
			return "DOUBLE";
		case Types.NUMERIC:
			return "NUMERIC";
		case Types.DECIMAL:
			return "DECIMAL";
		case Types.CHAR:
			return "CHAR";
		case Types.VARCHAR:
			return "VARCHAR";
		case Types.LONGVARCHAR:
			return "LONGVARCHAR";
		case Types.DATE:
			return "DATE";
		case Types.TIME:
			return "TIME";
		case Types.TIMESTAMP:
			return "TIMESTAMP";
		case Types.BINARY:
			return "BINARY";
		case Types.VARBINARY:
			return "VARBINARY";
		case Types.LONGVARBINARY:
			return "LONGVARBINARY";
		case Types.NULL:
			return "NULL";
		case Types.OTHER:
			return "OTHER";
		case Types.JAVA_OBJECT:
			return "JAVA_OBJECT";
		case Types.DISTINCT:
			return "DISTINCT";
		case Types.STRUCT:
			return "STRUCT";
		case Types.ARRAY:
			return "ARRAY";
		case Types.BLOB:
			return "BLOB";
		case Types.CLOB:
			return "CLOB";
		case Types.REF:
			return "REF";
		case Types.DATALINK:
			return "DATALINK";
		case Types.BOOLEAN:
			return "BOOLEAN";
		}
		return "UNKNOWN";
	}

	/**
	 * @Title: matchIndexTypeName @Description: TODO(这里用一句话描述这个方法的作用) @param @param
	 * typeCode @param @return 参数说明 @return String 返回类型 @throws
	 */
	public static String matchIndexTypeName(int typeCode) {
		switch (typeCode) {
		case DatabaseMetaData.tableIndexStatistic:
			return "STATISTIC";
		case DatabaseMetaData.tableIndexClustered:
			return "CLUSTERED";
		case DatabaseMetaData.tableIndexHashed:
			return "HASHED";
		case DatabaseMetaData.tableIndexOther:
			return "OTHER";
		}
		return "UNKNOWN";
	}

}
