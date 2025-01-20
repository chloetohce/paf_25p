package paf.practice.paf_25p_consumer.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Another MessageListener class to test having multiple listeners to different topic
 */
@Service
@Qualifier("greetingsService")
public class GreetingsService implements MessageListener{

    @Override
    @Async
    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        String channel = new String(message.getChannel());
        System.out.println("%s received message from %s channel: %s".formatted(Thread.currentThread().getName() ,channel, msg));
    }
    
}
