package com.example.demo.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {
  // 초기화
  private String uuid;
  private String saveDir;
  private String fileName;
  private int fileType;
  private long bno;
  private long fileSize;
  private String regDate;

  // 생성자
  public FileVO(String uuid){
    this.uuid = uuid;
  }
}
