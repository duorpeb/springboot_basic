package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class PagingVO {
  // 초기화
   // 페이징네이션
  private int pageNo;
  private int qty;
   /* 검색
   *
   * */
  private String type;
  private String keyword;


  // 생성자 - 페이징 값을 초기화 하지 않는 경우
  public PagingVO(){
    this.pageNo = 1;
    this.qty = 10;
  }

  // 생성자 - 선택한 셀렉트 (type) 와 검색내용 (keyword) 이 없는 경우의 생성자
  public PagingVO(int pageNo, int qty){
    this.pageNo = pageNo;
    this.qty = qty;
  }

  // 메서드 - 시작 페이지를 정하는 메서드
  public int getStartIdx(){
    return (this.pageNo - 1) * this.qty;
  }

  // 메서드 - type 을 분리하는 메서드
  public String[] getTypeToArray(){
    // 셀렉트가 null 이면 빈 배열을 반환하고 아닌 경우 한 글자씩 분리
    return this.type == null ? new String[]{} : this.type.split("");
  }
}
