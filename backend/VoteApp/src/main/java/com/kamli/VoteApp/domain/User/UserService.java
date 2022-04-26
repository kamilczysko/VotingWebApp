package com.kamli.VoteApp.domain.User;

import com.kamli.VoteApp.domain.Party.Party;
import com.kamli.VoteApp.dto.CandidatesDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class UserService {

    @PostConstruct
    public void initData() {
        BannedUsers candidates = new RestTemplate()
                .getForObject("http://webtask.future-processing.com:8069/blocked", BannedUsers.class);


    }

}
