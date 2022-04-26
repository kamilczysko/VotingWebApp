package com.kamli.VoteApp;

import com.kamli.VoteApp.dto.CandidatesDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class VoteAppApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(VoteAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CandidatesDTO candidates = new RestTemplate()
				.getForObject("http://webtask.future-processing.com:8069/candidates", CandidatesDTO.class);

	}
}
