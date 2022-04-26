package com.kamli.VoteApp.domain.candidate;

import com.kamli.VoteApp.dto.CandidateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/hello")
    public String get() {
        return "hello";
    }

    @GetMapping("/vote/{id}")
    public ResponseEntity vote(@PathVariable Long id) {
        Optional<Candidate> candidate = candidateService.voteOnCandidate(id);
        if (candidate.isPresent()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-all-candidates")
    public List<CandidateDTO> getAllCandidates(){
        return candidateService.getAllCandidates();
    }
}
