package com.example.demo.handler;

import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PagingHandler {
  // 초기화
  private  int startIdxPage;
  private int endIdxPage;
  private int endPage;
  private boolean prev, next;

  private  int ttc;
  private PagingVO pagingVO;
   // 댓글 페이징을 위한 멤버 변수
  private List<CommentVO> cvoList;


  // 생성자 - 1) 게시물 페이지네이션을 위한 생성자
  public PagingHandler(PagingVO pagingVO, int ttc){
    this.pagingVO = pagingVO;
    this.ttc = ttc;
    /** 10 단위로 페이징 (e.g., 1~10 -> 11~20 -> 21~30 -> ... )
     *
     * > (현재 페이지번호 / 10) 를 올림하여 10 을 곱
     */
    this.endIdxPage = (int)Math.ceil(pagingVO.getPageNo() / 10.0) * 10;
    this.startIdxPage = endIdxPage - 9;

    this.endPage = (int)Math.ceil((double)this.ttc / pagingVO.getQty());

    if(this.endPage < this.endIdxPage){
      this.endIdxPage = endPage;
    }

    this.prev = this.startIdxPage > 1;
    this.next = this.endIdxPage < this.endPage;
  }


  // 생성자 - 2) 댓글 페이지네이션을 위한 생성자
  public PagingHandler(int ttc, PagingVO pgvo, List<CommentVO> cvoList){
    // 기존 생성자 사용
    this(pgvo, ttc);
    // cvoList 초기화
    this.cvoList = cvoList;
  }
}
