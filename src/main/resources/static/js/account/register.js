const registerButton = document.querySelector(".account-button");

registerButton.onclick = () => {
  const accountInputs = document.querySelectorAll(".account-input");

  let user = {
    lastName: accountInputs[0].value,
    firstName: accountInputs[1].value,
    email: accountInputs[2].value,
    password: accountInputs[3].value
  }

  //JSON.stringify() -> js 객체를 JSON문자열로  변환
  //JSON.parse() -> JSON문자열을 js 객체로 변환
 $.ajax ({
    async: false,             //필수
    type: "post",             //필수
    url: "/api/account/register",           //필수 
    contentType: "application/json", //전송할 데이터가 json인 경우
    data: JSON.stringify(user),  //어떤 데이터를 보낼꺼냐 (user 보냄)  //전송할 데이터가 있으면
    dataType: "json",//응답받을 데이터 타입            //json외 text 등을 사용할 수 있지만 json 사용함
    success: (response) => {                  // 성공시에 실행될 메소드
        console.log(response);x
    },
    error: (error) => {                     // 실패시에 실행될 메소드
      console.log(error.responseJSON.data); //responseJSON <- CMRespDto
      loadErrorMessage(error.responseJSON.data)//이때 에러의 데이터를 여기에 넣어줌.
    }
  }); // ajax호출방법
}

//에러들을 들고와서 반복을 시킴.
function loadErrorMessage(errors){
  const errorList = document.querySelector(".errors");
  const errorMsgs = document.querySelector(".error-msgs");
  const errorArray = Object.values(errors);

  errorMsgs.innerHTML = "";


  //에러를 가져와서 innerHTML 에러를 뽑아줌.
  errorArray.forEach(error => {
    errorMsgs.innerHTML += `
    <li>${error}</li>`;
  });

  errorList.classList.remove("errors-invisible");

}