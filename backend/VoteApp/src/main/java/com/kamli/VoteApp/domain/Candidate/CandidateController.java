package com.kamli.VoteApp.domain.candidate;

import com.kamli.VoteApp.dto.CandidateDTO;
import com.kamli.VoteApp.dto.PartyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/hello")
    public String get() {
        return "hello";
    }

    @PostMapping("/vote/{id}")
    @Secured("ALLOWED")
    public ResponseEntity vote(@PathVariable Long id) {
        Optional<Candidate> candidate = candidateService.voteOnCandidate(id);
        if (candidate.isPresent()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-all-candidates")
    public ResponseEntity<Set<CandidateDTO>> getAllCandidates(){
        Set<CandidateDTO> candidatesDTO = candidateService.getAllCandidates().stream()
                .map(this::mapToCandidateDTO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(candidatesDTO);
    }

    private CandidateDTO mapToCandidateDTO(CandidateDTO candidate) {
        return CandidateDTO.builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .party(candidate.getParty())
                .votes(candidate.getVotes())
                .build();
    }
}
