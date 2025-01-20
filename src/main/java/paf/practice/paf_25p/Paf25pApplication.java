package paf.practice.paf_25p;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import paf.practice.paf_25p.consumer.MessagePoller;

@SpringBootApplication
@EnableAsync
public class Paf25pApplication implements CommandLineRunner{
	@Autowired
	private MessagePoller poller;

	public static void main(String[] args) {
		SpringApplication.run(Paf25pApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		poller.start();
	}

}
