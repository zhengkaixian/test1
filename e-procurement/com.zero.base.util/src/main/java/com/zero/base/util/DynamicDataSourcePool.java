package com.zero.base.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zero.base.pojo.database.MetadataConstant;


/**
 * @ClassName: DynamicDataSourcePool
 * @Description: 动态数据源池
 * @author
 * @date Aug 21, 2012 5:13:21 PM
 * 
 */
public class DynamicDataSourcePool {

	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourcePool.class);

	private BasicDataSource dataSourcePool = null;

	public BasicDataSource getDataSourcePool() {
		return dataSourcePool;
	}

	public void setDataSourcePool(BasicDataSource dataSourcePool) {
		this.dataSourcePool = dataSourcePool;
	}

	/**
	 * 
	 * <p>
	 * Title: 默认数据源池创建方法
	 * </p>
	 * <p>
	 * Description: 默认数据源池创建方法
	 * </p>
	 * 
	 * @param username
	 * @param mmpd
	 * @param url
	 * @param driverClassName
	 * @throws Exception
	 */
	public DynamicDataSourcePool(String username, String mmpd, String url, String driverClassName, int initialSize,
			int maxActive, int maxIdle, int minIdle) throws Exception {
		try {
			this.dataSourcePool = new BasicDataSource();
			this.dataSourcePool.setUrl(url);
			this.dataSourcePool.setDriverClassName(driverClassName);
			this.dataSourcePool.setUsername(username);
			this.dataSourcePool.setPassword(mmpd.trim());
			this.dataSourcePool.setInitialSize(initialSize); // 初始的连接数；
			this.dataSourcePool.setMaxActive(maxActive); // 最大激活连接数
			this.dataSourcePool.setMaxIdle(maxIdle); // 最大闲置连接数
			this.dataSourcePool.setMinIdle(minIdle); // 获得连接的最大等待毫秒数
			this.dataSourcePool.setLoginTimeout(1000);
			this.dataSourcePool.setTimeBetweenEvictionRunsMillis(20000); // 在空闲连接回收器线程运行期间休眠的时间值，以毫秒为单位
			this.dataSourcePool.setMinEvictableIdleTimeMillis(1000 * 60 * 30);
			this.dataSourcePool.setNumTestsPerEvictionRun(3); //
			this.dataSourcePool.setTestWhileIdle(true);

			logger.info("初始化数据源池完成");

		} catch (Exception e) {
			logger.error("初始化数据源池异常", e);
			throw e;
		}
	}

	/**
	 * <p>
	 * Title: 构造函数-创建数据池
	 * </p>
	 * <p>
	 * Description: 根据DBConfig创建数据源池
	 * </p>
	 * 
	 * @param dbInfo
	 * @throws Exception
	 */
	public DynamicDataSourcePool(String driverClassName, String url, String mmpd, String username, String dbType)
			throws Exception {

		// TODO 为处理数据源集群的链接配置信息
		try {
			String validationQuery = null;
			if (MetadataConstant.DATABASE_ORACLE.equals(dbType) || MetadataConstant.DATABASE_ORACLE9I.equals(dbType)) {
				validationQuery = "select 1 from dual";
			} else if (MetadataConstant.DATABASE_MYSQL.equals(dbType)) {
				validationQuery = "select 1";
			} else if (MetadataConstant.DATABASE_POSTGRESQL.equals(dbType)) {
				validationQuery = "select version()";
			}
			this.dataSourcePool = new BasicDataSource();
			this.dataSourcePool.setUrl(url);
			this.dataSourcePool.setDriverClassName(driverClassName);
			this.dataSourcePool.setUsername(username);
			this.dataSourcePool.setPassword(mmpd.trim());
			// 初始化10的时候sybaseIQ会报错
			this.dataSourcePool.setInitialSize(9);
			// 改为动态获取 edit by songbaifan 20121006
//			if (dbInfo.getInitialSize() == 0) {
//				this.dataSourcePool.setInitialSize(10);
//			} else {
//				this.dataSourcePool.setInitialSize(dbInfo.getInitialSize()); // 初始的连接数；
//			}
			this.dataSourcePool.setMaxActive(9); // 最大激活连接数
			this.dataSourcePool.setMaxIdle(9); // 最大闲置连接数
			this.dataSourcePool.setMaxWait(60000); // 获得连接的最大等待毫秒数

//			this.dataSourcePool.setMinIdle(dbInfo.getMinIdle()); // 最小空闲连接数
			this.dataSourcePool.setRemoveAbandoned(true); // 是否自动回收超 时连接
			this.dataSourcePool.setRemoveAbandonedTimeout(600); // 超时事件（以秒数为单位）
			this.dataSourcePool.setTimeBetweenEvictionRunsMillis(20000); // 在空闲连接回收器线程运行期间休眠的时间值，以毫秒为单位
			this.dataSourcePool.setMinEvictableIdleTimeMillis(1000 * 60 * 30);
			this.dataSourcePool.setTestOnReturn(true);
			this.dataSourcePool.setTestOnBorrow(true);
			if (validationQuery != null) {
				this.dataSourcePool.setValidationQuery(validationQuery);// 和testOnBorrow属性控制：取连接时，连接无效则取另一个。
			}
			this.dataSourcePool.setNumTestsPerEvictionRun(3); //
			this.dataSourcePool.setTestWhileIdle(true);
			// this.dataSourcePool.setLoginTimeout(1000);
			// 将动态连接池信息赋值 added by songbaifan 20121010
			this.setDataSourcePool(dataSourcePool);

			logger.info("初始化数据源池完成");

		} catch (Exception e) {
			logger.error("初始化数据源池异常", e);
			throw e;
		}

	}

	/**
	 * 得到连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public synchronized Connection getConnection() throws SQLException {
		return this.dataSourcePool.getConnection();
	}

	/**
	 * 关闭
	 */
	public void destroy() {
		if (null != this.dataSourcePool) {
			try {
				this.dataSourcePool.close();
			} catch (SQLException e) {
				logger.error("关闭数据源池异常", e);
			}
		}
	}

}
