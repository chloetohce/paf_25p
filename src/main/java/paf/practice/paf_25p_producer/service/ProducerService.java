package paf.practice.paf_25p_producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    @Qualifier("topic1")
    private ChannelTopic topic1;

    public void sendMessage(String msg, String channel) {
        template.convertAndSend(channel, msg);
    }
}
