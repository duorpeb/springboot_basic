package com.example.demo.controller;

import com.example.demo.Service.BoardService;
import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.BoardVO;
import com.example.demo.domain.FileVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.handler.FileHandler;
import com.example.demo.handler.FileRemoveHandler;
import com.example.demo.handler.PagingHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/*")
public class BoardController {
  private final BoardService bsv;
  private final FileHandler fh;

  // 게시물 작성 페이지
  @GetMapping("/register")
  public void register(){}

  
  // 게시물 작성
  @PostMapping("/insert")
  public String insert(BoardVO bvo, @RequestParam(value = "file",required = false)MultipartFile[] files, RedirectAttributes re){
    // 초기화
    int isOk;
    List<FileVO> fvoList = null;

    if(files != null && files[0].getSize() > 0){
      fvoList = fh.upFiles(files);
      log.info("board Register 에서 upFiles 시 fvoList : {}", fvoList);

    }
    isOk = bsv.insert(new BoardDTO(bvo, fvoList));

    return "redirect:/board/list";
  }

  
  // 게시물 전체 조회 
  @GetMapping("/list")
  public void list(Model model, PagingVO pgvo){
    // type 과 keyword 에 맞는 게시글 수 조회
    int ttc = bsv.getTTC(pgvo);

    // 전체 게시글 불러오기
    List<BoardVO> list = bsv.getList(pgvo);

    // PagingHandler 객체 생성
    PagingHandler pagingHandler = new PagingHandler(pgvo, ttc);

    // View 에 뿌리기
    model.addAttribute("list", list);
    model.addAttribute("ph",pagingHandler);
  }


  // 상세 조회
  @GetMapping("/detail")
  public void detail(@RequestParam("bno") long bno, Model m){
    BoardDTO bdto = bsv.getBvo(bno);

    m.addAttribute("bdto", bdto);
    m.addAttribute("bvo",bdto.getBvo());
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
  public String modify(BoardVO bvo, RedirectAttributes re, @RequestParam(value="file",required=false)MultipartFile[] files){
    // 초기화
     // 파일 리스트 초기화
    List<FileVO> fvoList = null;

    // 파일이 있다면
    if(files[0].getSize() > 0){
      // 수정된 파일 업로드
      fvoList = fh.upFiles(files);
    }

    // 수정
    BoardDTO bdto = new BoardDTO(bvo, fvoList);

    int isOk = bsv.modify(bdto);

    if(isOk > 0){
      int modMsg = 0;

      re.addFlashAttribute("modMsg", modMsg);
    }

    return "redirect:/board/detail?bno=" + bvo.getBno();
  }

  // 파일 삭제
  @ResponseBody
  @DeleteMapping("/fileDel")
  public String fileDel(@RequestParam("uuid") String uuid){
    // 확인
    log.info(uuid);

    // 저장된 폴더에서 삭제
     // 폴더에서 파일을 삭제하기 위한 파일 객체 생성
    FileVO fvo = new FileVO(); fvo = bsv.getFvo(uuid);

     // FileRemoveHandler()
    FileRemoveHandler fh = new FileRemoveHandler();
     // 파일 리무버 핸들러로 파일 삭제
    int isDel = fh.delFile(fvo);

    // DB 에서 삭제 - bsv.fileDel(uuid) > 0
    isDel *= bsv.fileDel(uuid);

    return (isDel > 0) ? "success" : "fail";
  }


}
