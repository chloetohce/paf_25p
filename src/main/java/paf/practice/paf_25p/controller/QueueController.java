package paf.practice.paf_25p.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import paf.practice.paf_25p.service.ProducerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/queue/submit")
public class QueueController {

    @Autowired
    ProducerService service;
    
    @PostMapping("")
    public String addMessages(@RequestBody List<String> messages) {
        
        for (String m : messages) {
            service.addMessage(m);
        }

        return "posted";
    }
    
    
}
