package ValidatingFormInput;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
/**
 *	To activate Spring MVC, you would normally add @EnableWebMvc to the Application class. 
 *  But Spring Boot’s @EnableAutoConfiguration already adds this annotation when it detects spring-webmvc on your classpath. 
 *  The application also has @ComponentScan to find the annotated @Controller class and its methods.

 *  The Thymeleaf configuration is also taken care of by @EnableAutoConfiguration: 
 *  by default templates are located in the classpath under templates/ and 
 *  are resolved as views by stripping the .html suffix off the file name. 
 *  Thymeleaf settings can be changed and overridden in a variety of ways depending on what you need to achieve 
 *
 */
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
