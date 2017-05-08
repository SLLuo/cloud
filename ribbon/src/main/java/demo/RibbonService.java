package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RibbonService {

    @Autowired
    private HystrixService hystrixService;

    @ResponseBody
    @RequestMapping(path = "/test/{fallback}", method = RequestMethod.POST)
    public Message test(@PathVariable("fallback") String fallback, @RequestBody User user) {
        System.out.println(user);
        String res = hystrixService.test(fallback);
        System.out.println("调用服务结果为" + res);
        if ("test".equals(res)) {
            return new Message(0, new Role(0, "测试人员"), null);
        }
        return new Message(0, null, "调用服务结果为" + res);
    }
}