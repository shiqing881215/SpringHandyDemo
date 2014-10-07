package MessagingWithJMS;

import java.io.File;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.util.FileSystemUtils;

/**
 * JMS - Java Message Service
 * http://en.wikipedia.org/wiki/Java_Message_Service 
 *
 */
@Configuration
@EnableAutoConfiguration
public class Application {
	// This serves as a Message Queue, message comes here and container listens to this
	static String mailboxDestination = "mailbox-destination"; 
	
	@Bean
	Receiver receiver() {
		return new Receiver();
	}
	
	@Bean
	MessageListenerAdapter adapter(Receiver receiver) {
		MessageListenerAdapter adapter = new MessageListenerAdapter(receiver);
		// This is the method on receive that will be called when receive a message
		adapter.setDefaultListenerMethod("receiveMessage");   
		return adapter;
	}
	
	/**
	 * The SimpleMessageListenerContainer class is an asynchronous message receiver. 
	 * It uses the MessageListenerAdapter and the ConnectionFactory and is fired up when the application context starts.
	 * @param adapter
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	SimpleMessageListenerContainer container(MessageListenerAdapter adapter, ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setMessageListener(adapter);
		container.setConnectionFactory(connectionFactory);
		container.setDestinationName(mailboxDestination);
		return container;
	}
	
	public static void main(String[] args) {
		// Clean out any ActiveMQ data from a previous run
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
        
        // Launch application
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        
        // Send a message
        MessageCreator messageCreator = new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("PING");
			}
		};
		
		/*
		 * Spring provides a convenient template class called JmsTemplate. 
		 * JmsTemplate makes it very simple to send messages to a JMS message queue. 
		 * 
		 * Two beans that you don’t see defined are JmsTemplate and ActiveMQConnectionFactory. 
		 * These are created automatically by Spring Boot.
		 */
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		System.out.println("Sending a message");
		// Send to the message queue and this will actually call the MessageCreator createMessage method
		jmsTemplate.send(mailboxDestination, messageCreator);
	}
}
