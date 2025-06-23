package com.example.demo.Service;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.BoardVO;
import com.example.demo.domain.FileVO;
import com.example.demo.domain.PagingVO;

import java.util.List;

public interface BoardService {
  // 게시글 리스트 불러오기
  List<BoardVO> getList(PagingVO pgvo);

  // 게시글 등록
  int insert(BoardDTO bdto);

  // 게시글 상세 조회
  BoardDTO getBvo(long bno);

  // 게시글 삭제
  int delete(long bno);

  // 게시물 수정
  
  // type 과 keyword 에 맞는 전체 게시글 수 조회
  int getTTC(PagingVO pgvo);

  // 게시물 수정 시 DB 에서 파일 삭제 (비동기처리)
  int fileDel(String uuid);

  // 게시물 수정 시 폴더에서 파일 삭제
  FileVO getFvo(String uuid);
  
  // 게시물 수정
  int modify(BoardDTO bdto);
}
