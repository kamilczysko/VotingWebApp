package com.kamli.VoteApp.domain.Candidate;

import com.kamli.VoteApp.domain.Party.Party;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @ManyToOne
    @JoinColumn(name="party_id")
    Party party;
    Long numberOfVotes = 0L;
}
