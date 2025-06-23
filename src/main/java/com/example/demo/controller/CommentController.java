package com.example.demo.controller;

import com.example.demo.Service.CommentService;
import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.handler.PagingHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/comment/*")
public class CommentController {
  // 초기화
  private final CommentService csv;

  @ResponseBody
  @GetMapping(value="/{bno}/{page}", produces= MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagingHandler> getList(@PathVariable("bno") long bno, @PathVariable("page") int page){
    // PagingVO 초기화
    PagingVO pgvo = new PagingVO(page, 5);

    // PagingHandler 초기화
    PagingHandler ph = csv.getList(pgvo, bno);

    return new ResponseEntity<PagingHandler>(ph, HttpStatus.OK);
  }


  /* public String post(@RequestBody CommentVO cvo){ }
   *
   * > @ResponserBody annotation 을 사용하지 않는 경우 다음과 같이 표현 가능
   *
   *    @PostMapping
   *    public String update(@RequestBody CommentVO cvo) {
   * 		  int isOk = csv.updateCmt(cvo);
   *
   * 		  return isOk > 0 ? new ResponseEntity<String>("1", HttpStatus.OK)
   * 			  : new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
   * 	 }
   * */
  @ResponseBody
  @PostMapping("/post")
  public String post(@RequestBody CommentVO cvo){
    int isOk = csv.postCmt(cvo);

    return isOk > 0 ? "1" : "0";
  }

  @ResponseBody
  @PutMapping("/update")
  public String update(@RequestBody CommentVO cvo){
    int isOk = csv.updateCmt(cvo);

    return isOk > 0 ? "1" : "0";
  }


  @ResponseBody
  @DeleteMapping("/delete")
  public String delete(@RequestParam("cno") long cno, @RequestParam("bno") long bno){
    int isOk = csv.deleteCmt(cno, bno);

    return isOk > 0 ? "1" : "0";
  }
}
