// 연결 확인, console.log("boardModify in..!");

// 초기화
const modBtn = document.getElementById('modBtn');
const delBtn = document.getElementById('delBtn');
const fileAddDiv = document.getElementById('fileAddDiv');
 // detail.html 의 title/writer/content
// document.getElementById('t')
// document.getElementById('w')
// document.getElementById('c')


// 수정 완료/실패 alert
 // 확인
console.log(modMsg);
if(modMsg == 0){ alert('게시물 수정 완료!'); }


// detail.html 에서 수정 버튼을 클릭한 경우,
document.getElementById('modBtn').addEventListener('click',() => {
    // title, content의 readonly를 해지  readOnly = true / false
    document.getElementById('t').readOnly = false;
    document.getElementById('c').readOnly = false;

    // modBtn, delBtn 버튼 삭제
    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();

    //update 버튼을 regBtn 이름으로 type="submit"인 버튼 추가
    // <button type="submit" id="regBtn" class="btn btn-warning">update</button>
    let regBtn = document.createElement('button');  //<button></button>
    // button 속성 추가
    regBtn.setAttribute('type','submit');
    regBtn.setAttribute('id','regBtn');
    regBtn.classList.add('btn','btn-warning');
    regBtn.innerHTML='update';
    // form 태그의 마지막 자식으로 추가 - form 태그의 가장 마지막에 추가
    document.getElementById('form').appendChild(regBtn);

    // 수정 목록의 visibility:hidden; 을 visible 로 변환
     // 파일 선택 div (fileAddDiv)
    fileAddDiv.style.visibility = 'visible';

     // 파일 삭제 button (fileDelBtn)
    let fileDelBtn = document.querySelectorAll('.file-x');

    for(let btn of fileDelBtn){
      btn.style.visibility = 'visible';
    }

    // fileUpload 버튼 disabled 해지
    document.getElementById('trigger').disabled = false;
});


// file-x 비동기 보내서 파일 삭제
document.querySelectorAll('.file-x').forEach(fileDelBtn => {
  fileDelBtn.addEventListener('click', () => { 
    // 삭제 비동기 작업 
     // uuid 가져오기 
    let uuid = fileDelBtn.dataset.uuid;
    console.log(uuid);

     // 삭제 작업 
    fileRemoveToServer(uuid).then(result => {
      if(result == "success"){ alert('파일 삭제 완료!'); }
      fileDelBtn.closest('li').remove();
    });
  })
})


async function fileRemoveToServer(uuid) {
  try { 
    const url = `/board/fileDel?uuid=${uuid}`;

    const config = {
      method : 'delete'
    }

    const resp = await fetch(url, config);
    const result = await resp.text();

    return result;
    
  } catch (err) {
    console.log(err);
  }
}

