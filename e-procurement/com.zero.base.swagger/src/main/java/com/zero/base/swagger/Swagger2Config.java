package com.zero.base.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2Config {

/*	private ApiInfo zkxApiInfo() {
		return new ApiInfoBuilder()
				.title("数据服务平台 RESTful APIs")
				.description("更多内容相关文章请关注：http://blog.xxxxxx.com/")
				.termsOfServiceUrl("http://blog.didispace.com/")
				.contact("zkx，187xxxx039")
				.version("1.0")
				.build();
	}
	
	@Bean(value = "catalogRestApi")
	@Order(value = 4)
	public Docket catalogRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(zkxApiInfo()) //加载配置信息
				.groupName("资源目录")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.zero")) //加载swagger扫描包
				.paths(PathSelectors.any())
				.build();
	}*/

}