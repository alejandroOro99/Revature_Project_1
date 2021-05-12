
let loginBtn = document.getElementById("loginBtn");

function saveLogin(username, password){

    let loginData = {
        Username: username,
        Password: password
    };


    loginData = JSON.stringify(loginData);
    localStorage.setItem('_loginData',loginData);

}

loginBtn.addEventListener("click",()=>{

    let data = {
        username: document.forms["loginForm"]["usernameInput"].value,
        password: document.forms["loginForm"]["passwordInput"].value
    }
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
  
});