package com.pengchenyao.votingback.voting_back.service;

import com.pengchenyao.votingback.voting_back.entity.Vote;
import com.pengchenyao.votingback.voting_back.entity.VoteRecord;
import com.pengchenyao.votingback.voting_back.repository.VoteRecordRepository;
import com.pengchenyao.votingback.voting_back.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoteRecordRepository voteRecordRepository;


    // 取得所有投票項目
    public List<Vote> getVotes() {
        return voteRepository.getVotes();
    }

    // 根據 ID 查詢投票
    public Vote getVoteById(Long id) {
        return voteRepository.getVoteById(id);
    }

    // 新增投票項目
    public void addVote(Vote vote) {
        voteRepository.addVote(vote.getName(), vote.getVotes_num());
    }

    // 更新投票項目
    public void updateVote(Vote vote) {
        voteRepository.updateVote(vote.getId(), vote.getName(), vote.getVotes_num());
    }

    // 刪除投票項目
    public void deleteVote(Long id) {
        voteRepository.deleteVote(id);
    }

    // 對項目進行投票
    @Transactional
    public void votingWithStoredProcedure(String voterName, List<Long> ids) {
        ids.forEach(id -> voteRepository.voteForItem(voterName, id));
    }

    //查詢投票紀錄
    public List<VoteRecord> getVoteRecords() {
        List<Object[]> rawData = voteRecordRepository.getVoteRecordsFromProcedure();

        return rawData.stream().map(record -> {
            return VoteRecord.builder()
                    .id((Long) record[0]) // vote_record 表的 ID
                    .voterName((String) record[1]) // 投票人名字
                    .voteItem(Vote.builder() // 解構關聯的投票項目
                            .id((Long) record[2]) // vote 表的 ID
                            .name((String) record[3]) // vote 表的名稱
                            .votes_num((Integer) record[4]) // vote 表的投票數
                            .build())
                    .build();
        }).collect(Collectors.toList());
    }
}
