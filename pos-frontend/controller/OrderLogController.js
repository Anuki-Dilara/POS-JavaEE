import {item_db, order_db} from "../db/db.js";
import {customer_db} from "../db/db.js";
import {Order} from "../model/Order.js";


var order_index = null;
let order_arr = [];
let customer_arr = [];

getAllOrders();
getAllEmloyees();

const LoadTable = ()=>{
    $(`#orderLogTbody`).empty();
    order_arr.map((item)=>{
        let record = `<tr><td>${item.id}</td><td>${item.date}</td><td>${item.customer.id}</td><td>${item.discount}</td><td>${item.total}</td></tr>`;
        $(`#orderLogTbody`).append(record);
    });
}

$("#showlog").on('click',()=>{
    LoadTable();
});
var itemstoload;
$("#orderLogTable").on('click', 'tr', function(){
    let data_col = $(this).find('td');
    var customer_id = data_col.eq(2).text();
    var order_id = data_col.eq(0).text();
    order_index = order_arr.findIndex(order => order.id == order_id);
    var customer_index = customer_arr.findIndex(customer => customer.id == customer_id);

    var selectedorder = order_arr[order_index];
    itemstoload = selectedorder.items; 
    
    $("#OlcusName").val(customer_arr[customer_index].name);
    $("#OlcusAddress").val(customer_arr[customer_index].address);
    $("#OlcusSalary").val(customer_arr[customer_index].salary);

    loadItemDetailsTable();
   
});

const loadItemDetailsTable = ()=>{
    $(`#orderLogitemTbody`).empty();
    itemstoload.map((item)=>{
        let record = `<tr><td>${item.id}</td><td>${item.name}</td><td>${item.price}</td><td>${item.qty}</td><td>${item.qty * item.price}</td></tr>`;
        $(`#orderLogitemTbody`).append(record);
    });
}
var requestedOrder;
$('#btn-search-orderlog').on('click', ()=>{
    let requestId = $('#search-orderlog-input').val();
    let index = order_arr.findIndex(order => order.id == requestId);
    requestedOrder = order_arr[index];
    loadRequestItemDetailsTable();
});

const loadRequestItemDetailsTable = ()=>{
    $(`#orderLogTbody`).empty();
    let record = `<tr><td>${requestedOrder.id}</td><td>${requestedOrder.date}</td><td>${requestedOrder.customer.id}</td><td>${requestedOrder.discount}</td><td>${requestedOrder.total}</td></tr>`;
    $(`#orderLogTbody`).append(record);
    $('#search-orderlog-input').val('');
}

function getAllOrders() {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/pos_backend_war_exploded/order",
        async: true,
        success: function (data) {

            for (let i = 0; i < data.length; i++) {
                order_arr[i] =  data[i] ;
            }
        },
        error: function (xhr, exception) {
            alert("Error")
        }
    })
}

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