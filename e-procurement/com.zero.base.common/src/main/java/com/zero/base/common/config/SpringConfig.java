package com.zero.base.common.config;


import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.MultipartConfigElement;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.zero.base.common.rest.client.DmRestTemplate;
import com.zero.base.common.springmvc.mapper.NullMapper;

@Configuration
public class SpringConfig {
	
	@Value("${common.remote.maxTotalConnect:0}")
	private int maxTotalConnect; // 连接池的最大连接数默认为0
	@Value("${common.remote.maxConnectPerRoute:200}")
	private int maxConnectPerRoute; // 单个主机的最大连接数
	@Value("${common.remote.connectTimeout:2000}")
	private int connectTimeout; // 连接超时默认2s
	@Value("${common.remote.readTimeout:30000}")
	private int readTimeout; // 读取超时默认30s
	@Value("${common.upload.maxFileSize:1024}")
	private int maxFileSize; // 上传单个文件最大默认100M
	@Value("${common.upload.maxRequestSize:2048}")
	private int maxRequestSize; // 设置总上传数据默认100M

	/**
	 * 文件上传配置
	 * 
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 单个文件最大
		factory.setMaxFileSize(maxFileSize + "MB"); // KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize(maxRequestSize + "MB");
		return factory.createMultipartConfig();
	}
	
	/**
	* @Title: dmRestTemplate
	* @Description: 支撑泛型
	* @param restTemplate
	* @return
	*/ 
	@Bean
	public DmRestTemplate dmRestTemplate(RestTemplate restTemplate){
		return new DmRestTemplate(restTemplate);
	}
	
	/**
	 * 文件上传配置
	 * 
	 * @return
	 */
	@Bean
	@Primary
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		RestTemplate restTemplate = new RestTemplate(factory);
		List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();

		// 重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
		HttpMessageConverter<?> converterTarget = null;
		for (HttpMessageConverter<?> item : converterList) {
			if (StringHttpMessageConverter.class == item.getClass()) {
				converterTarget = item;
				break;
			}
		}
		if (null != converterTarget) {
			converterList.remove(converterTarget);
		}
		converterList.add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		// 加入FastJson转换器 根据使用情况进行操作，此段注释，默认使用jackson
		// converterList.add(new FastJsonHttpMessageConverter4());
		return restTemplate;
	}

	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
		if (this.maxTotalConnect <= 0) {
			SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
			factory.setConnectTimeout(this.connectTimeout);
			factory.setReadTimeout(this.readTimeout);
			return factory;
		}
		HttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(this.maxTotalConnect)
				.setMaxConnPerRoute(this.maxConnectPerRoute).build();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		factory.setConnectTimeout(this.connectTimeout);
		factory.setReadTimeout(this.readTimeout);
		return factory;

	}

	@Bean
	@Primary
	public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
		NullMapper mapper = new NullMapper();
		return new MappingJackson2HttpMessageConverter(mapper);
	}
}
