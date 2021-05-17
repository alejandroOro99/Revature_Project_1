let loginBtn = document.getElementById("loginBtn");
let applyBtn = document.getElementById("applyBtn");

//set api messages constants for messages between server and browser
let apiMessages = new Map();
apiMessages.set("success",1);
apiMessages.set("failure",0);
apiMessages.set("exception",-1);


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
        let url = "http://localhost:9000/customer/login";//post url for login feature

        if(data.username&&data.password){
            fetch(url, {
                method: 'POST',
                body: JSON.stringify(data),
                headers: {"Content-type": "application/json; charset=UTF-8"}
            }).then((response) => response.json()) 
            .then((json) =>{
        
                let jsonResponse = json;
                console.log(jsonResponse)
        
                if(jsonResponse === apiMessages.get("success")){
                    saveLogin(data.username, data.password);
                    window.location.replace("../customerPortal/customerPortal.html"); 
                }else if(jsonResponse === apiMessages.get("failure")){
                    console.log("Username or password incorrect");
                    document.getElementById("bodyDiv").innerHTML = "<p>Username or password incorrect</p>";
                }else{
                    console.log("Exception thrown");
                    document.getElementById("bodyDiv").innerHTML = "<p>Server error</p>";
                }
               
            } );
        }else{
            console.log("All fields must have values");
            document.getElementById("bodyDiv").innerHTML = "<p>All fields must have values</p>";
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

            if(json === apiMessages.get("success")){
                document.getElementById("bodyDiv").innerHTML = "<p>Applied successfully</p>";
            }else if(json === apiMessages.get("failure")){
                document.getElementById("bodyDiv").innerHTML = "<p>Username already in use</p>";
            }
            
        } );
    }else{
        console.log("All fields must have values");
        document.getElementById("bodyDiv").innerHTML = "<p>All fields must have values</p>";
    }
    
});


