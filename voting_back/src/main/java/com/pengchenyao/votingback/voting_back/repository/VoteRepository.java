package com.pengchenyao.votingback.voting_back.repository;

import com.pengchenyao.votingback.voting_back.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    // 調用Stored Procedure 新增投票
    @Procedure(procedureName = "add_vote")
    void addVote(@Param("vote_name") String voteName, @Param("votes_num") Integer votesNum);

    // 調用Stored Procedure 查詢所有投票
    @Query(value = "CALL get_votes()", nativeQuery = true)
    List<Vote> getVotes();

    // 調用Stored Procedure 根據 ID 查詢投票
    @Query(value = "CALL get_vote_by_id(:vote_id)", nativeQuery = true)
    Vote getVoteById(@Param("vote_id") Long id);

    // 調用Stored Procedure 更新投票
    @Procedure(procedureName = "update_vote")
    void updateVote(
            @Param("vote_id") Long id,
            @Param("vote_name") String name,
            @Param("votes_num") Integer votesNum
    );

    // 調用Stored Procedure 刪除投票
    @Procedure(procedureName = "delete_vote")
    void deleteVote(@Param("vote_id") Long id);

    // 調用Stored Procedure 對項目投票
    @Procedure(procedureName = "vote_for_item")
    void voteForItem(@Param("voterName") String voterName, @Param("voteItemId") Long voteItemId);
}