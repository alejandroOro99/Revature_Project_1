let viewAccBtn = document.getElementById("viewAccBtn");
let viewApplicationsBtn = document.getElementById("viewApplicationsBtn");
let acceptBtn = document.getElementById("acceptBtn");
let logoutBtn = document.getElementById("logoutBtn");

let byTypeBtn = document.getElementById("byTypeBtn");
let byDateBtn = document.getElementById("byDateBtn");
let byUsernameBtn = document.getElementById("byUsernameBtn");
let allTransactionsBtn = document.getElementById("allTransactionsBtn");

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

//accept applications
acceptBtn.addEventListener("click",()=>{
    let username = document.forms["acceptForm"]["usernameInput"].value;
    let accName = document.forms["acceptForm"]["nameInput"].value;
    let pathToUrl = username+"/"+accName;
    
    let url = "http://localhost:9000/employee/approve/"+pathToUrl;//random parameter at the end, bug with non-parameter endpoint in Javalin
    console.log(url);
    
    fetch(url)
        .then(res => res.json())
        .then(response =>{
            console.log(response);
        }); 
});

//by type
byDateBtn.addEventListener("click",()=>{
    let date = document.forms["transactionsForm"]["byDateInput"].value;
    let url = "http://localhost:9000/employee/transactionsDate/"+date;//random parameter at the end, bug with non-parameter endpoint in Javalin
    console.log(url);
    
    fetch(url)
        .then(res => res.json())
        .then(response =>{
            let data = "<h3>Transactions with date: "+date+"</h3><br>";
                for(i in response.transactions){
                    console.log(response.transactions[i]);
                    let obj = response.transactions[i];
                   
                     data += "<p>Account name: "+obj+"</p><br>";
                    
                    
                }
                document.getElementById("bodyDiv").innerHTML = data;
        }); 
});
//transactions bu username
byUsernameBtn.addEventListener("click",()=>{
    let username = document.forms["transactionsForm"]["byUsernameInput"].value;
    let url = "http://localhost:9000/employee/transactionsDate/"+username;//random parameter at the end, bug with non-parameter endpoint in Javalin
    console.log(url);
    
    fetch(url)
        .then(res => res.json())
        .then(response =>{
            let data="";
                for(i in response.transactions){
                    console.log(response.transactions[i]);
                    let obj = response.transactions[i];
                   
                     data += "<p>Account name: "+obj+"</p><br>";
                    
                    
                }
                document.getElementById("bodyDiv").innerHTML = data;
        }); 
});

//transactions by type
byTypeBtn.addEventListener("click",()=>{
    let type = document.forms["transactionsForm"]["byTypeInput"].value;
    let url = "http://localhost:9000/employee/transactionsDate/"+type;//random parameter at the end, bug with non-parameter endpoint in Javalin
    console.log(url);
    
    fetch(url)
        .then(res => res.json())
        .then(response =>{
            let data="";
                for(i in response.transactions){
                    console.log(response.transactions[i]);
                    let obj = response.transactions[i];
                   
                     data += "<p>Account name: "+obj+"</p><br>";
                    
                    
                }
                document.getElementById("bodyDiv").innerHTML = data;
        }); 
});
//all transactions

allTransactionsBtn.addEventListener("click",()=>{
    let url = "http://localhost:9000/employee/allTransactions/a";//random parameter at the end, bug with non-parameter endpoint in Javalin
    console.log(url);
    
    fetch(url)
        .then(res => res.json())
        .then(response =>{
            let data="";
                for(i in response.transactions){
                    console.log(response.transactions[i]);
                    let obj = response.transactions[i];
                   
                     data += "<p>Account name: "+obj+"</p><br>";
                    
                    
                }
                document.getElementById("bodyDiv").innerHTML = data;
        }); 
});

//logout
logoutBtn.addEventListener("click",()=>{
    window.location.replace("../employeeApp/employeeApp.html");
});

