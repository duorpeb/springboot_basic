package com.example.demo.config;

import com.example.demo.security.CustomAuthUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/** SecurityConfig
 *
 * > 이 클래스는 Spring Boot 의 Security 설정을 위한 핵심 구성 클래스
 *
 * > Spring Boot 에서는 Spring 에서 사용했던 SecurityInitializer 가 없어도 됨
 *  # SecurityInitializer 코드
 *   public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer{ }
 *
 * >
 *
 * */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

  /** PasswordEncoder
   *
   * > 사용자 비밀번호를 BCrypt 해시 방식으로 암호화 및 검증하는 역할을 하며 Spring 의 BCryptPasswordEncoder 역할
   *
   * >
   *
   */
  @Bean
  PasswordEncoder passwordEncoder(){
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }


  /** SecurityFilterChain
   *
   * > Spring 에서 사용했던 SecurityInitializer 역할
   * */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    /** 아래 코드 설명
     *
     * > csrf.disable() 을 설정하고 싶다면 다음과 같이 설정
     *  # return http.csrf(csrf -> csrf.disable()). ~ . build();
     *
     * > csrf() 상태로 설정하고 싶다면 다음과 같이 설정
     *  # http.csrf(csrf -> {})
     *
     * */
    return http.csrf(csrf -> {})
        // Security 설정에서 익명 인증을 비활성화하여 an
//        .anonymous(anon -> anon.disable())
        // 권한 별 경로 접근 설정
        .authorizeHttpRequests(authorize
            -> authorize.requestMatchers("/user/list").hasRole("ADMIN")
            .requestMatchers("/", "/index","/js/**","/dist/**", "/img/**","/upload/**"
                ,"/board/list","/board/detail","/user/login","/user/join","/comment/**","/spring_myproject_up/profileImg/**")
            .permitAll()
            .anyRequest().authenticated())
        // 로그인
        .formLogin(login
            -> login.usernameParameter("id")
            .passwordParameter("pwd")
            .loginPage("/user/login")
            .loginProcessingUrl("/user/login")
            .defaultSuccessUrl("/board/list")
            .failureUrl("/user/login")
            .permitAll()
        )
        // 로그아웃
        .logout(logout
            -> logout.logoutUrl("/user/logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/"))
        .build();
  }


  /** UserDetailsService
   *
   * > Srping 의 public UserDetailsService customDetailService() {
   * 		return new CustomAuthUserService();
   * 	} 와 같은 역할을 하는 클래스 
   * */
  @Bean
  UserDetailsService userDetailsService(){
    // 커스텀 객체 생성
    return new CustomAuthUserService();
  }
  
  /**
   * > authenticationManager 객체 (인증용 객체 생성 매니저) 설정
   * */
  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
  
  
}
