package com.example.demo.security;

import com.example.demo.domain.AuthVO;
import com.example.demo.domain.UserVO;
import com.example.demo.repository.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Slf4j
public class CustomAuthUserService implements UserDetailsService {
  // 초기화
  @Autowired
  private UserMapper umap;

  /** loadUserByUsername
   *
   * */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // username 을 주고 해당 유저의 객체를 리턴 받기
    UserVO uvo = umap.getUser(username);
     // 유저의 권한 정보 불러오기
    List<AuthVO> authList = umap.getUserAuth(username);
    uvo.setAuthList(authList);

    return new AuthUser(uvo);
  }
}
