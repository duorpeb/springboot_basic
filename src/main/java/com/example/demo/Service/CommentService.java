package com.example.demo.Service;

import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.handler.PagingHandler;
import org.apache.ibatis.annotations.Param;

public interface CommentService {
  // 댓글 목록 불러오기
  PagingHandler getList(PagingVO pgvo, long bno);

  // 댓글 추가
  int postCmt(CommentVO cvo);

  // 댓글 수정
  int updateCmt(CommentVO cvo);

  // 댓글 삭제
  int deleteCmt(long cno, long bno);
}
