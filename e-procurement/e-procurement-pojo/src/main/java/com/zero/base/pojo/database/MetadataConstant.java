package com.zero.base.pojo.database;

/**
 * @ClassName: MetadataConstant
 * @Description: 数据库类型定义
 * @author:
 * @date: 2018年11月28日 下午4:14:04
 */
public class MetadataConstant {

	/**
	 * @fieldName: ALL_OBJECT_TYPES
	 * @fieldType: String[]
	 * @Description: 默认表、视图、物化视图、同义词
	 */
	public static final String[] ALL_OBJECT_TYPES = { "TABLE", "VIEW", "MVIEW", "SYNONYM" };

	public static final String[] ALL_ROUTINE_TYPES = { "PROCEDURE", "FUNCTION" };

	public static final String[] ALL_PROPERTY_TYPES = { "COLUMN", "INDEX", "PARTITION" };

	/**
	 * 使用jdbc的DatabaseMetaData接口抽取
	 */
	public static final String JDBC_METADATA_EXTRACT = "jdbcMetadataExtract";

	/**
	 * @Fields DATABASE_DB2 :db2
	 */
	public static final String DATABASE_DB2 = "01";

	/**
	 * @Fields DATABASE_MYSQL : mysql
	 */
	public static final String DATABASE_MYSQL = "04";

	/**
	 * @Fields DATABASE_ORACLE9I : oracle9i
	 */
	public static final String DATABASE_ORACLE9I = "05";

	/**
	 * @Fields DATABASE_ORACLE : oracle10~12
	 */
	public static final String DATABASE_ORACLE = "06";

	/**
	 * @Fields DATABASE_SYBASEIQ : SybaseIq
	 */
	public static final String DATABASE_SYBASEIQ = "10";

	/**
	 * @Fields DATABASE_DM :dm
	 */
	public static final String DATABASE_DM = "11";

	/**
	 * @Fields DATABASE_GBASE : Gbase
	 */
	public static final String DATABASE_GBASE = "12";

	/**
	 * @Fields DATABASE_POSTGRESQL : postgre
	 */
	public static final String DATABASE_POSTGRESQL = "13";

	/**
	 * @Fields DATABASE_HIVE : hive
	 */
	public static final String DATABASE_HIVE = "14";

	/**
	 * @Fields DATABASE_ORACLERDB : oraclerdb
	 */
	public static final String DATABASE_ORACLERDB = "15";

	/**
	 * @Fields DATABASE_KINGBASEES :KingbaseES
	 */
	public static final String DATABASE_KINGBASEES = "16";

	/**
	 * @Fields DATABASE_GBASE8ACLUSTER : Gbase8A MPP Cluster
	 */
	public static final String DATABASE_GBASE8ACLUSTER = "17";

	public static final String DATABASE_SQLSERVERNATIVE = "02";

	public static final String DATABASE_SQLSERVER = "03";

	public static final String DATABASE_SAPDB = "07";

	public static final String DATABASE_SAPR3 = "08";

	public static final String DATABASE_SYBASE = "09";

	public static final String DATABASE_ODPS = "20";

	public static final String DATABASE_SGRDB = "21";

	public static final String DATABASE_HBASE = "22";

	public static final String DATABASE_HDFS = "23";

	public static final String DATABASE_HANA = "24";

	/**
	 * db级别类型
	 */
	/**
	 * @Fields TOTALSPACE : 总空间
	 */
	public static final String DB_TOTALSPACE = "TOTALSPACE";

	/**
	 * @Fields DB_USEDSPACE : 已用空间
	 */
	public static final String DB_USEDSPACE = "USEDSPACE";

	/**
	 * @Fields USER_TOTALRECORD : 用户总记录数
	 */
	public static final String USER_TOTALRECORD = "TOTAL_RECORD";

	/**
	 * @fieldName: USER_TOTALDATASIZE
	 * @fieldType: String
	 * @Description: 用户总数据量
	 */
	public static final String USER_TOTALDATASIZE = "TOTAL_DATASIZE";

	/**
	 * ObjectType 对象类型
	 */
	/**
	 * @Fields USER : 用户
	 */
	public static final String SCHEMA = "SCHEMA";
	/**
	 * @Fields TABLE : 表
	 */
	public static final String TABLE = "TABLE";
	/**
	 * @Fields VIEW : 视图
	 */
	public static final String VIEW = "VIEW";
	/**
	 * @Fields SYNONYM :同义词
	 */
	public static final String SYNONYM = "SYNONYM";
	/**
	 * @Fields MVIEW : 物化视图
	 */
	public static final String MVIEW = "MVIEW";

	/**
	 * @Fields PROCEDURE : 存储过程
	 */
	public static final String PROCEDURE = "PROCEDURE";

	/**
	 * @Fields FUNCTION : 函数
	 */
	public static final String FUNCTION = "FUNCTION";

	/**
	 * propertyType 属性类型
	 */
	/**
	 * @Fields COLUMN : 字段
	 */
	public static final String COLUMN = "COLUMN";
	/**
	 * @Fields PARTITION : 分区
	 */
	public static final String PARTITION = "PARTITION";
	/**
	 * @Fields INDEX : 索引
	 */
	public static final String INDEX = "INDEX";

	/**
	 * privType 权限类型
	 */
	/**
	 * @Fields PRIVS_SELECT : 对对象的查询权限
	 */
	public static final String PRIVS_SELECT = "SELECT";

	/**
	 * @Fields PRIVS_SELECT_ANY : 权限类型-查询所有对象
	 */
	public static final String PRIVS_SELECT_ANY = "1";

	/**
	 * @Fields PRIVS_SELECT_PART : 权限类型-查询部分对象
	 */
	public static final String PRIVS_SELECT_PART = "0";

	/**
	 * @Fields PRIVS_UPDATE : 对对象的修改权限
	 */
	public static final String PRIVS_UPDATE = "UPDATE";
	/**
	 * @Fields PRIVS_ADD :对对象的添加权限
	 */
	public static final String PRIVS_ADD = "ADD";
	/**
	 * @Fields PRIVS_DELETE : 对对象的删除权限
	 */
	public static final String PRIVS_DELETE = "DELETE";

	/**
	 * @Fields IS_CLUSTER : 是集群
	 */
	public static final String IS_CLUSTER = "01";

	/**
	 * @Fields IS_NOT_CLUSTER : 非集群
	 */
	public static final String IS_NOT_CLUSTER = "02";

	/**
	 * @Fields DBA_VIEW : 通过DBA视图抽取
	 */
	public static final String DBA_VIEW = "0";
	/**
	 * @Fields DBA_VIEW : 通过USER视图抽取
	 */
	public static final String USER_VIEW = "1";

	/**
	 * @Fields IDX_TYPE_STATISTIC : 索引类型-STATISTIC(JDBC标准)
	 */
	public static final String IDX_TYPE_STATISTIC = "STATISTIC";

	/**
	 * @Fields IDX_TYPE_CLUSTERED : 索引类型-CLUSTERED(JDBC标准)
	 */
	public static final String IDX_TYPE_CLUSTERED = "CLUSTERED";

	/**
	 * @Fields IDX_TYPE_HASHED : 索引类型-HASHED(JDBC标准)
	 */
	public static final String IDX_TYPE_HASHED = "HASHED";

	/**
	 * @Fields IDX_TYPE_OTHER : 索引类型-OTHER(JDBC标准)
	 */
	public static final String IDX_TYPE_OTHER = "OTHER";

	/**
	 * @Fields IDX_TYPE_NORMAL : 索引类型-Normal(ORACLE)
	 */
	public static final String IDX_TYPE_NORMAL = "NORMAL";

	/**
	 * @Fields IDX_TYPE_NORMAL : 索引类型-Normal(反向)(ORACLE)
	 */
	public static final String IDX_TYPE_NORMAL_REV = "NORMAL/REV";

	/**
	 * @Fields IDX_TYPE_UNIQUE : 索引类型-Unique(反向)(ORACLE)
	 */
	public static final String IDX_TYPE_UNIQUE_REV = "UNIQUE/REV";

	/**
	 * @Fields IDX_TYPE_UNIQUE : 索引类型-Unique(ORACLE)
	 */
	public static final String IDX_TYPE_UNIQUE = "UNIQUE";

	/**
	 * @Fields IDX_TYPE_BITMAP : 索引类型-Bitmap(ORACLE)
	 */
	public static final String IDX_TYPE_BITMAP = "BITMAP";

	/**
	 * @Fields IDX_COMPOSITE : 组合索引
	 */
	public static final String IDX_COMPOSITE = "1";

	/**
	 * @Fields IDX_NO_COMPOSITE : 非组合索引
	 */
	public static final String IDX_NO_COMPOSITE = "0";

	/**
	 * @Fields PART_TYPE_RANGE : 分区类型-范围分区
	 */
	public static final String PART_TYPE_RANGE = "RANGE";

	/**
	 * @Fields PART_TYPE_HASH : 分区类型-散列分区
	 */
	public static final String PART_TYPE_HASH = "HASH";

	/**
	 * @Fields PART_TYPE_LIST : 分区类型-列表分区
	 */
	public static final String PART_TYPE_LIST = "LIST";

	/**
	 * @Fields ONCE_SIZE : 每次抽取表数
	 */
	public static final int OBJECT_ONCE_SIZE = 500;
}
