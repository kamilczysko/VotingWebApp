package com.kamli.VoteApp.domain.party;

import com.kamli.VoteApp.domain.candidate.Candidate;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Party")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String name;
    @OneToMany(mappedBy = "party")
    List<Candidate> candidate;
}
