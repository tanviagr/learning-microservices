package com.example.demo.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
	
	@Autowired
	private MessageSource messageSource;
	
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
	
//	I18n
	@GetMapping(path = "hello-world-internationalized")
	public String helloWorldInternational(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
//		return "Good Morning";
		return messageSource.getMessage("good.morning.message", null, locale);
	}
	
}
