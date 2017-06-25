package pl.xsolve.redisfilterdemo.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @PostMapping("/")
    public Response hello(@RequestBody final Request request) {
        return new Response(String.format("Hello %s!", request.getName()));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private String greeting;
    }
}
