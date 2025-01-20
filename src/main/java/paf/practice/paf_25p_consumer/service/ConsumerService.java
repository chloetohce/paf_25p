package paf.practice.paf_25p_consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@Qualifier("messagesService")
public class ConsumerService implements MessageListener {
    @Autowired
    private RedisTemplate<String, String> template;

    /**
     * The topic of the message can be retrieved, and can be the basis of differentiating
     * different processing logic on how to handle the incoming data. The Message Listener 
     * can then be configured to listen to multiple different topics. 
     * 
     * Granted, it is also possible to just have a separate MessageListener class that 
     * handles a different logic and listens to a completely different channel. 
     */
    @Override
    @Async
    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        String channel = new String(message.getChannel());
        System.out.println("%s received message from %s channel: %s".formatted(Thread.currentThread().getName() ,channel, msg));
    }

}
