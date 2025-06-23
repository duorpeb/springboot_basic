package com.example.demo.handler;

import com.example.demo.domain.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class FileRemoveHandler {
  // 초기화
   // 저장된 위치에서 삭제해야하기에 저장된 경로 초기화
  private final String DEL_DIR = "D:\\Jstl_Servlet_Spring\\_myProject\\_Java\\_fileUpload";

  // delFile() - 파일 삭제 메서드
  public int delFile(FileVO fvo){
    // 초기화
     // 상태를 전달하기 위한 확인용 변수
    boolean isDel = false;
     // 파일 객체 생성 - 이 객체 자체는 디스크에 어떤 작업도 하지않고 단순히 그 경로 (디렉터리나 파일) 를 가리키는 역할만 수행
     // File 변수명 = new File(parent, child);
    File fileDir = new File(DEL_DIR, fvo.getSaveDir());

    /**
     * > parent (fileDir) 가 최하위 폴더라면 child 파일을 가리킴
     *
     * > child 에 파일명 적기
     *  # 일반 파일명 형식 : uuid + "_" + File.getOriginalFileName()
     *
     *  # 이미지 파일인 경우 썸네일의 파일명 형식 : uuid + "_th_" + File.getOriginalFileName()
     * */
     //
     // 일반 파일
    File delFile = new File(fileDir, fvo.getUuid() + "_" + fvo.getFileName());
     // 썸네일
    File delThFile = new File(fileDir, fvo.getUuid() + "_th_" + fvo.getFileName());
    log.info("delFile : " + delFile.toString());
    log.info("delThFile : " + delThFile.toString());

    // 파일이 존재한다면 삭제
    try {
      if(delFile.exists()){
        log.info("delFile.exists() = True");

        // 파일 삭제
        isDel = delFile.delete();
        // 확인
        log.info(delFile.getName() + "File Success Delete..!");

        // 이미지 파일이라면
        if(fvo.getFileType() == 1 && delThFile.exists()){
          log.info("delThFile.exists() = True");

          // 썸네일 삭제
          isDel = delThFile.delete();
          log.info(delThFile.getName() + "File Success Delete..!");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    log.info("isDel {}", isDel);

    return isDel ? 1 : 0;
  }
}
