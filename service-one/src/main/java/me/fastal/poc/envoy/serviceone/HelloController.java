package me.fastal.poc.envoy.serviceone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    private static final Logger LOG = LoggerFactory.getLogger (HelloController.class);

    @GetMapping("/hello")
    public List<String> hello(HttpServletRequest request) {

        List<String> result = new ArrayList<> ();

        //String hostname = "localhost";
        String hostname = "service-one-envoy";
        String service2Url = String.format ("http://%s:9210/hello", hostname);
        String service3Url = String.format ("http://%s:9310/hello", hostname);

        ResponseEntity<String> response;

        result.add("Hello, I'm service one");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set ("x-request-id", request.getHeader ("x-request-id"));
        headers.set ("x-b3-traceid", request.getHeader ("x-b3-traceid"));
        headers.set ("x-b3-spanid", request.getHeader ("x-b3-spanid"));
        headers.set ("x-b3-parentspanid", request.getHeader ("x-b3-parentspanid"));
        headers.set ("x-b3-sampled", request.getHeader ("x-b3-sampled"));
        headers.set ("x-b3-flags", request.getHeader ("x-b3-flags"));
        headers.set ("x-ot-span-context", request.getHeader ("x-ot-span-context"));
        HttpEntity<String> entity = new HttpEntity<> (null, headers);

        LOG.info ("Consuming service 2...");
        response = restTemplate.exchange(service2Url, HttpMethod.GET, entity, String.class);
        LOG.info ("Service 2 response: {}", response.getBody ());
        result.add(response.getBody ());

        LOG.info ("Consuming service 3...");
        response = restTemplate.exchange(service3Url, HttpMethod.GET, entity, String.class);
        LOG.info ("Service 3 response: {}", response.getBody ());
        result.add(response.getBody ());

        return result;
    }



}
