package com.example.demo.repository;

import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.handler.PagingHandler;
import org.apache.ibatis.annotations.Mapper;

import javax.xml.stream.events.Comment;
import java.util.List;

@Mapper
public interface CommentMapper {
  // 댓글 목록 불러오기
  List<CommentVO> getList(PagingVO pgvo, long bno);

  // 댓글 추가
  int postCmt(CommentVO cvo);

  // 해당 게시물의 전체 댓글 수 조회
  int getCmtTotal(long bno);

  // 댓글 수정
  int updateCmt(CommentVO cvo);

  // 댓글 삭제
  int deleteCmt(long cno);
}
