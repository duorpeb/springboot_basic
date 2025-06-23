// 초기화 
 // 댓글 등록 버튼 
const cmtAddBtn = document.getElementById('cmtAddBtn');
 // 댓글 출력 ul
const cmtUl = document.getElementById('cmtUl');
 // 댓글 입력 창 
const cmtContent = document.getElementById('cmtContent');
 // 댓글 작성자 
const cmtWriter = document.getElementById('cmtWriter');
 // csrf Token
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');


// 확인
console.log("========== detailComment.js ==========");
console.log(bno);
console.log(authNick);

// 문서 처음 로드 시 
document.addEventListener('DOMContentLoaded', () => {
  console.log("========DOMContentLoaded========");
  printCmtList(bno);
})


/** 전체 문서에 click EventListener 달기  
 * 
 * > 추가하는 click EventListener - 댓글 수정/삭제 버튼, 모달창 변경/취소 버튼  
 * 
 * */ 
document.addEventListener('click', (e) => {
  /* if(e.target.id === moreBtn){
        ...
        let page = parseInt(e.target.dataset.page);
        ...
    }
    
      > 커스텀 데이터 속성 (Custom Data Attribute)
			
	    # "data-변수명" 은 HTML5부터 사용할 수 있는 커스텀 데이터 속성으로 변수명의 이름은 사용자 임의로
		    설정이 가능 (e.g., data-value="...", data-user-id="...")
			  
	    # JS 에서 DOM 요소의 dataset 객체를 통해 접근할 수 있음 (e.g., elem.dataset.id)
			
	    # 커스텀 데이터 속성은 HTML 에 커스텀 정보를 달아두고 JS 에서 element.dataset.변수명으로 
	 	    꺼내서 사용하는 속성
    
    */ 
  // 클릭 이벤트리스너 - Case 1) 더보기 버튼  
  if(e.target.id == 'moreBtn'){
    /** page 의 값은 String 으로 반환됨 
     * 
     * <div class="mb-3">
          <button
            type="button"
            id="moreBtn"
            class="btn btn-outline-info"
            data-page="1"
            style="visibility : hidden;"
          >더보기</button>
        </div>
     * 
     * */ 
    let page = parseInt(e.target.dataset.page);
    printCmtList(bno, page);
  }

  /** 클릭 이벤트리스너 - Case 2) 댓글 수정 버튼 
   * 
   *  > 댓글의 수정 버튼을 클릭한 경우, 모달창으로 내용을 옮기고 모달창을 띄움
   * 
   * */
  if(e.target.classList.contains('mod')){
    // 해당 버튼이 달린 댓글 아이템 (li) 가져오기
    let li = e.target.closest('li');
    // 수정을 위한 cno (PK) 가져오기 
    let cno = li.dataset.cno;

    // 모달창에 댓글 내용 전달 
    document.getElementById('cmtTextMod').value = cmtContent.value;
    // 작성자 추출
    let cmtWriter = li.querySelector(".username").innerText;
    // 모달창에 작성자 표시
    document.getElementById('exampleModalLabel').innerHTML 
      = `<b>${cmtWriter}</b>`;
    // 모달창에 data-cno 초기화
    document.getElementById('cmtModBtn').setAttribute('data-cno', cno);
  }

  
  /** 클릭 이벤트리스너 - Case 3) 모달창 수정 버튼 
   * 
   *  > 모달창 수정 버튼을 클릭한 경우, 변경된 댓글의 내용을 비동기로 서버에 전송
   * 
   * */ 
  if(e.target.id == 'cmtModBtn'){
    console.log("cmdModBtn clicked..!");

    // CommentController 에 전송할 cvo 객체 생성 
    let cvo = {
      bno : bno,
      cno : e.target.dataset.cno,
      content : cmtTextMod.value
    }

    // 비동기로 전송 
    updateCmtToServer(cvo).then(result => {
      if(result == '1'){
        alert('댓글 수정 완료!');
      }

      printCmtList(bno);
    })

    // 비동기로 전송 후 모달창 닫기 
    document.querySelector('.btn-close').click();
  }


    /** 클릭 이벤트리스너 - Case 4) 댓글 삭제 버튼 
   * 
   *  > 댓글 삭제 버튼 을 클릭한 경우, 댓글 삭제 
   * 
   * */ 
  if(e.target.classList.contains('del')){
    // 해당 버튼이 달린 댓글 아이템 (li) 가져오기
    let li = e.target.closest('li');
    // 삭제를 위한 cno (PK) 가져오기
    let cno = li.dataset.cno;
    let delBno = bno;
    

    // 삭제 작업 비동기 요청 
    deleteCmtToServer(cno, delBno).then(result => {
      // 확인
      console.log("deleteCmtToServer's cno : " + cno);
      console.log("deleteCmtToServer's bno : " + bno);

      if(result == '1'){
        li.remove();
        alert('댓글 삭제 완료!');
      }
      printCmtList(bno);
    })
  }

}) // fin document.addEventListener  


/** 댓글 등록 버튼의 이벤트 리스너
 * 
 * > 댓글 버튼이 있는 경우 (로그인한 경우에만) 해당 이벤트 활성화)
 * 
 * */  
if(cmtAddBtn){
  cmtAddBtn.addEventListener('click', () => {
    // 댓글창이 비어있으면 return
    if(cmtContent.value.trim() === ''){ 
      alert('댓글을 입력해주세요!'); 
      cmtContent.focus(); return;
    }
  
    // postCmtToServer(cmtObj) 에 전달할 매개변수 객체 생성
    let cmtObj = {
      // 게시글 id
      bno : bno,
      // 댓글 작성자
      writer : cmtWriter.innerText,
      // 댓글 내용 
      content : cmtContent.value, 
      fileName : profileName
    } 
    // 확인
    console.log(cmtObj);
  
    // 서버에 비동기 요청 - comment TABLE 에 Insert
    postCmtToServer(cmtObj).then(result => {
      // 댓글이 성공적으로 등록되었으면
      if(result == '1'){ 
        alert("댓글 등록 완료!");
  
        cmtContent.value = '';
  
        // 댓글 출력
        printCmtList(cmtObj.bno);
      }
    }) 
  })
}


/** deleteCmtToServer(cno) - 댓글 삭제
 * 
 *  > 댓글 삭제 요청에도 CSRF 토큰을 전송해야 함 
 * 
 */
async function deleteCmtToServer(cno, delBno) {
  try {
    const url = `/comment/delete?cno=${cno}&bno=${delBno}`;

    const config = {
      method : 'DELETE',
      headers : {
        [csrfHeader] : csrfToken
      }
    };

    const resp = await fetch(url, config);

    const result = await resp.text();

    return result;

  } catch (error) {
    console.log(error);
  }
}


/** updateCmtToServer(bno) - 댓글 수정 
 *
 * 
 */
async function updateCmtToServer(cvo) {
  try {
    const url = "/comment/update";

    const config = {
      method : 'PUT',
      headers : {
        'Content-Type' : 'application/json; charset=utf-8',
        [csrfHeader] : csrfToken
      },
      body : JSON.stringify(cvo)
    };

    const resp = await fetch(url, config);

    const result = await resp.text();

    return result;

  } catch (error) {
    console.log(error);
  }

  
}


/** printCmtList(bno) - 해당 게시물에 작성된 댓글 출력 
 * 
 *  > 댓글 출력 형식 
 * 
 *  <ul class="comment-list" sec:authorize="isAnonymous()">
      <!-- 반복되는 댓글 아이템 -->
      <li class="comment-item">
        <div class="comment-avatar">
          <img th:src="@{|/spring_myproject_up/profileImg/${cvo.fileName }|}">
        </div>
        <div class="comment-body">
          <div class="comment-meta">
            <span class="username">${cvo.writer}</span>
            <span class="time">${cvo.regDate}</span>
          </div>
          <p class="comment-text">${cvo.content}</p>
        </div>
      </li>
    </ul>
 * 
 * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
 * > page=1 로 page 매개 변수를 전달 받지 못한 경우 page 를 1 로 초기화 
 * 
 */
function printCmtList(bno, page=1){
  console.log("=========printCmtList in=========");
  // 서버로 부터 해당 게시물의 댓글 가져오기 
  getCmtListFromServer(bno, page).then(result => {
    // List<CommentVO> cvoList 를 확인
    console.log("=========getCmtListFromServer in=========");
    console.log(result);

    // 기존에 작성된 댓글이 있는 경우 (PagingHandler 의 cvoList.length > 0)
    if(result.cvoList.length > 0){
      if(page == 1) { cmtUl.innerHTML = ''; }

      for(let cvo of result.cvoList){
        // 삭제 및 수정을 위한 dataset.cno 초기화
        let li = `<li 
                    class="comment-item"
                    data-cno="${cvo.cno}">`;
          // 유저의 프로필 사진 출력
          li += `<div class="comment-avatar">`;
            li += `<img src="/spring_myproject_up/profileImg/${cvo.fileName}">`;
          li += `</div>`; // fin comment-avator

          // 작성자, 등록일, 내용 출력
          li += `<div class="comment-body">`;
            // 작성자, 등록일 출력
            li += `<div class="comment-meta">`;
              li += `<span class="username">${cvo.writer}</span>`;
              li += `<span class="time">${cvo.regDate}</span>`;
            li += `</div>`; // fin 작성자 div
            // 내용 출력
            li += `<p class="comment-text">${cvo.content}</p>`;
          li += `</div>`;
          // 댓글 작성자와 사용자가 일치하는 경우 수정 및 삭제 버튼 출력
           // 현재 사용자 nickName 확인
          console.log("현재 사용자 nickName : " + authNick);
          
          if(authNick != undefined){
            if(cvo.writer == authNick){
                      // 수정 버튼
              li += `<button 
                        type="button" 
                        class="btn btn-outline-primary mod"
                        style="width:45px; height:35px;"
                        data-bs-toggle="modal"
                        data-bs-target="#myModal"
                      >✏️</button>`;
                      // 삭제 버튼
              li += `<button 
                        type="button" 
                        class="btn btn-outline-primary del"
                        style="width:45px; height:35px;"
                      >❌</button>`;
            }
          }
          
        // 해당 게시물의 댓글 하나 출력 끝
        li += `</li>`; // fin comment-item
  
        cmtUl.innerHTML += li;
      }
      // 더보기 버튼 활성화 및 비활성화 코드
      let moreBtn = document.getElementById('moreBtn');
       // moreBtn 의 data-page 가 PagingHandler 의 endPage 보다 작으면 더보기 버튼 활성화
       // moreBtn 의 data-page 가 PagingHandler 의 endPage 보다 크거나 같으면 더보기 버튼 비활성화
      if(result.pagingVO.pageNo < result.endPage){
        // style.visivility = "hidden" (숨김)
        // style.visivility = "visible" (표시)
        moreBtn.style.visibility = "visible";
        // data-page 의 값 1 증가
        moreBtn.dataset.page = page + 1;

      } else {
          moreBtn.style.visibility = "hidden";
      }
      
      
    } // 댓글이 없다면 (result.cvoList.length < 0)
      else {
        cmtUl.innerHTML = `<li class="list-group-item">댓글이 없습니다.</li>`;
    } 

  })
}


/** getCmtListFromServer(bno, page) - 해당 게시물의 댓글 비동기로 가져오기 
 * 
 * > select from comment where bno = #{bno}
 * 
 */
async function getCmtListFromServer(bno, page){
  try {
    // 기본 method 는 get 이기에 const resp = await fetch(`/comment/${bno}/${page}`); 로 작성 가능
    const url = `/comment/${bno}/${page}`;
  
    const resp = await fetch(url);
    // List<CommentVO> cvoList 를 리턴 받기에 resp.json()
    const result = resp.json();
  
    return result;
    
  } catch (error) {
    console.log(error);
  }
  
}


/** postCmtToServer(cmtObj) - comment TABLE 에 비동기로 Insert (댓글 작성)
 * 
 *  > cmtObj 는 bno + writer + content 를 담은 객체 
 * 
 * */
async function postCmtToServer(cmtObj) {
  try {
    const url = "/comment/post";

    const config = {
      method : 'POST',
      headers : {
        'Content-Type' : 'application/json; charset=UTF-8',
        [csrfHeader] : csrfToken
      },
      body : JSON.stringify(cmtObj)
    };

    const resp = await fetch(url, config);

    const result = await resp.text();

    return result;

  } catch (error) {
    console.log(error);
  }
}