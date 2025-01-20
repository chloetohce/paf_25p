package paf.practice.paf_25p.repository;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QueueRepository {
    @Autowired
    private RedisTemplate<String, String> template;

    private static final Logger logger = Logger.getLogger(QueueRepository.class.getName());

    public void addToQueue(String msg) {
        template.opsForList().rightPush("queue", msg);
        logger.info("Message added to queue: " + msg);
    }
}
