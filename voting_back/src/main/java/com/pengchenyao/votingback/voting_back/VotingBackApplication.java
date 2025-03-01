package com.pengchenyao.votingback.voting_back;

import com.pengchenyao.votingback.voting_back.entity.Vote;
import com.pengchenyao.votingback.voting_back.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class VotingBackApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(VotingBackApplication.class, args);
    }

    private final VoteService voteService;
    @Override
    public void run(String... args) throws Exception {
        if (voteService.getVotes().isEmpty()) { // 檢查資料庫裡有無資料
            Vote vote1 = Vote.builder().name("反萊豬").votes_num(3).build();
            Vote vote2 = Vote.builder().name("公投綁大選").votes_num(6).build();
            Vote vote3 = Vote.builder().name("重啟核四").votes_num(3).build();
            Vote vote4 = Vote.builder().name("珍愛藻礁").votes_num(2).build();
            voteService.addVote(vote1);
            voteService.addVote(vote2);
            voteService.addVote(vote3);
            voteService.addVote(vote4);
        }
    }
}
