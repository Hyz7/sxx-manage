package com.sxx.manage;

import com.sxx.manage.config.DataEntityHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EntityScan("com.sxx.framework.domain")//扫描实体类
@ComponentScan(basePackages = {"com.sxx.api"})//扫描接口
@ComponentScan(basePackages = {"com.sxx.framework"})//扫描common
@ComponentScan(basePackages = {"com.sxx.manage"})//扫描本项目下的所有类
/**
 * 〈一句话功能简述〉<br>
 * 〈启动类〉
 *
 * @author hyz
 * @create 2018/11/23 0023
 * @since 1.0.0
 */
@EnableDiscoveryClient
public class ManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }

    @Bean
    public DataEntityHttpMessageConverter dataEntityHttpMessageConverter(){
        return new DataEntityHttpMessageConverter();
    }
}
