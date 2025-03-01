package com.pengchenyao.votingback.voting_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vote_record") // 資料表名稱
public class VoteRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //
    private Long id;

    @Column(name = "voter_name", nullable = false) // 投票人名稱
    private String voterName;

    @ManyToOne
    @JoinColumn(name = "vote_item_id", nullable = false) // 外鍵關聯到vote表
    private Vote voteItem; // 關聯的投票項目
}