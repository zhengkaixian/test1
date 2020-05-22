package com.zero.base.pojo.database;

/**
 * @ClassName: DbType
 * @Description: 根据数据库类型进行驱动适配
 * @author:
 * @date: 2019年11月28日 下午4:14:04
 */
public enum DbType {

	/**
	 * @Fields db2
	 */
	db2("com.ibm.db2.jcc.DB2Driver", "jdbc:db2://{ip}:{port}/{sid}"),

	/**
	 * @Fields sqlservernative
	 */
	sqlservernative("com.microsoft.sqlserver.jdbc.SQLServerDriver", ""),

	/**
	 * @Fields sqlserver SQLSERVER数据库
	 */
	sqlserver("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://{ip}:{port};DatabaseName={sid}"),

	/**
	 * @Fields mysql MYSQL数据库
	 */
	mysql("com.mysql.jdbc.Driver", "jdbc:mysql://{ip}:{port}/{sid}?useUnicode=true&characterEncoding=UTF-8"),

	/**
	 * @Fields oracle9~12
	 */
	oracle("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@{ip}:{port}/{sid}"),

	/**
	 * @Fields oraclerdb
	 */
	oraclerdb("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@{ip}:{port}:{sid}"),

	/**
	 * @Fields sapdb
	 */
	sapdb("org.pentaho.di.core.database.SAPDBDatabaseMeta", ""),

	/**
	 * @Fields sapr3
	 */
	sapr3("org.pentaho.di.core.database.SAPR3DatabaseMeta", ""),

	/**
	 * @Fields sybase
	 */
	sybase("com.sybase.jdbc3.jdbc.SybDriver", ""),

	/**
	 * @Fields sybaseiq SYSBQASE数据库
	 */
	sybaseiq("com.sybase.jdbc3.jdbc.SybDriver", "jdbc:sybase:Tds:{ip}:{port}/{sid}"),

	/**
	 * @Fields dm 达梦数据库
	 */
	dm("dm.jdbc.driver.DmDriver", "jdbc:dm://{ip}:{port}/{sid}"),

	/**
	 * @Fields gbase数据库
	 */
	gbase("com.informix.jdbc.IfxDriver",
			"jdbc:informix-sqli://{ip}:{port}/{name}:INFORMIXSERVER={sid};{config}NEWCODESET=GB18030,GB18030-2000,5486;DBDATE=Y4MD"),

	/**
	 * @Fields gbase8a cluster数据库
	 */
	gbase8acluster("com.gbase.jdbc.Driver", "jdbc:gbase://{ip}:{port}/{sid}"),

	/**
	 * @Fields postgresql数据库
	 */
	postgresql("org.postgresql.Driver", "jdbc:postgresql://{ip}:{port}/{sid}"),

	/**
	 * @Fields kingbasees
	 */
	kingbasees("com.kingbase.Driver", "jdbc:kingbase://{ip}/{sid}"),

	hana("com.sap.db.jdbc.Driver", "jdbc:sap://{ip}:{port}?reconnect=true"),

	hive("org.apache.hive.jdbc.HiveDriver", "jdbc:hive2://{ip}:{port}/{sid}");

	/**
	 * @Fields driverName : 驱动名
	 */
	private String driverName;

	/**
	 * @Fields connectString : 连接字
	 */
	private String connectString;

	private DbType(String driverName, String connectString) {
		this.driverName = driverName;
		this.connectString = connectString;
	}

	public String getDriverName() {
		return driverName;
	}

	public String getConnectString() {
		return connectString;
	}

}
