package com.pengchenyao.votingback.voting_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor //生成無參數構造函數
@AllArgsConstructor //生成包含所有字段的構造函數
@Builder //自動生成建造者模式
@Entity //指定為資料庫實體
public class Vote {
    @Id //指定主鍵
    @GeneratedValue(strategy = GenerationType.IDENTITY) //按序列生成主鍵
    private Long id;
    @Column(name = "Name", nullable = false) //指定對應的列名稱
    private String name;
    @Column(name = "Votes_num", nullable = false) //指定對應的列名稱
    private int votes_num;
}
