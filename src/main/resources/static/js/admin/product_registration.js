class ProductMst{
  #category;
  #name;
  #price;
  #simpleInfo;
  #detailInfo;
  #optionInfo;
  #managementInfo;
  #shippingInfo;

  constructor(category,name,price,simpleInfo,detailInfo,optionInfo,managementInfo,shippingInfo){
    this.#category = category;
    this.#name=name;
    this.#price=price;
    this.#simpleInfo=simpleInfo;
    this.#detailInfo=detailInfo;
    this.#optionInfo=optionInfo;
    this.#managementInfo=managementInfo;
    this.#shippingInfo=shippingInfo;
  }

  getCategory(){return this.#category;}
  setCategory(category){this.#category = category;}

  getName(){return this.#name;}
  setName(name){this.#name = name;}

  getPrice(){return this.#price;}
  setPrice(price){this.#price = price;}

  getSimpleInfo(){return this.#simpleInfo;}
  setSimpleInfo(simpleInfo){this.#simpleInfo =simpleInfo ;}

  getDetailInfo(){return this.#detailInfo;}
  setDetailInfo(detailInfo){this.#detailInfo = detailInfo;}

  getOptionInfo(){return this.#optionInfo;}
  setOptionInfo(optionInfo){this.#optionInfo = optionInfo;}

  getManagementInfo(){return this.#managementInfo;}
  setManagementInfo(managementInfo){this.#managementInfo =managementInfo ;}

  getShippingInfo(){return this.#shippingInfo;}
  setShippingInfo(shippingInfo){this.#shippingInfo =shippingInfo ;}

getObject(){
  const obj = {
    category : this.#category,
    name : this.#name,
    price : this.#price,
    simpleInfo : this.#simpleInfo,
    detailInfo : this.#detailInfo,
    optionInfo : this.#optionInfo,
    managementInfo : this.#managementInfo,
    shippingInfo : this.#shippingInfo,
    }
    return obj;
  }
}


class CommonApi {
  getCategoryList() {
      let responseResult = null;

      $.ajax({
          async:false,
          type: "get",
          url:"/api/admin/product/category",
          dataType: "json",
          success: (response) => {
              responseResult = response.data;
          },
          error: (error) => {
              console.log(error);
          }
      
      });
  
      return responseResult;
  
  }
}


class ProductApi {
  createProductRequest(productMst) {
    let responseData = null;

    $.ajax({
      async: false,
      type: "post",
      url:"/api/admin/product",
      contentType: "application/json",
      data: JSON.stringify(productMst),
      dataType: "json",
      success : (response) => {
        responseData = response.data;
      },
      error: (error) => {
      console.log(error);
   }
 });

 return responseData;
  }

  getProductListRequest(listRequestParams) {
    let responseData = null;
     

    $.ajax({
      async: false,
      type:"get",
      url:"/api/admin/products",
      data: listRequestParams, //객체 그자체로 넣어주면됨
      dataType: "json",
      success: (response) => {
          responseData = response.data
      },
      error:(error) =>{
          console.log(error);
      }
    })
  }
}


class RegisterEventService{
  

  #categorySelectObj;
  #nameInputObj;
  #priceInputObj;
  #registButtonObj;
  #infoTextareaObjs;

  constructor(){
    this.#categorySelectObj = document.querySelectorAll(".product-inputs")[0];
    this.#nameInputObj = document.querySelectorAll(".product-inputs")[1];
    this.#priceInputObj = document.querySelectorAll(".product-inputs")[2];
    this.#registButtonObj= document.querySelector(".register-button");
    this.#infoTextareaObjs = document.querySelectorAll(".product-inputs");

    this.init();

    this.addCategorySelectEvent();
    this.addNameInputEvent();
    this.addPriceInputEvent();
    this.addRegistButtonEvent();

  } 

  init(){
    this.#nameInputObj.disabled = true;
    this.#priceInputObj.disabled = true;
    this.#registButtonObj.disabled = true;
  }

  addCategorySelectEvent() {
    this.#categorySelectObj.onchange = () => {
      if(this.#categorySelectObj.value != "none"){
        this.#nameInputObj.disabled = false;
      }else {
        this.#nameInputObj.disabled = true;
    }
  }
}
  addNameInputEvent() {
  this.#nameInputObj.onkeyup = () => {
    if(this.#nameInputObj.value.length != 0){
      this.#priceInputObj.disabled = false;
    }else {
      this.#nameInputObj.disabled = true;
    }
  }
}
addPriceInputEvent() {
  this.#priceInputObj.onkeyup = () => {
    const registerInfo = document.querySelector(".regist-info")

    if(this.#priceInputObj.value.length != 0){
      this.#registButtonObj.disabled = false;
      registerInfo.classList.remove("regist-info-invisible");
    }else {     
      this.#registButtonObj.disabled = true;
      registerInfo.classList.add("regist-info-invisible");
    }
  }
}

addRegistButtonEvent(){
  this.#registButtonObj.onclick = () => {

    const category = this.#categorySelectObj.value;
    const name = this.#nameInputObj.value;
    const price = this.#priceInputObj.value;

    const simpleInfo = this.#infoTextareaObjs[3].value;
    const detailInfo = this.#infoTextareaObjs[4].value;
    const optionInfo = this.#infoTextareaObjs[5].value;
    const managementInfo = this.#infoTextareaObjs[6].value;
    const shippingInfo = this.#infoTextareaObjs[7].value;

    const productMst = new ProductMst(
      category,name,price,simpleInfo,detailInfo,optionInfo,
      managementInfo,shippingInfo
      );

      const product = new ProductApi();
      if(product.createProductRequest(productMst.getObject())){
        alert("상품 등록 완료");
        location.reload();
      }
    }
  }
}

const ProductRegistration = {
  initRegisterEvent: () => {
    RegisterEventService.init();
    RegisterEventService.addCategorySelectEvent();
    RegisterEventService.addNameInputEvent();
    RegisterEventService.addPriceInputEvent();
    RegisterEventService.addRegistButtonEvent();
  }
}

class RegisterService {
  static #instance = null;

  constructor() {

  }

  static getInstance() { //생성 안하고 바로 호출가능
      if(this.#instance == null){
          this.#instance = new RegisterService();
      }
      return this.#instance;
  }

  loadRegister() {
      
  }

  getCategoryList() {
      const commonApi = new CommonApi();
      const productCategoryList = commonApi.getCategoryList();

      const productCategory = document.querySelector(".product-category");
      productCategory.innerHTML = "";

      productCategoryList.forEach(category => {
          productCategory.innerHTML += `productMst
              <option value="${category.id}">${category.name}</option>
          `;
      })
  }
  
  setRegisterHeaderEvent() {
      new RegisterEventService();
  }
}

class ListService {
  static #instance = null;

  getInstance(){
    if(this.#instance == null){
      this.#instance = new ListService();
    }
    return this.#instance;
  }
}

window.onload = () => {
  RegisterService.getInstance().getCategoryList();
  RegisterService.getInstance().setRegisterHeaderEvent();
}