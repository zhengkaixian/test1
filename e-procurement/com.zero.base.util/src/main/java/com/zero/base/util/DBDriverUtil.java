package com.zero.base.util;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: DBDriverUtil
 * @Description: 加载数据库驱动
 * @author:
 * @date: 2018年7月18日 上午10:39:11
 */

public class DBDriverUtil {

	private static final Logger logger = LoggerFactory.getLogger(DBDriverUtil.class);

	/**
	 * 
	 * @Title: loadDriver
	 * @Description: 动态加载数据库驱动
	 * @param driverName
	 * @throws SQLException
	 */
	public static void loadDriver(String driverName) throws SQLException {
		switch (driverName) {
//			case "com.ibm.db2.jcc.DB2Driver": 
//				DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver()); 
//				break;
		case "com.microsoft.sqlserver.jdbc.SQLServerDriver":
			logger.error("没有找到对应的数据库驱动");
			break;
		case "com.mysql.jdbc.Driver":
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			break;
//			case "oracle.jdbc.driver.OracleDriver": 
//				DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()); 
//				break;
		case "org.pentaho.di.core.database.SAPDBDatabaseMeta":
			logger.error("没有找到对应的数据库驱动");
			break;
		case "com.sybase.jdbc3.jdbc.SybDriver":
			logger.error("没有找到对应的数据库驱动");
			break;
		case "dm.jdbc.driver.DmDriver":
			logger.error("没有找到对应的数据库驱动");
			// DriverManager.registerDriver(new dm.jdbc.driver.DmDriver());
			break;
		case "com.informix.jdbc.IfxDriver":
			logger.error("没有找到对应的数据库驱动");
			break;
//			case "org.postgresql.Driver": 
//				DriverManager.registerDriver(new org.postgresql.Driver()); 
//				break;
//			case "com.gbase.jdbc.Driver": 
//				DriverManager.registerDriver(new com.gbase.jdbc.Driver()); 
//				break;
//			case "com.kingbase.Driver": 
//				DriverManager.registerDriver(new com.kingbase.Driver()); 
//				DriverManager.deregisterDriver(new dm.jdbc.driver.DmDriver());
//				break;
		default:
			logger.error("没有找到对应的数据库驱动");
			break;
		}
	}

}
