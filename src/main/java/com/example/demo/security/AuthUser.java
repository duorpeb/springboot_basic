package com.example.demo.security;

import com.example.demo.domain.UserVO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

/** AuthUser 클래스
 *
 * > AuthUser 클래스는 Spring Security 의 org.springframework.security.core.userdetails.User 클래스를 상속한
 *   사용자 클래스로 로그인 시 인증에 필요한 정보를 사용자 임의로 커스텀
 *
 * > 또한, AuthUser 클래스는 CustomUserDetailsService 클래스에서 사용자를 인증할 때 반환할 때, 사용되며
 *   SecurityContextHolder 에 저장될 사용자 정보를 확장할 때 사용
 *
 *
 *
 * */
public class AuthUser extends User {
  // 초기화
  @Getter
  private UserVO uvo;

  /** 생성자 - AuthUser(String username, String password, Collection GrantedAuthority authorities)
   *
   * > User 클래스의 생성자를 그대로 받는 형태로 권한이 GrantedAuthority 로 전달되어야 하며 이 권한 컬렉션은
   *   Spring Security 에서 인증 처리를 위해 필요
   *
   * > GrantedAuthority 란 Spring Security 에서 "권한" 을 나타내는 인터페이스로 한 사용자에게 여러 개의 권한을 부여할 수 있음
   *
   * > AuthUser(UserVO uvo) 생성자 작성 시 authorities 는 GrantedAuthority 형식에 맞게 매개변수를 주어야 함
   *
   * > Java 제네릭에서 ? extends 는 상위 클래스 (인터페이스) 를 상속한 모든 타입을 허용한다는 의미로
   *   "Collection ? extends GrantedAuthority authorities" 를 통해  SimpleGrantedAuthority, CustomGrantedAuthority
   *   등 그 외 GrantedAuthority 를 구현한 어떤 객체든 받을 수 있게 함
   *
   * > ? extends GrantedAuthority 는 GrantedAuthority 의 하위 타입 허용 (읽기 전용)
   *   ? super GrantedAuthority 는 상위 타입 허용 (쓰기용 컬렉션에서 자주 사용)
   *   Collection"<"GrantedAuthority">" 는	정확히 GrantedAuthority 타입만 허용 (하위 타입 불가)
   * */
  public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }


  /** 생성자 - AuthUser(UserVO uvo)
   *
   * > 이 생성자가 실제로 UserVO 객체 기반의 사용자 인증 정보 등록에 사용됨
   *
   * > "uvo.getAuthList() ~ ..." 부분은 사용자 권한 목록을 가져와 각 권한 문자열을 SimpleGrantedAuthority 로 변환하여
   *   GrantedAuthority 컬렉션으로 만듦
   *
   * */
  public AuthUser(UserVO uvo){

    super(uvo.getId(),
        uvo.getPwd(),
        uvo.getAuthList()
            .stream()
            .map(authVO -> new SimpleGrantedAuthority(
              authVO.getAuth()))
            .collect(Collectors.toList()));

    this.uvo = uvo;
  }
}
