package com.kamli.VoteApp.domain.Candidate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Override
    <S extends Candidate> List<S> saveAll(Iterable<S> entities);

}
