package paf.practice.paf_25p_producer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import paf.practice.paf_25p_producer.service.ProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/send")
public class QueueController {
    @Autowired
    private ProducerService service;

    @PostMapping("/messages")
    public ResponseEntity<String> sendMessage(@RequestBody List<String> messages) {
        for (int i = 0; i < messages.size(); i++) {
            service.sendMessage(messages.get(i), i % 2 == 0 ? "dispatch1" : "dispatch2");
        }
        
        return ResponseEntity.ok().body("Message sent.");
    }
    
}
