package com.example.demo.Service;

import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.handler.PagingHandler;
import com.example.demo.repository.BoardMapper;
import com.example.demo.repository.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService{
  // 초기화
  private final CommentMapper cmap;
  private final BoardMapper bmap;

  // 댓글 목록 불러오기
  @Override
  public PagingHandler getList(PagingVO pgvo, long bno) {
    List<CommentVO> cvoList = cmap.getList(pgvo, bno);
    int ttc = cmap.getCmtTotal(bno);

    return new PagingHandler(ttc, pgvo, cvoList);
  }

  // 댓글 추가
  @Override
  public int postCmt(CommentVO cvo) {
    int isOk = cmap.postCmt(cvo);
    return isOk > 0 ? bmap.increCmtCnt(cvo.getBno()) : -1;
  }

  // 댓글 수정
  @Override
  public int updateCmt(CommentVO cvo) {
    int isOk = cmap.updateCmt(cvo);

    return isOk;
  }

  // 댓글 삭제
  @Override
  public int deleteCmt(long cno, long bno) {
    int isOk = cmap.deleteCmt(cno);

    return isOk > 0 ? bmap.decreCmtCnt(bno) : -1;
  }
}
