package com.example.demo.controller;

import com.example.demo.Service.UserService;
import com.example.demo.domain.UserVO;
import com.example.demo.handler.FileHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user/*")
public class UserController {
  // 초기화
  private final UserService usv;
   // passwordEncoding
  private final PasswordEncoder passwordEncoder;
   // 파일 업로더
  private final FileHandler fileHandler;

  @GetMapping("/join")
  public void join(){}

  @PostMapping("/join")
  public String join(UserVO uvo, RedirectAttributes re){
    // 패스워드 암호화
    uvo.setPwd(passwordEncoder.encode(uvo.getPwd()));
    // 암호화 후 가입
    int isOk = usv.userJoin(uvo);

    if(isOk > 0){
      re.addFlashAttribute("joinSig", isOk);
    }

    return "redirect:/";
  }

  @GetMapping("/login")
  public void login(){}

  @GetMapping("/mypage")
  public void mypage(@RequestParam("id")String id, Model m){
    UserVO uvo = usv.getUser(id);

    if(uvo.getFileName() == null){
      uvo.setFileName("basicImg.png");
    }

    m.addAttribute("uvo", uvo);
  }

  /* public String update(UserVO uvo) 
  * 
  * > 다음 코드에서 UserVO uvo 앞에 @ModelAttribute 가 생략된 형태로 @ModelAttribute 는 생략 가능
  *
  * > update 시 4개의 케이스를 따져봐야 함
  *  #Case 1_ mypage 의 모든 정보를 업데이트 하는 경우
  *
  *  #Case 2_ 파일만 제외하여 업데이트 하는 경우
  * 
  *  #Case 3_ 비밀번호만 제외하여 업데이트 하는 경우 
  * 
  *  #Case 4_ 파일과 비밀번호를 제외하고 업데이트 하는 경우
  * 
  * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
  * > ID 와 nickName 은 중복 되면 안되기에 비동기로 중복 검사를 진행한 후 변경해야 함
  * 
   * */
  @PostMapping("/update")
  public String update(UserVO uvo, RedirectAttributes re, @RequestParam(value="file", required = false)
                       MultipartFile profile, HttpServletRequest req, HttpServletResponse res) throws IOException {
    // 초기화
     // 비밀번호 변경 검사 - 변경이 있는 경우에만 비밀번호 입력
    boolean isChangedPwd = uvo.getPwd() != null && !uvo.getPwd().isEmpty();
     // 파일 업로드 검사 변수
    boolean isUploadProfile = !profile.getOriginalFilename().equals("basicImg.png");

    // 비밀번호가 변경된 경우 함호화
    if(isChangedPwd){
      // 비밀번호 암호화
      uvo.setPwd(passwordEncoder.encode(uvo.getPwd()));
    }

    // 파일이 업로드된 경우 폴더에 파일 저장 및 uvo.setFileName(file.getOriginalFilename())
    if(isUploadProfile){
      // 파일이 이미지 인지 확인
      String ct = profile.getContentType();

      // 파일이 이미지가 아니라면 alert 창을 띄움
      if(!ct.startsWith("image/")){
        re.addFlashAttribute("imgErrMsg", -1);

        return "redirect:/user/mypage?id=" + uvo.getId();
      }

      // 파일이 이미지라면 폴더에 이미지 저장후 uvo.setFileName(파일이름);
      fileHandler.upProfile(profile, uvo);
    }

    // update
     // case 1 - 모든 정보 업데이트
    if(isChangedPwd && isUploadProfile){
      usv.allUpdate(uvo);

    } // case 2 - 파일만 제외하고 업데이트
      else if(isChangedPwd){
        usv.subFileUpdate(uvo);

    } // case 3 - 비밀번호만 제외하고 업데이트
      else if(isUploadProfile){
        usv.subPwdUpdate(uvo);

    } // case 4 - 파일과 비밀번호를 제외하고 업데이트
      else{ usv.subFilePwdUpdate(uvo); }

    // 로그아웃 후 홈으로 이동
    logout(req, res);

    return "redirect:/";
  }

  // logout(HttpServletRequest req, HttpServletResponse res) - 로그아웃 메서드
  private void logout(HttpServletRequest req, HttpServletResponse res){
    // 로그인한 Security 의 authentication 객체 가져오기
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // 로그아웃 작업 수행
    new SecurityContextLogoutHandler().logout(req, res, authentication);
  }
}
