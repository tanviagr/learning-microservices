package com.example.demo.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
	
	@GetMapping(path = "hello-world")
	public String helloWorld() {
		return "Hello World!";
	}
	
	@GetMapping(path = "hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello world bean!");
	}
	
	@GetMapping(path = "hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable (@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World %s", name));
	}
	
}
