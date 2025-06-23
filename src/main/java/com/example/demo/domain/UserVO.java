package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/** CREATE TABLE user(
     id VARCHAR(256) PRIMARY KEY,
     email VARCHAR(256) NOT NULL,
     pwd VARCHAR(256) NOT NULL,
     nick_name VARCHAR(256) UNIQUE,
     gender VARCHAR(20) NOT NULL,
     terms VARCHAR(5) NOT NULL DEFAULT 'Y',
     reg_date DATETIME DEFAULT NOW(),
     last_login DATETIME DEFAULT NOW(),
     file_name VARCHAR(256),
     save_dir VARCHAR(256)
   );
 *
 * */
@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
  private String id;
  private String email;
  private String pwd;
  private String nickName;
  private String gender;
  private String terms;
  private String regDate;
  private String lastLogin;
  private String fileName;
  private String saveDir;
  private List<AuthVO> authList;
}
