package com.kamli.VoteApp.domain.candidate;

import com.kamli.VoteApp.domain.party.Party;
import com.kamli.VoteApp.domain.party.PartyRepository;
import com.kamli.VoteApp.dto.CandidateDTO;
import com.kamli.VoteApp.dto.CandidatesDTO;
import com.kamli.VoteApp.dto.PartyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    public List<CandidateDTO> getAllCandidates() {
        return candidateRepository.findAll().stream()
                .map(this::mapToCandidateDTO)
                .collect(Collectors.toList());
    }

    private CandidateDTO mapToCandidateDTO(Candidate candidate) {
        return CandidateDTO.builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .party(candidate.getParty().getName())
                .partyDTO(mapToPartyDTO(candidate))
                .votes(candidate.getNumberOfVotes())
                .build();
    }

    private PartyDTO mapToPartyDTO(Candidate candidate) {
        return PartyDTO.builder().id(candidate.getParty().getId()).name(candidate.getParty().getName()).build();
    }

    @PostConstruct
    public void initData() {
        CandidatesDTO candidates = new RestTemplate()
                .getForObject("http://webtask.future-processing.com:8069/candidates", CandidatesDTO.class);
        Map<String, Party> nameToParty = saveParties(candidates);
        saveCandidates(candidates, nameToParty);
    }

    public Optional<Candidate> voteOnCandidate(Long id) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        return candidate.map(c -> Candidate.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .party(c.getParty())
                        .numberOfVotes(c.getNumberOfVotes() + 1)
                        .build())
                .map(candidateToUpdate -> candidateRepository.save(candidateToUpdate));
    }

    private List<Candidate> saveCandidates(CandidatesDTO candidates, Map<String, Party> nameToParty) {
        List<Candidate> candidatesToSave = candidates.getCandidate().stream()
                .map(candidate -> mapToCandidate(nameToParty, candidate))
                .collect(Collectors.toList());
        return candidateRepository.saveAll(candidatesToSave);
    }

    private Candidate mapToCandidate(Map<String, Party> nameToParty, CandidateDTO candidate) {
        return Candidate.builder()
                .name(candidate.getName())
                .party(nameToParty.get(candidate.getParty()))
                .numberOfVotes(candidate.getVotes())
                .build();
    }

    private Map<String, Party> saveParties(CandidatesDTO candidates) {
        List<Party> allParties = candidates.getCandidate().stream()
                .map(CandidateDTO::getParty)
                .distinct()
                .map(this::mapToParty)
                .collect(Collectors.toList());
        return partyRepository.saveAll(allParties).stream()
                .collect(Collectors.toMap(Party::getName, Function.identity()));
    }

    private Party mapToParty(String partyName) {
        return Party.builder().name(partyName).build();
    }
}
