package paf.practice.paf_25p.consumer;

import java.time.Duration;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * Here, the thread worker does the work of the consumer, reading the messages
 * within the list in Redis.
 * 
 * I think this can also be used to implement the producer side.
 */
public class ThreadWorker implements Runnable {
    private final Logger logger;
    private RedisTemplate<String, String> template;
    private String name;
    
    public ThreadWorker(RedisTemplate<String, String> template, String name) {
        this.name = name;
        this.template = template;
        this.logger = Logger.getLogger(this.name);
    }

    @Override
    public void run() {
        ListOperations<String, String> ops = template.opsForList();
        while (true) {
            Optional<String> opt = Optional.ofNullable(ops.leftPop("queue", Duration.ofSeconds(30)));
            
            if (opt.isEmpty()) {
                continue;
            }

            String payload = opt.get();
            logger.info("Received message from queue: %s".formatted(payload));
        }
    }

    
}
