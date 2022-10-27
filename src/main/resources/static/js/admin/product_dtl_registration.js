class CommonApi{
  static #instance = null;
  static getInstance(){
    if(this.#instance == null){
      this.#instance = new CommonApi(); 
    } 
    return this.#instance; 
  }

  getProductMstList(){
    let responseData = null;
    $.ajax({
      async: false,
      type: "get",
      url: "/api/admin/option/products/mst",
      dataType: "json",
      success: (response) => {
        responseData = response.data;
      },
      error: (error) => {
        console.log(error);
      }
    });
    return responseData;
  }
  getProductSizeList(productId) {
    let responseData = null;
    $.ajax({
      async: false,
      type: "get",
      url: "/api/admin/option/products/size/" + productId,
      dataType: "json",
      success: (response) => {
        responseData = response.data;
      },
      error: (error) => {
        console.log(error);
      }
    });
    return responseData;
  }
  

}
//common은 공통으로 쓰여지는것.

class ProductApi{
  //싱글톤으로 쓰여야함.
  
  //추가니까 포스트요청
  //객체로받을거임.
  static #instance = null;
  static getInstance(){
    if(this.#instance == null){
      this.#instance = new ProductApi(); 
    } 
    return this.#instance; 
  }

  registProductDtl(productDtlParams){
    //응답데이터가 필요없으니 바로 에이잭스열어줌
    $.ajax({
      async: false,
      type: "post",
      url: "/api/admin/product/dtl",
      contentType: "application/json",
      data: JSON.stringify(productDtlParams),
      dataType: "json",
      success: (response) =>{
          alert("추가 완료!");
          location.reload();
      },
      error: (error) => {
        console.log(error);
        alert(`상품 추가 실패.
        ${error.responseJSON.data.error}
        `)
      }
    })
  }



  registImgFiles(formData){
    $.ajax({
      async: false,
      type: "post",
      url: "/api/admin/product/img",
      enctype: "multipart/form-data", //파일전송필수
      contentType: false, //파일전송필수
      processData: false, //파일전송필수
      data: formData,
      dataType: "json",
      success: (response) => {
        alert("이미지 등록 완료");
        location.reload();
      },
      error:(error) => {
        console.log(error);
      }

    });
  }
}

class Option {
  static #instance = null;
  static getInstance(){
    if(this.#instance == null){
      this.#instance = new Option(); 
    } 
    return this.#instance; 
  }
  constructor(){
    this.setProductMstSelectOptions();
    this.addSubmitEvent();
  }


  setProductMstSelectOptions() {
    const pdtMstSelect = document.querySelector(".product-select");
    const responseData = CommonApi.getInstance().getProductMstList();
    if(responseData != null){
    if(responseData.length > 0) {
        responseData.forEach(product => {
        pdtMstSelect.innerHTML += `
        <option value="${product.pdtId}">(${product.category})${product.pdtName}</option> 
          `;
        });
      }
    this.addMstSelectEvent();
    }
  }


  addMstSelectEvent() {
    const pdtMstSelect = document.querySelector(".product-select");
    pdtMstSelect.onchange = () => {
        this.setSizeSelectOptions(pdtMstSelect.value);
    }
  }

  setSizeSelectOptions(productId){
    const pdtSizeSelect = document.querySelector(".product-size");
    pdtSizeSelect.innerHTML = "";

    CommonApi.getInstance().getProductSizeList(productId).forEach(size => {
      pdtSizeSelect.innerHTML += `
      <option value="${size.sizeId}">${size.sizeName}</option> 
      `;
    })
  }

  addSubmitEvent() {
    const registButton = document.querySelectorAll(".regist-button")[0];

    registButton.onclick = () =>{
      const productDtlParams = {
        //객체를 여기에 넣음
          "pdtId": document.querySelector(".product-select").value,
          "pdtSize": document.querySelector(".product-size").value,
          "pdtColor": document.querySelector(".product-color").value,
          "pdtStock": document.querySelector(".product-stock").value
      }
      ProductApi.getInstance().registProductDtl(productDtlParams);
    }
  }
}

class ProductImgFile {
  static #instance = null;
  static getInstance() {
    if(this.#instance == null){
      this.#instance = new ProductImgFile();
    }
    return this.#instance;
  }

  newImgList = new Array();


  constructor(){
    this.addFileInputEvent();
  }

  addUploadEvent() {
    const uploadButton = document.querySelector(".upload-button");
    uploadButton.onclick = () => {
      const formData = new FormData();

      const productId = document.querySelector(".product-select").value;
      formData.append("pdtId", productId);  


      //리스트로 잡힘.
      this.newImgList.forEach(imgFile => {
        formData.append("files", imgFile);
      });
      ProductApi.getInstance().registImgFiles(formData);
    }
  }

  addFileInputEvent() {
      const filesInput = document.querySelector(".files-input");
      const imgAddButton = document.querySelector(".img-add-button");
      imgAddButton.onclick = () => {
        filesInput.click();
    }
    //폼 데이터 안에는 인풋의 정보들이 담겨져있다.
      filesInput.onchange = () => {
        const formData = new FormData(document.querySelector("form"));
    
        let changeFlag = false;

        //파일의 용량 크기.
        formData.forEach(value => {
          if(value.size != 0){
              this.newImgList.push(value);
              changeFlag = true;
          }
        })

        if(changeFlag){
            this.loadImgs();
            filesInput.value = null;  
          }

      }  
  }
  loadImgs() {
      const fileList = document.querySelector(".file-list");
      fileList.innerHTML = "";

      this.newImgList.forEach((imgFile, i) => {
          const reader = new FileReader();
          //리더가 읽어서 이미지에대한 정보를 URL로만들어서 줌
          reader.onload = (e) => {
            //이미지 주소 e.target.result
            //비동기임 밑에 //순서에 타임을 줄것
              fileList.innerHTML += `
              <li class="file-info">
                <div class="file-img">
                  <img src="${e.target.result}">
                </div>
                <div class="file-name">${imgFile.name}</div>
                <button type="button" class="btn delete-button">삭제</button>
              </li>
              `;
            }
            //비동기처리 readAsDataURL 
            //비동기처리 순차적으로 오다가 데이터를 받아와야지만 띄울수있는애면 비동기처리로 빼버림 보류해놓음.
            //보류후 나머지애들 처리 그러고나서 보류해놓은것들을 순차적으로 처리
            //동기처리는 순차적으로
            //ajax  async : false 를 주면 비동기처리가됨.
          

            //이 메소드 호출을 시간단위로 줄것.
          setTimeout(() => {
            reader.readAsDataURL(imgFile)
          }, i * 300);
      }); 
      setTimeout(() => {
          this.addDeleteEvent();
      },this.newImgList.length * 300);
  }
  addDeleteEvent(){
    const deleteButtons = document.querySelectorAll(".delete-button");

    deleteButtons.forEach((deleteButton, i) => {
      deleteButton.onclick = () => {
        if(confirm("상품을 지우시겠습니까?")) {
            this.newImgList.splice(i, 1);
            this.loadImgs();
        }
      }
    });
  }
}

window.onload = () => {
  ProductImgFile.getInstance();
  Option.getInstance();
}