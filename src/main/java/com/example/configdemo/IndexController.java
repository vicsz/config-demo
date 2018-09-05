package com.example.configdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RefreshScope
public class IndexController {

    @Value("${application.greeting.message:hello from default}")
    private String greetingMessage;

    @Value("${application.number.value:0}")
    private String numberValue;

    @Value("${vcap.services.my-custom-service.username:No VCAP Settings found}")
    private String customServiceUsername;

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("greetingMessage", greetingMessage);
        model.put("numberValue", numberValue);
        model.put("customServiceUsername", customServiceUsername);
        return "index";
    }
}
