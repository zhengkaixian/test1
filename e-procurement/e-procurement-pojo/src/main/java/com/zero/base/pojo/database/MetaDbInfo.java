package com.zero.base.pojo.database;

import java.io.Serializable;
import java.sql.Connection;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
* @ClassName: MataDbIno
* @Description: 数据库连接基本信息
* @author 
* @date 2016-10-8 上午10:53:57
*
*/
public class MetaDbInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1237293134895162532L;

	/**
	* @Fields dbType : 数据库类型
	*/ 
	private String dbType;
	
	/**
	* @Fields dbIp : 数据库IP
	*/ 
	private String dbIp;
	
	/**
	* @Fields dbPort : 数据库端口
	*/ 
	private String dbPort;
	
	/**
	* @Fields dbName : 数据库名称
	*/ 
	private String dbName;
	
	/**
	* @Fields dbSchema : 某些数据库需要指定schema，例如db2，dm
	*/ 
	private String dbSchema;
	
	/**
	* @Fields dbUsername : 用户名称
	*/ 
	private String dbUsername;
	
	/**
	* @Fields dbPass : 密码
	*/ 
	private String dbPass;
	
	/**
	* @Fields dbSid :实例名称
	*/ 
	private String dbSid;
	
	/**
	* @Fields cName : 中文名(gbase使用)
	*/ 
	private String cName;
	
	/**
	* @Fields desc : 描述(gbase使用)
	*/ 
	private String desc;
	
	/**
	* @Fields isCluster : 是否集群
	*/ 
	private String isCluster;
	
	/**
	* @Fields connStr : 集群模式的连接串
	*/ 
	private String connStr;
	
	
	/**
	* @Fields conn : 数据库连接
	*/ 
	@JsonIgnore
	private Connection conn;

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbIp() {
		return dbIp;
	}

	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbSchema() {
		return dbSchema;
	}

	public void setDbSchema(String dbSchema) {
		this.dbSchema = dbSchema;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public String getDbPass() {
		return dbPass;
	}

	public void setDbPass(String dbPass) {
		this.dbPass = dbPass;
	}

	public String getDbSid() {
		return dbSid;
	}

	public void setDbSid(String dbSid) {
		this.dbSid = dbSid;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIsCluster() {
		return isCluster;
	}

	public void setIsCluster(String isCluster) {
		this.isCluster = isCluster;
	}

	public String getConnStr() {
		return connStr;
	}

	public void setConnStr(String connStr) {
		this.connStr = connStr;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/* (非 Javadoc)
	* <p>Title: toString</p>
	* <p>Description: </p>
	* @return
	* @see java.lang.Object#toString()
	*/
		
	@Override
	public String toString() {
		return "MetaDbInfo [dbType=" + dbType + ", dbIp=" + dbIp + ", dbPort=" + dbPort + ", dbName=" + dbName
				+ ", dbSchema=" + dbSchema + ", dbUsername=" + dbUsername + ", dbPass=" + dbPass + ", dbSid=" + dbSid
				+ ", cName=" + cName + ", desc=" + desc + ", isCluster=" + isCluster + ", connStr=" + connStr
				+ ", conn=" + conn + "]";
	}

}
