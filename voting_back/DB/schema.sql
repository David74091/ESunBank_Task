-- 建立投票項目表 vote
CREATE TABLE vote (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) NOT NULL COMMENT '投票項目名稱',
                      votes_num INT NOT NULL COMMENT '投票數'
);

-- 建立投票紀錄表 vote_record
CREATE TABLE vote_record (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             voter_name VARCHAR(255) NOT NULL COMMENT '投票人名稱',
                             vote_item_id BIGINT NOT NULL COMMENT '投票項目ID',
                             FOREIGN KEY (vote_item_id) REFERENCES vote(id)     -- 外鍵，關聯到vote表的ID
                                 ON DELETE CASCADE

-- 新增投票項目
DELIMITER $$
CREATE PROCEDURE add_vote(
    IN vote_name VARCHAR(255),
    IN votes_num INT
)
BEGIN
INSERT INTO vote (name, votes_num) VALUES (vote_name, votes_num);
END$$
DELIMITER ;

-- 查詢所有投票項目
DELIMITER $$
CREATE PROCEDURE get_votes()
BEGIN
SELECT * FROM vote;
END$$
DELIMITER ;

-- 根據ID查詢投票項目
DELIMITER $$
CREATE PROCEDURE get_vote_by_id(
    IN vote_id BIGINT
)
BEGIN
SELECT * FROM vote WHERE id = vote_id;
END$$
DELIMITER ;

-- 更新投票項目
DELIMITER $$
CREATE PROCEDURE update_vote(
    IN vote_id BIGINT,
    IN vote_name VARCHAR(255),
    IN votes_num INT
)
BEGIN
UPDATE vote
SET name = vote_name, votes_num = votes_num
WHERE id = vote_id;
END$$
DELIMITER ;

-- 刪除投票項目
DELIMITER $$
CREATE PROCEDURE delete_vote(IN vote_id BIGINT)
BEGIN
    -- 先刪除 vote_record 表中的依賴記錄
DELETE FROM vote_record WHERE vote_item_id = vote_id;

-- 然後刪除 vote 表中的記錄
DELETE FROM vote WHERE id = vote_id;
END$$
DELIMITER ;

-- 對項目進行投票
DELIMITER $$
CREATE PROCEDURE vote_for_item(
    IN voterName VARCHAR(255),
    IN voteItemId BIGINT
)
BEGIN
    -- 更新投票數
UPDATE vote v
SET v.votes_num = v.votes_num + 1
WHERE v.id = voteItemId;

-- 插入投票紀錄
INSERT INTO vote_record (voter_name, vote_item_id)
VALUES (voterName, voteItemId);
END$$
DELIMITER ;

-- 查詢所有投票紀錄
DELIMITER $$
CREATE PROCEDURE get_vote_records()
BEGIN
SELECT
    vr.id AS vote_record_id,
    vr.voter_name,
    v.id AS vote_item_id,
    v.name AS vote_item_name,
    v.votes_num AS vote_item_votes_num
FROM vote_record vr
         LEFT JOIN vote v ON vr.vote_item_id = v.id; -- 聯結 vote 表以獲取相關數據
END$$
DELIMITER ;