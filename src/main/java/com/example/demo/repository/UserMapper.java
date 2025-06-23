package com.example.demo.repository;

import com.example.demo.domain.AuthVO;
import com.example.demo.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
  // 회원 가입
  int userJoin(UserVO uvo);
  
  // 회원 가입 시 권한 부여
  int userJoinAuth(String id);

  // id 로 유저 정보 불러오기
  UserVO getUser(String username);

  // getUser 로 불러온 정보로 Auth 불러오기
  List<AuthVO> getUserAuth(String username);

  // mypage 에서 모든 정보 업데이트
  void allUpdate(UserVO uvo);

  // mypage 에서 파일만 제외한 업데이트
  void subFileUpdate(UserVO uvo);

  // mypage 에서 비밀번호만 제외한 업데이트
  void subPwdUpdate(UserVO uvo);

  // mypage 에서 파일과 비밀번호만 제외한 업데이트
  void subFilePwdUpdate(UserVO uvo);
}
