package com.example.demo.repository;

import com.example.demo.domain.FileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
  // 파일 추가
  int insertFile(FileVO f);

  // 파일 리스트 가져오기
  List<FileVO> getFileList(long bno);

  // 파일 삭제
  int fileDel(String uuid);
  
  // 파일 불러오기
  FileVO getFvo(String uuid);
  
  // 파일 수정
  int fileModify(FileVO fvo);
}
