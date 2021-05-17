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

//login feature
employeeLoginBtn.addEventListener("click",()=>{

    localStorage.removeItem("_loginData");
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
                saveLogin(data.username, data.password);
                window.location.replace("../employeePortal/employeePortal.html");
            }else{
                document.getElementById("bodyDiv").innerHTML = json;
            }
        } );
    }else{
        console.log("All fields must have values");
        document.getElementById("bodyDiv").innerHTML = "<p>All fields must have values</p>";
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
            document.getElementById("bodyDiv").innerHTML = "<p>All fields must have values</p>";
        } );
    }else{
        console.log("All fields must have values");
        document.getElementById("bodyDiv").innerHTML = "<p>All fields must have values</p>";
    }
});