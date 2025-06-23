package com.example.demo.Service;

import com.example.demo.domain.UserVO;
import com.example.demo.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
  // 초기화
  private final UserMapper umap;

  // 회원 가입
  @Override
  public int userJoin(UserVO uvo) {
    int isOk = umap.userJoin(uvo);

    if(isOk > 0){
      umap.userJoinAuth(uvo.getId());

      return isOk;
    }

    return -1;
  }
  
  // id 로 유저 정보 불러오기
  @Override
  public UserVO getUser(String id) {
    return umap.getUser(id);
  }

  // mypage 에서 모든 정보 업데이트
  @Override
  public void allUpdate(UserVO uvo) {
    umap.allUpdate(uvo);
  }

  // mypage 에서 파일만 제외한 업데이트
  @Override
  public void subFileUpdate(UserVO uvo) {
    umap.subFileUpdate(uvo);
  }

  // mypage 에서 비밀번호만 제외한 업데이트
  @Override
  public void subPwdUpdate(UserVO uvo) {
    umap.subPwdUpdate(uvo);
  }

  // mypage 에서 파일과 비밀번호를 제외한 업데이트
  @Override
  public void subFilePwdUpdate(UserVO uvo) {
    umap.subFilePwdUpdate(uvo);
  }
}
