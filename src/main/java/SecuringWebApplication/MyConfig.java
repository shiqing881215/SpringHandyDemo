package SecuringWebApplication;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfig extends WebMvcConfigurerAdapter{

	// method setViewName map to the html file under resources/templates directory 
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("secureHome");
		registry.addViewController("/").setViewName("secureHome");
		registry.addViewController("/hello").setViewName("secureHello");
		registry.addViewController("/login").setViewName("secureLogin");
	}
	
}
