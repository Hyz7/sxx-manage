package com.sxx.manage_media;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-06-23 11:53
 **/
@EnableDiscoveryClient//从Eureka Server获取服务
@SpringBootApplication//扫描所在包及子包的bean，注入到ioc中
@EntityScan("com.sxx.framework.domain")//扫描实体类
@ComponentScan(basePackages={"com.sxx.api"})//扫描接口
@ComponentScan(basePackages={"com.sxx.framework"})//扫描framework中通用类
@ComponentScan(basePackages={"com.sxx.manage_media"})//扫描本项目下的所有类
public class ManageMediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageMediaApplication.class,args);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("Access-Control-Allow-Origin");
        return corsConfiguration;
    }

    /**
     * 跨域过滤器
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
