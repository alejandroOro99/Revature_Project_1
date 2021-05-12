 window.onload = (event) => {
    let data = loadLogin();
    document.getElementById("customerPortal").innerHTML = "Welcome to your account: "+data.Username;
 };


function loadLogin(){
    let loginData = localStorage.getItem('_loginData');

    if(!loginData)
    return null;;

    //localStorage.removeItem('_loginData');

    loginData = JSON.parse(loginData);
    return loginData;
 }

let viewAccBtn = document.getElementById("viewAccBtn");

//view acc balance
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

let depositBtn = document.getElementById("depositBtn");

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