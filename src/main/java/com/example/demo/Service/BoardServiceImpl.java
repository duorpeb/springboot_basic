package com.example.demo.Service;

import com.example.demo.domain.BoardVO;
import com.example.demo.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{
  // 초기화
  private final BoardMapper bmap;

  // 게시글 리스트 불러오기
  @Override
  public List<BoardVO> getList() {
    return bmap.getList();
  }

  // 게시글 등록
  @Override
  public int insert(BoardVO bvo) {
    return bmap.insert(bvo);
  }

  // 게시글 상세조회
  @Override
  public BoardVO getBvo(long bno) {
    return bmap.getBvo(bno);
  }

  // 게시글 삭제
  @Override
  public int delete(long bno) {
    return bmap.delete(bno);
  }

  // 게시물 수정
  @Override
  public int modify(BoardVO bvo) {
    return bmap.modify(bvo);
  }

}
