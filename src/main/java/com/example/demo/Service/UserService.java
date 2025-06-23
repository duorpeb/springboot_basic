package com.example.demo.Service;

import com.example.demo.domain.UserVO;
import org.springframework.stereotype.Service;


public interface UserService {
  // 회원 가입
  int userJoin(UserVO uvo);

  // id 로 회원 정보 불러오기
  UserVO getUser(String id);

  // mypage 에서 모든 정보 업데이트
  void allUpdate(UserVO uvo);

  // mypage 에서 파일만 제외한 업데이트
  void subFileUpdate(UserVO uvo);

  // mypage 에서 비밀번호만 제외한 업데이트
  void subPwdUpdate(UserVO uvo);

  // mypage 에서 파일과 비밀번호를 제외한 업데이트
  void subFilePwdUpdate(UserVO uvo);
}
