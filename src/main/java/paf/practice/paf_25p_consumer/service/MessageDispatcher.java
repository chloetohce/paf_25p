package paf.practice.paf_25p_consumer.service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

/**
 * This class uses a Consumer function, which does not return any output. To configure it 
 * to return a value, use Function<String, T> instead.
 */
@Service
public class MessageDispatcher {
    private final Map<String,Consumer<String>> processors = new HashMap<>();

    public MessageDispatcher() {
        processors.put("dispatch1", this::processTopic1);
        processors.put("dispatch2", this::processTopic2);
    }

    public void dispatch(String topic, String message) {
        processors.getOrDefault(topic, this::handleUnknownTopic).accept(message);
    }

    public void processTopic1(String msg) {
        System.out.println("Processing topic1 message: %s".formatted(msg));
    }

    public void processTopic2(String msg) {
        System.out.println("Processing topic2 message: %s".formatted(msg));
    }

    public void handleUnknownTopic(String msg) {
        System.out.println("No handler for message: " + msg);
    }
}
