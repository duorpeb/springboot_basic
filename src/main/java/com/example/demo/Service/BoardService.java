package com.example.demo.Service;

import com.example.demo.domain.BoardVO;

import java.util.List;

public interface BoardService {
  // 게시글 리스트 불러오기
  List<BoardVO> getList();

  // 게시글 등록
  int insert(BoardVO bvo);

  // 게시글 상세 조회
  BoardVO getBvo(long bno);

  // 게시글 삭제
  int delete(long bno);

  // 게시물 수정
  int modify(BoardVO bvo);
}
