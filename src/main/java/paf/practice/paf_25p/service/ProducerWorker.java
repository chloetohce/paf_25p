package paf.practice.paf_25p.service;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * This is a worker class for the producer that adds the message to the Redis list (for 
 * the consumer to read). 
 * 
 * Messages can be passed in as a constructor parameter, but that fixes it. Here, a shared
 * blocking queue is used, where the Service class would add new messages to. The worker 
 * thread asynchronously processes these messages without having to create new threads.
 */
public class ProducerWorker implements Runnable {

    private RedisTemplate<String, String> template;
    private String name;
    private final BlockingQueue<String> messageQueue;
    private final Logger logger;

    public ProducerWorker(RedisTemplate<String, String> template, String name, BlockingQueue<String> messageQueue) {
        this.template = template;
        this.name = name;
        this.logger = Logger.getLogger(this.name);
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        ListOperations<String, String> ops = template.opsForList();
        Random random = new Random();
        try {
            while (true) {
                String msg = messageQueue.take();
                ops.rightPush("queue", msg);
                logger.info("Message added to queue: " + msg);
                Thread.sleep((long) (random.nextDouble() * 2000));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle thread interruption
            logger.warning("ProducerWorker %s interrupted".formatted(name));
        }
        
    }
    
}
