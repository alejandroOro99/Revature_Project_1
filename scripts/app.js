let loginBtn = document.getElementById("loginBtn");
let applyBtn = document.getElementById("applyBtn");
let employeeLoginBtn = document.getElementById("employeeLoginBtn");
let employeeApplyBtn = document.getElementById("employeeBtn");

function saveLogin(username, password){

    let loginData = {
        Username: username,
        Password: password
    };


    loginData = JSON.stringify(loginData);
    localStorage.setItem('_loginData',loginData);

}

//LOGIN feature
loginBtn.addEventListener("click",()=>{

    localStorage.removeItem("_loginData");
    let data = {
        username: document.forms["loginForm"]["usernameInput"].value,
        password: document.forms["loginForm"]["passwordInput"].value
    }
    if(data.username === "admin" && data.password === "admin"){
        window.location.replace("employeePortal.html");
    }else{
        let url = "http://localhost:9000/customer/login";//post url for login feature

        fetch(url, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {"Content-type": "application/json; charset=UTF-8"}
        }).then((response) => response.json()) 
        .then((json) =>{
    
            let jsonResponse = json;
            console.log(jsonResponse)
    
            if(jsonResponse==="login success"){
                saveLogin(data.username, data.password);
                window.location.replace("customerPortal.html"); 
            }
           
        } );
    }
    
  
});

//APPLY feature
applyBtn.addEventListener("click",()=>{

    let data = {
        username: document.forms["applyForm"]["usernameInput"].value,
        password: document.forms["applyForm"]["passwordInput"].value,
        firstname: document.forms["applyForm"]["firstnameInput"].value,
        lastname: document.forms["applyForm"]["lastnameInput"].value
    }

    let url = "http://localhost:9000/customer/apply";//post url for login feature

    if(data.username&&data.password&&data.firstname&&data.lastname){
        fetch(url, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {"Content-type": "application/json; charset=UTF-8"}
        }).then((response) => response.json()) 
        .then((json) =>{
    
            console.log(json);
        } );
    }else{
        console.log("All fields must have values");
    }
    
});
//create new employee feature

employeeApplyBtn.addEventListener("click",()=>{
    let data = {
        username: document.forms["employeeApplyForm"]["employeeUserInput"].value,
        password: document.forms["employeeApplyForm"]["employeePasswordInput"].value,
        firstname: document.forms["employeeApplyForm"]["employeeFirstInput"].value,
        lastname: document.forms["employeeApplyForm"]["employeeLastInput"].value
    }

    let url = "http://localhost:9000/employee/apply";//post url for login feature

    if(data.username&&data.password&&data.firstname&&data.lastname){
        fetch(url, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {"Content-type": "application/json; charset=UTF-8"}
        }).then((response) => response.json()) 
        .then((json) =>{
    
            console.log(json);
        } );
    }else{
        console.log("All fields must have values");
    }
});

//login feature
employeeLoginBtn.addEventListener("click",()=>{

    let data = {
        username: document.forms["employeeLoginForm"]["usernameInput"].value,
        password: document.forms["employeeLoginForm"]["passwordInput"].value,
    }

    let url = "http://localhost:9000/employee/login";//post url for login feature

    if(data.username&&data.password){
        fetch(url, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {"Content-type": "application/json; charset=UTF-8"}
        }).then((response) => response.json()) 
        .then((json) =>{
    
            console.log(json);
            if(json == "login success"){
                window.location.replace("employeePortal.html");
            }
        } );
    }else{
        console.log("All fields must have values");
    }
});