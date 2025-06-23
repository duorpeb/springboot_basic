package com.example.demo.domain;

import lombok.*;

/** comment TABLE
 *
 * CREATE TABLE comment(
 *  cno BIGINT AUTO_INCREMENT PRIMARY KEY,
 *  bno BIGINT NOT NULL,
 *  file_name VARCHAR(256),
 *  writer VARCHAR(256) NOT NULL,
 *  content TEXT,
 *  reg_date DATETIME DEFAULT NOW()
 * );
 *
 * */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {
  private long cno;
  private long bno;
  private String fileName;
  private String writer;
  private String content;
  private String regDate;
}
