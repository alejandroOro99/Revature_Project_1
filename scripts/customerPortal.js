 
 let withdrawBtn = document.getElementById("withdrawBtn");
 let viewAccBtn = document.getElementById("viewAccBtn");
 let depositBtn = document.getElementById("depositBtn");
 let applyBankAccBtn = document.getElementById("applyBankAccBtn");
 let postTransferBtn = document.getElementById("postTransferBtn");
 let viewTransfersBtn = document.getElementById("viewTransfersBtn");
 let viewTransactionsBtn = document.getElementById("viewTransactionsBtn");
 let acceptTransferBtn = document.getElementById("acceptTransferBtn");

 window.onload = (event) => {
    let data = loadLogin();
    document.getElementById("customerPortal").innerHTML = "Welcome to your account: "+data.Username;
 };

//load login from local storage
function loadLogin(){
    let loginData = localStorage.getItem('_loginData');

    if(!loginData)
        return null;

    //localStorage.removeItem('_loginData');

    loginData = JSON.parse(loginData);
    return loginData;
 }


//VIEW ACCOUNT BALANCE feature
viewAccBtn.addEventListener("click",()=>{

    let accName = document.forms["viewAccForm"]["viewAccInput"].value;
    let url = "http://localhost:9000/customer/"+accName;
    console.log(url);
    fetch(url)
        .then(res => res.json())
        .then(currentBalance =>{
            console.log(typeof currentBalance);

            if(currentBalance < 0){
                document.getElementById("balanceDiv").innerHTML = "<p>Please enter an account that belongs to your user</p>";
            }else{
                document.getElementById("balanceDiv").innerHTML = "<p>Current balance of account "+accName+": $"+currentBalance+"</p>";
            }
            

        });  
});

//DEPOSIT feature
depositBtn.addEventListener("click",()=>{


    let data = {
        accName: document.forms["depositForm"]["accDepositInput"].value,
        depositAmount: document.forms["depositForm"]["depositAmountInput"].value
    }

    let url = "http://localhost:9000/customer/deposit";//post url for login feature

    fetch(url, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {"Content-type": "application/json; charset=UTF-8"}
    }).then((response) => response.json()) 
    .then((json) =>{

        console.log(json);
        if(json == "true"){
            document.getElementById("balanceDiv").innerHTML = "<p>Succesfully deposited: $"+data.depositAmount+"</p>";
        }else{
            document.getElementById("balanceDiv").innerHTML = "<p>Incorrect input</p>";
        }

       
    } );
});

//WITHDRAW feature
withdrawBtn.addEventListener("click",()=>{

    let data = {
        accName: document.forms["withdrawForm"]["accWithdrawInput"].value,
        withdrawAmount: document.forms["withdrawForm"]["amountWithdrawInput"].value
    }

    let url = "http://localhost:9000/customer/withdraw";//post url for login feature

    fetch(url, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {"Content-type": "application/json; charset=UTF-8"}
    }).then((response) => response.json()) 
    .then((json) =>{

        console.log(json);
        if(json === "success"){
            document.getElementById("balanceDiv").innerHTML = "<p>Succesfully withdrew: $"+data.withdrawAmount+"</p>";
        }else{
            document.getElementById("balanceDiv").innerHTML = "<p>Incorrect input</p>";
        }

       
    } );

});

//APPLY BANK ACCOUNT feature
applyBankAccBtn.addEventListener("click",()=>{
    
    let data = {
        accName: document.forms["applyBankAccForm"]["applyAccNameInput"].value,
        accBalance: document.forms["applyBankAccForm"]["applyAmountInput"].value
    }

    let url = "http://localhost:9000/customer/applyAcc";//post url for login feature

    fetch(url, {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {"Content-type": "application/json; charset=UTF-8"}
    }).then((response) => response.json()) 
    .then((json) =>{

        console.log(json);
        if(json == "applyAcc success"){
            document.getElementById("balanceDiv").innerHTML = "<p>Account "+data.accName+" successfully created with balance: $"+data.accBalance+"</p>";
        }else{
            document.getElementById("balanceDiv").innerHTML = "<p>"+json+"</p>";
        }
    
    } );

});

//POST TRANSFER FEATURE
postTransferBtn.addEventListener("click",()=>{

    let data = {
        username: document.forms["postTransferForm"]["usernameInput"].value,
        transferAmount: document.forms["postTransferForm"]["transferAmountInput"].value,
        senderAcc: document.forms["postTransferForm"]["senderAccInput"].value,
    }
    if(data.username&&data.senderAcc&&data.transferAmount){
        let url = "http://localhost:9000/customer/postTransfer";//post url for login feature

        fetch(url, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {"Content-type": "application/json; charset=UTF-8"}
        }).then((response) => response.json()) 
        .then((json) =>{
    
            console.log(json);
            if(json == "success"){
                document.getElementById("balanceDiv").innerHTML = "<p>Posted $ "+data.transferAmount+" to "+data.username+" from "+data.senderAcc+"</p>";
            }else{
                document.getElementById("balanceDiv").innerHTML = "<p>"+json+"</p>";
            }
        
        } );
    }else{
        document.getElementById("balanceDiv").innerHTML = "<p>All fields must have values</p>";
    }

    

});

//VIEW TRANSFERS feature
viewTransfersBtn.addEventListener("click",()=>{

    let url = "http://localhost:9000/customer/view/a";//random parameter at the end, bug with non-parameter endpoint in Javalin
    console.log(url);
    
    fetch(url)
        .then(res => res.json())
        .then(response =>{
            
            if(response != null){
                document.getElementById("balanceDiv").innerHTML = "<p>"+response+"</p>";
            }
        }); 


});

//view customer transactions feature
viewTransactionsBtn.addEventListener("click",()=>{
    let url = "http://localhost:9000/customer/viewTransactions/a";//random parameter at the end, bug with non-parameter endpoint in Javalin
    console.log(url);
    
    fetch(url)
        .then(res => res.json())
        .then(response =>{
            
            if(response != null){
                let x = 0;
               let data = "<h3>Transactions</h3><br>";
                for(i in response.dates){
                    console.log(response.dates[i]);
                    data += "<p>"+response.dates[i]+"</p><br>";
                }
                document.getElementById("balanceDiv").innerHTML = data;
                
            }
        }); 
});

acceptTransferBtn.addEventListener("click",()=>{
    let accName = document.forms["acceptTransferForm"]["accNameInput"].value;
    let accDeposit = document.forms["acceptTransferForm"]["accDepositInput"].value;
    let pathToUrl = accName + "/"+accDeposit;

    let url = "http://localhost:9000/customer/handleTransfer/"+pathToUrl;//random parameter at the end, bug with non-parameter endpoint in Javalin
    console.log(url);
    
    fetch(url)
        .then(res => res.json())
        .then(response =>{
            console.log(response);
        }); 
});
