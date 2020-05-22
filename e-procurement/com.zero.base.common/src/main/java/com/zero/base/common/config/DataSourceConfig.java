package com.zero.base.common.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

@Configuration
@ConfigurationProperties(prefix = "first.datasource")
public class DataSourceConfig {
	private Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	/**
	 * @fieldName: type
	 * @fieldType: String
	 * @Description: TODO(用一句话描述这个变量表示什么)
	 */
	private String type;

	/**
	 * @fieldName: url
	 * @fieldType: String
	 * @Description: 连接串
	 */

	private String url;

	/**
	 * @fieldName: driverClassName
	 * @fieldType: String
	 * @Description: 驱动名称
	 */
	private String driverClassName;

	/**
	 * @fieldName: username
	 * @fieldType: String
	 * @Description: 用户名
	 */

	private String username;

	/**
	 * @fieldName: password
	 * @fieldType: String
	 * @Description: 密码
	 */

	private String password;

	/**
	 * @fieldName: initialSize
	 * @fieldType: Integer
	 * @Description: 初始化多少个连接
	 */

	private Integer initialSize;

	/**
	 * @fieldName: minIdle
	 * @fieldType: Integer
	 * @Description: 最小空闲连接数
	 */

	private Integer minIdle;

	/**
	 * @fieldName: maxActive
	 * @fieldType: Integer
	 * @Description: 连接池中最大的活跃连接数
	 */

	private Integer maxActive;

	/**
	 * @fieldName: maxWait
	 * @fieldType: Integer
	 * @Description: 获取连接的，最大等待时间
	 */

	private Integer maxWait;

	/**
	 * @fieldName: timeBetweenEvictionRunsMillis
	 * @fieldType: Integer
	 * @Description: 检查一次连接池中空闲的连接
	 */

	private Integer timeBetweenEvictionRunsMillis;

	/**
	 * @fieldName: minEvictableIdleTimeMillis
	 * @fieldType: Integer
	 * @Description: 连接保持空闲而不被驱逐的最长时间
	 */

	private Integer minEvictableIdleTimeMillis;

	/**
	 * @fieldName: validationQuery
	 * @fieldType: String
	 * @Description: 验证语句
	 */

	private String validationQuery;

	/**
	 * @fieldName: testWhileIdle
	 * @fieldType: Boolean
	 * @Description: TODO(用一句话描述这个变量表示什么)
	 */

	private Boolean testWhileIdle;

	/**
	 * @fieldName: testOnBorrow
	 * @fieldType: Boolean
	 * @Description: 当从连接池借用连接时，是否测试该连接.
	 */

	private Boolean testOnBorrow;

	/**
	 * @fieldName: testOnReturn
	 * @fieldType: Boolean
	 * @Description: 在连接归还到连接池时是否测试该连接.
	 */

	private Boolean testOnReturn;

	/**
	 * @fieldName: poolPreparedStatements
	 * @fieldType: Boolean
	 * @Description: 是否池化statements
	 */

	private Boolean poolPreparedStatements;

	/**
	 * @fieldName: maxPoolPreparedStatementPerConnectionSize
	 * @fieldType: Integer
	 * @Description: 打开PSCache，并且指定每个连接上PSCache的大小
	 */

	private Integer maxPoolPreparedStatementPerConnectionSize;

	/**
	 * @fieldName: filters
	 * @fieldType: String
	 * @Description: 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
	 */

	private String filters;

	/**
	 * @fieldName: connectionProperties
	 * @fieldType: String
	 * @Description: 通过connectProperties属性来打开mergeSql功能；慢SQL记录
	 */

	private String connectionProperties;

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public Integer getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(Integer maxWait) {
		this.maxWait = maxWait;
	}

	public Integer getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public Integer getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public Boolean getTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(Boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public Boolean getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(Boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public Boolean getTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(Boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public Boolean getPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(Boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public Integer getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}

	public void setMaxPoolPreparedStatementPerConnectionSize(Integer maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	@Bean
	public ServletRegistrationBean druidServlet() {
		ServletRegistrationBean reg = new ServletRegistrationBean();
		reg.setServlet(new StatViewServlet());
		reg.addUrlMappings("/druid/*");
		reg.addInitParameter("loginUsername", username);
		reg.addInitParameter("loginPassword", password);
		return reg;
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		filterRegistrationBean.addInitParameter("profileEnable", "true");
		return filterRegistrationBean;
	}

	@Bean
	@Primary
	public DataSource druidDataSource() {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(url);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setDriverClassName(driverClassName);
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setConnectionProperties(connectionProperties);
		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}
		return datasource;
	}

	
	/**
	 * 监听Spring 1.定义拦截器 2.定义切入点 3.定义通知类
	 * 
	 * @return
	 */
	@Bean
	public DruidStatInterceptor druidStatInterceptor() {
		return new DruidStatInterceptor();
	}

	@Bean
	public JdkRegexpMethodPointcut druidStatPointcut() {
		JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
		String controller = "com.zero.*.*.controller.*";
		String service = "com.zero.*.*.service.*";
		String mapper = "com.zero.*.*.mapper.*";
		druidStatPointcut.setPatterns(controller, service, mapper);
		return druidStatPointcut;
	}

	@Bean
	public Advisor druidStatAdvisor() {
		DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
		defaultPointcutAdvisor.setPointcut(druidStatPointcut());
		defaultPointcutAdvisor.setAdvice(druidStatInterceptor());
		return defaultPointcutAdvisor;
	}
	@Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource druidDataSource) {
        return new DataSourceTransactionManager(druidDataSource);
    }
}
