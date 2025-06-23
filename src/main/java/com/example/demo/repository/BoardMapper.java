package com.example.demo.repository;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.BoardVO;
import com.example.demo.domain.PagingVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
public interface BoardMapper {
  // 게시글 등록
  int insert(BoardVO bvo);
  
  // 게시글 리스트 불러오기
  List<BoardVO> getList(PagingVO pgvo);

  // 게시글 상세조회
  BoardVO getBvo(long bno);
  
  // 게시물 삭제
  int delete(long bno);

  // 게시물 수정
  int modify(BoardVO bvo);

  // type 과 keyword 에 맞는 전체 게시글 수 조회
  int getTTC(PagingVO pgvo);

  // 파일 저장 시 최근 게시물의 bno 를 불러오기
  long getBno();

  // 댓글 수 1 증가
  int increCmtCnt(long bno);

  // 댓글 수 1 감소
  int decreCmtCnt(long bno);
}
