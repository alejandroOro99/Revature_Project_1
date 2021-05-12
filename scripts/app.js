
let loginBtn = document.getElementById("loginBtn");

loginBtn.addEventListener("click",()=>{

    //user input for username/password
    let username = document.forms["loginForm"]["usernameInput"].value;
    let password = document.forms["loginForm"]["passwordInput"].value;

    let url = "http://localhost:9000/customer/login";//post url for login feature

    fetch(url, {
    method: 'POST',
    body: JSON.stringify({
    username: this.username,
    password: this.password,
  }),
  headers: {
    'Content-type': 'application/json; charset=UTF-8',
  },
})
  .then((response) => response.json())
  .then((json) => console.log(json));

});