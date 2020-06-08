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

//
//    @Bean
//    public Docket stockApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("库存")
//                .genericModelSubstitutes(DeferredResult.class)
//                .useDefaultResponseMessages(false)
//                .forCodeGeneration(true)
//                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
//                .select()
//                .paths(or(regex("/stock/.*")))//过滤的接口
//                .build()
//                .apiInfo(stockApiInfo());
//    }
//
//    private ApiInfo stockApiInfo() {
//        return new ApiInfoBuilder()
//                .title("库存管理")//大标题
//                .description("根据条件查询库存")//详细描述
//                .version("1.0")//版本
//                .contact(new Contact("日播开发团队", null, "http://itwx.ribo.com.cn/"))//作者
//                //.license("2.0apache许可证")
//                //.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
//                .build();
//    }


    @Bean
    public Docket createRestApi() {
        Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                Class<?> declaringClass = input.declaringClass();
                if (declaringClass == BasicErrorController.class)// 排除
                    return false;
                if(declaringClass.isAnnotationPresent(RestController.class)) // 被注解的类
                    return true;
                if(input.isAnnotatedWith(ResponseBody.class)) // 被注解的方法
                    return true;
                return false;
            }
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
                .title("TaorayTaoray")//大标题
                .version("1.0")//版本
                .contact(new Contact("日播开发团队", null,null))//作者
                .build();
    }

}
