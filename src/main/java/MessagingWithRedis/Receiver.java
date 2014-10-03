package MessagingWithRedis;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Receiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	
	// A synchronization aid that allows one or more threads to wait until 
	// a set of operations being performed in other threads completes.
	// A CountDownLatch is initialized with a given count. 
	// The await methods block until the current count reaches zero due to invocations of the countDown() method
	private CountDownLatch latch;
	
	@Autowired
	public Receiver(CountDownLatch latch) {
		this.latch = latch;
	}
	
	public void receiveMessage(String message) {
		LOGGER.info("Received <" + message + ">");
		latch.countDown(); // minus one
	}
}
