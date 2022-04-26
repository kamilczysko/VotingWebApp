package com.kamli.VoteApp.domain.candidate;

import com.kamli.VoteApp.dto.CandidateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/hello")
    public String get() {
        return "hello";
    }

    @PostMapping("/vote")
    @Secured("ALLOWED")
    public ResponseEntity vote(@RequestBody Long id) {
        Optional<Candidate> candidate = candidateService.voteOnCandidate(id);
        if (candidate.isPresent()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-all-candidates")
    public String getAllCandidates(){
        String res = " ";
        for(CandidateDTO a : candidateService.getAllCandidates()){
            res += a.getName()+" - "+a.getParty() + " - "+a.getVotes()+"\n";
        }

        return res;
    }
}
