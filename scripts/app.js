let loginBtn = document.getElementById("loginBtn");
let applyBtn = document.getElementById("applyBtn");

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