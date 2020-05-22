package com.zero.base.common.config;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
///** 
// * @ClassName: MicroServiceConfig 
// * @Description: 微服务配置地址集合
// * @author:
// * @date: 2018年12月24日 上午9:09:37
// *
// */
//@Component
//public class MicroServiceConfig {
//	public static String busUrl = "http://localhost:9023/";//业务服务地址
//	public static String etlUrl = "http://localhost:9023/";//etl采集服务地址 
//	public static String collectUrl = "http://localhost:9023/";//模型采集服务地址 
//	/**
//	 * @return the busUrl
//	 */
//	public String getBusUrl() {
//		return busUrl;
//	}
//	/**
//	 * @param busUrl 要设置的 busUrl 
//	 */
//	@Value("${microservice.busUrl:#{http://localhost:9023/}}")
//	public void setBusUrl(String busUrl) {
//		MicroServiceConfig.busUrl = busUrl;
//	}
//	/**
//	 * @return the etlUrl
//	 */
//	public String getEtlUrl() {
//		return etlUrl;
//	}
//	/**
//	 * @param etlUrl 要设置的 etlUrl 
//	 */
//	@Value("${microservice.etlUrl:#{http://localhost:9023/}}")
//	public void setEtlUrl(String etlUrl) {
//		MicroServiceConfig.etlUrl = etlUrl;
//	}
//	/**
//	 * @return the collectUrl
//	 */
//	public String getCollectUrl() {
//		return collectUrl;
//	}
//	/**
//	 * @param collectUrl 要设置的 collectUrl 
//	 */
//	@Value("${microservice.collectUrl:#{http://localhost:9024/}}")
//	public void setCollectUrl(String collectUrl) {
//		MicroServiceConfig.collectUrl = collectUrl;
//	}
//}
