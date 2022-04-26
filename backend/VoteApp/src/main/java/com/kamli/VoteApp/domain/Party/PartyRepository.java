package com.kamli.VoteApp.domain.Party;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends CrudRepository<Party, Long> {

    @Override
    <S extends Party> List<S> saveAll(Iterable<S> entities);
}
