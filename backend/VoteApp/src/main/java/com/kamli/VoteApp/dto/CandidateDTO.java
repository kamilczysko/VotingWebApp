package com.kamli.VoteApp.dto;

import lombok.*;

import javax.xml.bind.annotation.XmlTransient;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateDTO {
    @XmlTransient
    private Long id;
    private String name;
    private String party;
    @XmlTransient
    private PartyDTO partyDTO;
    @XmlTransient
    private Long votes = 0L;
}
