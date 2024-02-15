import { totalCustomerCount } from "./DashboardController.js";
let row_index = null;
let customer_arr = [];

const customerIdRegex = /C\d{3}/;
const customerName = /^[A-Za-z]+ [A-Za-z]+$/;
const customerAddress = /^[A-Za-z\s]+$/i;
const customerSalary = /^[0-9]\d*$/;


//load table
getAllEmloyees();
totalCustomerCount(customer_arr.length)
//table on click
$(`#cusTable`).on('click', 'tr', function(){
    let data_col = $(this).find('td');
    $('#cusId').val(data_col.eq(0).text());
    $('#cusName').val(data_col.eq(1).text());
    $('#cusAddress').val(data_col.eq(2).text());
    $('#cusSalary').val(data_col.eq(3).text());

    row_index = $(this).index();
});
//add customer
$(`#add-customer`).on('click', ()=>{
    let cusId = $('#cusId').val();
    let cusName = $('#cusName').val();
    let cusAddress = $('#cusAddress').val();
    let cusSalary = $('#cusSalary').val();
    

    let val = validateValues(cusId, cusName, cusAddress, cusSalary);
    if(val){
        const cusData ={
            id:cusId,
            name: cusName,
            address: cusAddress,
            salary: cusSalary
        }
        console.log(cusData)
        const customerJson = JSON.stringify(cusData);
        console.log(customerJson)

        $.ajax({
            url:"http://localhost:8080/pos_backend_war_exploded/customer",
                type:"POST",
                data:customerJson,
                headers:{"Content-Type":"application/json"},
                success: (res) =>{
                    console.log(JSON.stringify(res))
                    if(res == "Error"){
                        alert("Duplicate Entry")
                    }else{
                        Swal.fire({
                            position: 'top-end',
                            icon: 'success',
                            title: 'Your work has been saved',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        getAllEmloyees();
                    }
                },
                error: (err)=>{
                    console.error(err)
                }
        });
        
        
        $(`#reset-customer`).click();
    
        totalCustomerCount(customer_arr.length);
        }else{
            return;
        }
    }
);
//update customer
$(`#btn-update-customer`).on('click', ()=>{
    let cusId = $('#cusId').val();
    let cusName = $('#cusName').val();
    let cusAddress = $('#cusAddress').val();
    let cusSalary = $('#cusSalary').val();

    
        let val = validateValues(cusId, cusName, cusAddress, cusSalary);

        if(val){
            
           // LoadCustomerData();
           const cusData ={
            id:cusId,
            name: cusName,
            address: cusAddress,
            salary: cusSalary
        }
        console.log(cusData)
        const customerJson = JSON.stringify(cusData);
        console.log(customerJson)

        $.ajax({
            url:"http://localhost:8080/pos_backend_war_exploded/customer",
                type:"PUT",
                data:customerJson,
                headers:{"Content-Type":"application/json"},
                success: (res) =>{
                    if(res === "Error"){
                        alert("No matching Customer found")
                    }else{
                        console.log(JSON.stringify(res))
                    Swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Your work has been saved',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    getAllEmloyees();
                    }
                    
                },
                error: (err)=>{
                    console.error(err)
                }
        });
        
            
        $(`#reset-customer`).click();
        }else{
            return;
        }
});
//delete customer
$(`#btn-delete-customer`).on('click', ()=>{

    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
      }).then((result) => {
        if (result.isConfirmed) {
            let cusId = $('#cusId').val();
           
            //LoadCustomerData();
            //$(`#reset-customer`).click();
            //totalCustomerCount(customer_db.length);

            const cusData ={
                id:cusId
            }
            console.log(cusData)
            const customerJson = JSON.stringify(cusData);
            console.log(customerJson)
    
            $.ajax({
                url:"http://localhost:8080/pos_backend_war_exploded/customer",
                    type:"DELETE",
                    data:customerJson,
                    headers:{"Content-Type":"application/json"},
                    success: (res) =>{
                        if(res == "Error"){
                            alert("No Matching Customer found")
                        }else{
                            console.log(JSON.stringify(res))
                        Swal.fire({
                            position: 'top-end',
                            icon: 'success',
                            title: 'Customer Deleted',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        getAllEmloyees();
                        }
                        
                    },
                    error: (err)=>{
                        console.error(err)
                    }
            });
          $(`#reset-customer`).click();
          getAllEmloyees();
        }
      })

    
});
//search customer
$('#btn-search-customer').on('click', ()=>{

    let requestId = $('#search-customer-input').val();

        let index = customer_arr.findIndex(customer => customer.id == requestId);

        if(index !==-1){
            $('#cusId').val(customer_arr[index].id);
            $('#cusName').val(customer_arr[index].name);
            $('#cusAddress').val(customer_arr[index].address);
            $('#cusSalary').val(customer_arr[index].salary);

            row_index = customer_arr.findIndex((customer => customer.id == requestId));
            $('#search-customer-input').val('');
        }else{
            Swal.fire('No matching Customer Found!');
            $('#search-customer-input').val('');
        }
    
    

});

//get All
function getAllEmloyees() {
    console.log("Get All Employees");
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/pos_backend_war_exploded/customer",
        async: true,
        success: function (data) {

            $('#cusTable').empty();
            for (let cus of data) {
                let cusID = cus.id;
                let cusName = cus.name;
                let cusaddress = cus.address;
                let cusSalary = cus.salary;

                var row = `<tr><td>${cusID}</td><td>${cusName}</td><td>${cusaddress}</td><td>${cusSalary}</td></tr>`;
                $('#cusTable').append(row);
            }

            
            for (let i = 0; i < data.length; i++) {
                customer_arr[i] =  data[i] ;
            }
        },
        error: function (xhr, exception) {
            alert("Error")
        }
    })
}

//validation
function validateValues(cusId, cusName, cusAddress, cusSalary){
    const regexarr = [customerIdRegex, customerName, customerAddress, customerSalary];
    const fieldsarr = [cusId, cusName, cusAddress, cusSalary];

    for (let i = 0; i < regexarr.length; i++) {
        if (!(regexarr[i].test(fieldsarr[i]))) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Invalid Data!'
              })
            return false;
        }
    }
    return true;
}