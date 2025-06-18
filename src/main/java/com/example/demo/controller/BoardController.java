package com.example.demo.controller;

import com.example.demo.Service.BoardService;
import com.example.demo.domain.BoardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/*")
public class BoardController {
  private final BoardService bsv;

  // 게시물 작성 페이지
  @GetMapping("/register")
  public void register(){}

  
  // 게시물 작성
  @PostMapping("/insert")
  public String insert(BoardVO bvo){
    int isOk = bsv.insert(bvo);

    return "/index";
  }

  
  // 게시물 전체 조회 
  @GetMapping("/list")
  public void list(Model model){
    List<BoardVO> list = bsv.getList();
    model.addAttribute("list", list);
  }


  // 상세 조회
  @GetMapping("/detail")
  public void detail(@RequestParam("bno") long bno, Model m){
    BoardVO bvo = bsv.getBvo(bno);
    m.addAttribute("bvo", bvo);
  }


  // 삭제
  @GetMapping("/del")
  public String delete(@RequestParam("bno") long bno, RedirectAttributes re){
    // 초기화
    String delMsg = "fail";

    int isOk = bsv.delete(bno);
    if(isOk > 0){
      delMsg = "ok";

      re.addFlashAttribute("delMsg", delMsg);
    }
    return "redirect:/board/list";
  }


  // 수정
  @PostMapping("/modify")
  public String modify(BoardVO bvo, RedirectAttributes re){
    int isOk = bsv.modify(bvo);

    if(isOk > 0){
      int modMsg = 0;

      re.addFlashAttribute("modMsg", modMsg);
    }

    return "redirect:/board/detail?bno=" + bvo.getBno();
  }
}
