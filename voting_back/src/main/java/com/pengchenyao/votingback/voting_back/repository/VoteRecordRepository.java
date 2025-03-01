package com.pengchenyao.votingback.voting_back.repository;

import com.pengchenyao.votingback.voting_back.entity.VoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRecordRepository extends JpaRepository<VoteRecord, Long> {

    @Query(value = "CALL get_vote_records()", nativeQuery = true)
    List<Object[]> getVoteRecordsFromProcedure();
}