package me.fastal.poc.envoy.servicethree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private static final Logger LOG = LoggerFactory.getLogger (HelloController.class);

    @GetMapping("/hello")
    public String hello() {
        String message = "Hello, I'm service three";
        LOG.info (message);
        return message;
    }

}
