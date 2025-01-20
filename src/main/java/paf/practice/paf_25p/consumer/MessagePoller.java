package paf.practice.paf_25p.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

@Component
public class MessagePoller {
    @Autowired
    private RedisTemplate<String, String> template;

    private static final ExecutorService pool = Executors.newSingleThreadExecutor();

    @Async
    public void start() {
        // can create an inner class poller that implements Runnable, or create a separate class file.

        /** Code for an anonymous inner class:
         * Runnable poller = () -> {
         *  ListOperations<String, String> orderList = template.opsForList();
         *  while (true) {
         *      Optional<String> opt = Optional.ofNullable(orderList.rightPop("orders", Duration.ofSeconds(5)));
         *      if (opt.isPresent()) {
         *          String data = opt.get();
         *          ...
         *      }
         *  }
         * }
         */
        
        pool.execute(new ThreadWorker(template, "Thread 1"));
    }

    @PreDestroy
    public void end() {
        pool.shutdownNow();
    }
}
