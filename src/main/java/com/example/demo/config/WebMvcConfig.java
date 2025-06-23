package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// alt + insert 로 Generate 관련 옵션 실행
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  // 초기화
  String upPath = "file:///D:\\Jstl_Servlet_Spring\\_myProject\\_Java\\_fileUpload\\";

  // fileupload 경로 설정
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/upload/**").addResourceLocations(upPath);

    // 일반적인 정적 리소스 핸들러 (기본 설정)
    registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");

    /** 이미지 파일을 가져오기 위한 경로 설정
     *
     * > "/spring_myproject_up/profileImg/*" 의 요청을
     *   D:\Jstl_Servlet_Spring\spring_myproject_up\profileImg\ 폴더로 매핑
     *
     * */
    registry.addResourceHandler("/spring_myproject_up/profileImg/**")
        .addResourceLocations("file:///D:/Jstl_Servlet_Spring/spring_myproject_up/profileImg/");
  }


}
