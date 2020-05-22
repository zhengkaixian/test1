package com.zero.eprocurement.app;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** 
 * @ClassName: Application 
 * @Description: TODO (这里用一句话描述这个类的作用)
 * @author: think
 * @date: 2018年11月28日 下午8:49:19
 *
 */
@SuppressWarnings("deprecation")
@SpringBootApplication(scanBasePackages="com.zero")
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = VelocityAutoConfiguration.class)
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}