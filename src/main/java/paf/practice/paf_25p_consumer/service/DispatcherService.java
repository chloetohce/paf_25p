package paf.practice.paf_25p_consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Qualifier("dispatcherService")
public class DispatcherService implements MessageListener {
    @Autowired
    private MessageDispatcher dispatcher;

    @Override
    @Async
    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        String channel = new String(message.getChannel());
        dispatcher.dispatch(channel, msg);
    }
}
