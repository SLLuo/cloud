package demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class HystrixService {

    @Value("${test:test}")
    private String test;

    @RequestMapping("/test/{fallback}")
    @HystrixCommand(fallbackMethod="testFallbackMethod")/*调用方式失败后调用helloFallbackMethod*/
    public String test(@PathVariable("fallback") String fallback){
        System.out.println(test);
        System.out.println(fallback);
        if("1".equals(fallback)){
            throw new RuntimeException("...");
        }
        return "test";
    }

    public String testFallbackMethod(String fallback){
        return "fallback 参数值为:"+fallback;
    }
}