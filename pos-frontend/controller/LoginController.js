
let user_arr = [];

document.addEventListener('DOMContentLoaded', function () {
    var tabs = document.querySelectorAll('.tabs h3 a');
  
    tabs.forEach(function (tab) {
      tab.addEventListener('click', function (event) {
        event.preventDefault();
        
        tabs.forEach(function (t) {
          t.classList.remove('active');
        });
  
        this.classList.add('active');
  
        var tabContentId = this.getAttribute('href');
        var tabContents = document.querySelectorAll('div[id$="tab-content"]');
        
        tabContents.forEach(function (content) {
          content.classList.remove('active');
        });
  
        document.querySelector(tabContentId).classList.add('active');
      });
    });
  });
  

  $('#Signup').on('click', ()=>{
    let email = $('#user_email').val();
    let userName = $('#user_name').val();
    let password = $('#user_pass').val();

    const User ={
      email:email,
      username: userName,
      password: password
    }

    const userJSON = JSON.stringify(User);

    $.ajax({
        url:"http://localhost:8080/pos_backend_war_exploded/user",
            type:"POST",
            data:userJSON,
            headers:{"Content-Type":"application/json"},
            success: (res) =>{
                console.log(JSON.stringify(res))
                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Your work has been saved',
                    showConfirmButton: false,
                    timer: 1500
                })

              $('#user_email').val('');
              $('#user_name').val('');
              $('#user_pass').val('');
            },
            error: (err)=>{
                console.error(err)
            }
    });
  });

  $('#Loginbtn').on('click', ()=>{
    $.ajax({
      method:"GET",
      url:"http://localhost:8080/pos_backend_war_exploded/user",
      async:true,
      success: function (data) {

      for (let i = 0; i < data.length; i++) {
          user_arr[i] =  data[i] ;
      }
      console.log(user_arr);
      },
      error: function (xhr, exception) {
        alert("Error")
      }
  });

  let username = $('#user_login').val().toLowerCase();
  let password = $('#user_login_pass').val();
  
  let index = user_arr.findIndex(user => user.email.toLowerCase() === username);
  
  if (index !== -1 && user_arr[index].password === password) {
      console.log("True");
      window.location.href = 'pos.html';
  } else {
      console.log("Invalid username or password");
  }
  });
