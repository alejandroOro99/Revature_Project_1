let viewAccBtn = document.getElementById("viewAccBtn");
let viewApplicationsBtn = document.getElementById("viewApplicationsBtn");

//view customer accounts
viewAccBtn.addEventListener("click",()=>{

    let username = document.forms["viewAccForm"]["usernameInput"].value;
    let password = document.forms["viewAccForm"]["passwordInput"].value;

    let pathToUrl = username+"/"+password;
    let url = "http://localhost:9000/employee/getCustomer/"+pathToUrl;//random parameter at the end, bug with non-parameter endpoint in Javalin
    console.log(url);
    
    fetch(url)
        .then(res => res.json())
        .then(response =>{
            
            if(response != null){
                let x = 0;
                let data = "<h3>Customer accounts:</h3><br>";
                for(i in response.accounts){
                    console.log(response.accounts[i]);
                    let obj = response.accounts[i];
                   
                     data += "<p>Account name: "+obj["name"]+", Balance: $"+obj["balance"]+
                     ", AccountID: "+obj["accountID"]+"</p><br>";
                    
                    
                }
                document.getElementById("bodyDiv").innerHTML = data;
                
            }
        }); 
});

//view pending applications feature
viewApplicationsBtn.addEventListener("click",()=>{
    let username = document.forms["viewApplicationsForm"]["usernameInput"].value;

    let url = "http://localhost:9000/employee/viewApplications/"+username;//random parameter at the end, bug with non-parameter endpoint in Javalin
    console.log(url);
    
    fetch(url)
        .then(res => res.json())
        .then(response =>{
            
            if(response != null){
                let x = 0;
                let data = "<h3>Accounts pending approval:</h3><br>";
                for(i in response.accounts){
                    console.log(response.accounts[i]);
                    let obj = response.accounts[i];
                   
                     data += "<p>Account name: "+obj["account"]+"</p><br>";
                    
                    
                }
                document.getElementById("bodyDiv").innerHTML = data;
                
            }
        }); 
});