package com.arthur.cloud.activity.conf;

import com.google.common.base.Predicate;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = { "com" })
public class Swagger2Config  extends WebMvcConfigurerAdapter {



    @Override
   public  void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket createRestApi() {
        Predicate<RequestHandler> predicate = input -> {
            Class<?> declaringClass = input.declaringClass();
            // 排除
            if (declaringClass == BasicErrorController.class)
                return false;
            // 被注解的类
            if(declaringClass.isAnnotationPresent(RestController.class))
                return true;
            // 被注解的方法
            if(input.isAnnotatedWith(ResponseBody.class))
                return true;
            return false;
        };
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(predicate)
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //大标题
                .title("幸运小活动")
                //版本
                .version("1.0")
                //作者
                .contact(new Contact("惊叹号开发团队", null,null))
                .build();
    }

    @Bean
    public Docket webApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("web")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                // base，最终调用接口后会和paths拼接在一起
                .pathMapping("/")
                .select()
                //过滤的接口
                .paths(or(regex("/web/.*")))
                .build()
                .apiInfo(webApiInfo());
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                //大标题
                .title("授权登录")
                //详细描述
                .description("授权登录、更新用户信息")
                //版本
                .version("1.0")
                //作者
                .contact(new Contact("arthur", "https://github.com/Arthur-qin", null))
                .build();
    }

    @Bean
    public Docket brandApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("品牌业务接口")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                // base，最终调用接口后会和paths拼接在一起
                .pathMapping("/")
                .select()
                //过滤的接口
                .paths(or(regex("/brand/.*")))
                .build()
                .apiInfo(brandApiInfo());
    }

    private ApiInfo brandApiInfo() {
        return new ApiInfoBuilder()
                //大标题
                .title("品牌业务")
                //详细描述
                .description("品牌业务接口")
                //版本
                .version("1.0")
                //作者
                .contact(new Contact("arthur", "https://github.com/Arthur-qin", null))
                .build();
    }

    @Bean
    public Docket activityApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("活动业务接口")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                // base，最终调用接口后会和paths拼接在一起
                .pathMapping("/")
                .select()
                //过滤的接口
                .paths(or(regex("/activity/.*")))
                .build()
                .apiInfo(activityApiInfo());
    }

    private ApiInfo activityApiInfo() {
        return new ApiInfoBuilder()
                //大标题
                .title("活动业务")
                //详细描述
                .description("活动业务接口")
                //版本
                .version("1.0")
                //作者
                .contact(new Contact("arthur", "https://github.com/Arthur-qin", null))
                .build();
    }


}
