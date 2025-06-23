package com.example.demo.Service;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.BoardVO;
import com.example.demo.domain.FileVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.repository.BoardMapper;
import com.example.demo.repository.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{
  // 초기화
  private final BoardMapper bmap;
  private final FileMapper fmap;

  // 게시글 리스트 불러오기
  @Override
  public List<BoardVO> getList(PagingVO pgvo) {
    return bmap.getList(pgvo);
  }

  // 게시글 등록, @Transactional 로 commit or rollback 으로 처리
  @Transactional
  @Override
  public int insert(BoardDTO bdto) {
    BoardVO bvo = bdto.getBvo();
    int isOk = bmap.insert(bvo);

    if(bdto.getFvoList() == null){ return -1; }

    List<FileVO> fvoList = bdto.getFvoList();

    if(bdto.getFvoList().size() > 0 && isOk > 0){

      for(FileVO f : fvoList){
        f.setBno(bmap.getBno());
        isOk *= fmap.insertFile(f);
      }
    }

    return isOk;
  }

  // 게시글 상세조회
  @Override
  public BoardDTO getBvo(long bno) {
    log.info("getBvo() 의 bno : {}", bno);

    BoardVO bvo = bmap.getBvo(bno);
    log.info("getBvo() 의 bvo : {}", bvo);

    List<FileVO> fvoList = fmap.getFileList(bno);
    log.info("getBvo() 의 fvoList : {}", fvoList);

    return new BoardDTO(bvo, fvoList);
  }

  // 게시글 삭제
  @Override
  public int delete(long bno) {
    return bmap.delete(bno);
  }

  // 게시물 수정
  @Override
  public int modify(BoardDTO bdto) {
    int isOk = bmap.modify(bdto.getBvo());

    if(bdto.getFvoList() != null){
      for(FileVO fvo : bdto.getFvoList()){
        fvo.setBno(bdto.getBvo().getBno());
        isOk *= fmap.insertFile(fvo);
      }
    }

    return isOk;
  }


  // type 과 keyword 에 맞는 전체 게시글 수 조회
  @Override
  public int getTTC(PagingVO pgvo) {
    return bmap.getTTC(pgvo);
  }

  // 게시물 수정 시 DB 에서 파일 삭제 처리 (비동기)
  @Override
  public int fileDel(String uuid) {

    return fmap.fileDel(uuid);
  }

  // 게시물 수정 시 폴더에서 파일 삭제 처리
  @Override
  public FileVO getFvo(String uuid) {
    log.info(uuid);

    return fmap.getFvo(uuid);
  }


}
