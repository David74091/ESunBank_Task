package com.pengchenyao.votingback.voting_back.controller;

import com.pengchenyao.votingback.voting_back.entity.Vote;
import com.pengchenyao.votingback.voting_back.entity.VoteRecord;
import com.pengchenyao.votingback.voting_back.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/votes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/") //允許跨域請求5173為前端地址
public class VoteController {
    private final VoteService voteService;

    @GetMapping
    public ResponseEntity<List<Vote>> getVotes(){
        List<Vote> votes = voteService.getVotes();
        return new ResponseEntity<>(votes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vote> getVoteById(@PathVariable Long id){
        Vote vote = voteService.getVoteById(id);
        return new ResponseEntity<>(vote, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVote(@RequestBody Vote vote) {
        voteService.addVote(vote);
        return new ResponseEntity<>(HttpStatus.CREATED); // 返回成功狀態碼
    }

    //對項目投票
    @PostMapping("/voting")
    public ResponseEntity<?> voting(@RequestParam String voterName, @RequestBody List<Long> ids) {
        voteService.votingWithStoredProcedure(voterName, ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateVote(@RequestBody Vote vote) {
        voteService.updateVote(vote);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVote(@PathVariable Long id){
        voteService.deleteVote(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 查詢所有投票紀錄
    @GetMapping("/voteRecords")
    public ResponseEntity<List<VoteRecord>> getVoteRecords() {
        List<VoteRecord> voteRecords = voteService.getVoteRecords();
        return new ResponseEntity<>(voteRecords, HttpStatus.OK);
    }

}
