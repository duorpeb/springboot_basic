package com.example.demo.repository;

import com.example.demo.domain.BoardVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
public interface BoardMapper {
  // 게시글 등록
  int insert(BoardVO bvo);
  
  // 게시글 리스트 불러오기
  List<BoardVO> getList();

  // 게시글 상세조회
  BoardVO getBvo(long bno);
  
  // 게시물 삭제
  int delete(long bno);

  // 게시물 수정
  int modify(BoardVO bvo);
}
