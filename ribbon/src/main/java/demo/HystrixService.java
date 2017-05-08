package demo;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("hystrix")
public interface HystrixService {
    @RequestMapping("/test/{fallback}")
    String test(@PathVariable("fallback") String fallback);
}