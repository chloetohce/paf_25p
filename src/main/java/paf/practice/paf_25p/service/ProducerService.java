package paf.practice.paf_25p.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;

@Service
public class ProducerService {
    private static final BlockingQueue<String> messageQueue =  new LinkedBlockingQueue<>();
    private final RedisTemplate<String, String> template;
    private final ExecutorService pool;
    
    public ProducerService(RedisTemplate<String, String> template) {
        this.template = template;

        // Start worker threads
        this.pool = Executors.newFixedThreadPool(2);
        pool.execute(new ProducerWorker(template, "Producer 1", messageQueue));
        pool.execute(new ProducerWorker(template, "Producer 2", messageQueue));
    }

    public void addMessage(String msg) {
        try {
            messageQueue.put(msg); //If the queue is full, add() fails with an exception, whereas put() blocks.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } 
    }
    
    @PreDestroy // Ensures that the thread pool is shut down when the app stops
    private void shutdownWorkers() {
        if (pool != null) {
            pool.shutdownNow();
        }
    }
}
