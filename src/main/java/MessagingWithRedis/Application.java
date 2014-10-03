package MessagingWithRedis;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@EnableAutoConfiguration

/**
 *	Initialization order :
 *	Latch -> Receiver ->  MessageListenerAdapter -> RedisMessageListenerContainer -> StringRedisTemplate
 *	Based on the dependency requirement
 *
 *	http://spring.io/guides/gs/messaging-redis/
 *	http://zh.wikipedia.org/wiki/Redis - Memory based, most popular key-value database
 *	
 *
 *	Several issue during development :
 *	1. Make sure you have the correct web container dependency, if you meet any issue when start the Tomcat
 *	   check your dependency version, for this one, we are using the spring-boot-starter-web, before I'm using
 *	   1.1.1-release and get the method cannot find exception, update to the latest 1.1.7-release resolve the issue 
 *	2. Make sure you install the Redis and start the Redis server
 *	   Download here and follow the instruction - http://redis.io/
 *
 */
public class Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	@Bean
	// We need this container to hold both the connection to the Redis and also the receiver which
	// is wrapped by the MessageListenerAdapter
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("Chat"));
		return container;
	}
	
	@Bean
	// It needs wrapped by the MessageListenerAdapter so it can be add to the container
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		// This also define which method will be called when receive message, that is "receiveMessage"
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	
	@Bean
	Receiver receiver(CountDownLatch latch) {
		return new Receiver(latch);
	}
	
	@Bean
	CountDownLatch latch() {
		return new CountDownLatch(1);
	}
	
	@Bean
	// Use to send the message
	// focused on the common use of Redis where both keys and values are `String`s.
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}
	
	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		
		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
		CountDownLatch latch = ctx.getBean(CountDownLatch.class);
		
		LOGGER.info("Sending message...");
		template.convertAndSend("Chat", "Hello from Redis!");
		
		// Block here until receiver response with the message and count down the latch to 0
		latch.await();
		
		System.exit(0);
	}
}
