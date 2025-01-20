package paf.practice.paf_25p_consumer.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;

import paf.practice.paf_25p_consumer.service.ConsumerService;

@Configuration
@EnableAsync
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;
    
    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUsername;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Bean
    public RedisConnectionFactory createConnectionFactory() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        config.setDatabase(0);

        if (!redisUsername.equals("") && redisPassword.equals("")) {
            config.setUsername(redisUsername);
            config.setPassword(redisPassword);
        }

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(config, jedisClient);
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> template(RedisConnectionFactory fac) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(fac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean("topic1")
    public ChannelTopic topic() {
        return new ChannelTopic("messages");
    }

    // Abstracted to interface to allow Spring to handle async.
    @Bean("messagesAdapter")
    public MessageListenerAdapter listenerAdapter(@Qualifier("messagesService") MessageListener service) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(service);
        adapter.setSerializer(new StringRedisSerializer());
        return adapter;
    }

    @Bean("greetingsAdapter")
    public MessageListenerAdapter greetingsAdapter(@Qualifier("greetingsService") MessageListener service) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(service);
        adapter.setSerializer(new StringRedisSerializer());
        return adapter;
    }

    @Bean("dispatcherAdapater")
    public MessageListenerAdapter dispatcherAdapter(@Qualifier("dispatcherService") MessageListener service) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(service);
        adapter.setSerializer(new StringRedisSerializer());
        return adapter;
    }

    @Bean
    public RedisMessageListenerContainer listenerContainer(ChannelTopic topic, 
            @Qualifier("messagesAdapter") MessageListenerAdapter adapter, 
            @Qualifier("greetingsAdapter") MessageListenerAdapter adapter2, 
            @Qualifier("dispatcherAdapater") MessageListenerAdapter adapter3,
            RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(adapter, topic);
        container.addMessageListener(adapter2, new PatternTopic("greetings"));
        container.addMessageListener(adapter3, new PatternTopic("dispatch1"));
        container.addMessageListener(adapter3, new PatternTopic("dispatch2"));
        return container;
    }
}
