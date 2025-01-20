package paf.practice.paf_25p_consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService implements MessageListener {
    @Autowired
    private RedisTemplate<String, String> template;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        String channel = new String(message.getChannel());
        System.out.println("Received message from %s channel: %s".formatted(channel, msg));
    }

}
