package veryFirstDemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class App {
	
	@Bean
	MessageService mockMessageService() {
		return new MessageService() {
			public String getMessage() {
				return "Mock message service";
			}
		};
	}
	
    public static void main( String[] args ) {
    	ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
    	MessagePrinter printer = context.getBean(MessagePrinter.class);
    	printer.printMessage();
    	
//    	MessageService service = context.getBean(MessageService.class);
//    	System.out.println(service.getMessage());
    }
}
