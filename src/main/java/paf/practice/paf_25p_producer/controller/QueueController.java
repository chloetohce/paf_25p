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
        for (String m : messages) {
            service.sendMessage(m);
        }
        
        return ResponseEntity.ok().body("Messagge sent.");
    }
    
}
