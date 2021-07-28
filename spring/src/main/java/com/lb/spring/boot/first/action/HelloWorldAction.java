package com.lb.spring.boot.first.action;


//@RestController
//@RequestMapping("/api/v1")
public class HelloWorldAction {

    //@RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

}