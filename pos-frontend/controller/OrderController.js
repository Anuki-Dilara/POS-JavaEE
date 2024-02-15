import {Customer} from "../model/Customer.js";
import { Item } from "../model/Item.js";
import { totalOrderCount } from "./DashboardController.js";

var customerRowIndex = null;
var itemRowIndex = null;
let customer_arr = [];
let item_arr = [];
let cartItems = [];
let order_arr = [];
let currentOrderNumber = 1;
let nextOrderId;


var currentDate = new Date();
var year = currentDate.getFullYear();
var month = currentDate.getMonth();
var day = currentDate.getDate();
$("#order-date-lbl").text(`Order Date : ${year} : ${month} : ${day}`);
var date =` ${year}-${month}-${day}`;

//load Cart
const LoadCart = ()=>{
    $(`#orderedItemTBody`).empty();
    cartItems.map((item)=>{
        let record = `<tr><td>${item.id}</td><td>${item.name}</td><td>${item.price}</td><td>${item.qty}</td><td>${item.price *item.qty }</td></tr>`;
        $(`#orderedItemTBody`).append(record);
    });
}

//load customer ID's to dropdown
$("#orderCusId>button").on('click', ()=> {
    $("#cusDropdown").empty();
    customer_arr.map((customer) => {
        $("#cusDropdown").append(`<a class="dropdown-item" href="#"> ${customer.id} </a>`);
    });
});

//load Item Codes to dropdown
$("#orderItemId>button").on('click', ()=> {
    $("#itemDropdown").empty();
    item_arr.map((item) => {
        $("#itemDropdown").append(`<a class="dropdown-item" href="#"> ${item.id} </a>`);
    });
});

//set customer details to fields
let buttonText;
$("#cusDropdown").on('click', "a", function(){
    buttonText = $(this).text().trim();    
    $("#orderCusId>button").text(buttonText);
    customerRowIndex = customer_arr.findIndex(customer => customer.id === buttonText);
    $("#orderCusName").val(customer_arr[customerRowIndex].name);
    $("#orderCusAddress").val(customer_arr[customerRowIndex].address);
    $("#orderCusSalary").val(customer_arr[customerRowIndex].salary);
});

//set item details to fields
let selectedItem;
$("#itemDropdown").on('click', "a", function(){
    selectedItem = $(this).text().trim();
    $("#orderItemId>button").text(selectedItem);
    itemRowIndex = item_arr.findIndex(item => item.id == selectedItem);

    $("#orderItemName").val(item_arr[itemRowIndex].name);
    $("#orderItemPrice").val(item_arr[itemRowIndex].price);
    $("#qty-on-hand").val(item_arr[itemRowIndex].qty);
});

//add to cart
var total = 0;
$("#add-item-btn").on('click', ()=>{
    var itemId = selectedItem;
    var itemName = $("#orderItemName").val();
    var itemPrice = parseFloat($("#orderItemPrice").val());
    var itemQtyOnHand = parseFloat($("#qty-on-hand").val());
    var itemQty = parseFloat($("#orderItemQty").val());
 
    if(itemQtyOnHand < itemQty){
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Not enough items!'
          })
    }else{
       $("#qty-on-hand").val(itemQtyOnHand - itemQty);
        var itemTotal = itemPrice * itemQty;
        total += itemTotal;
        $("#subTotal").text("Sub Total: " + total);
    
        cartItems.push(new Item(itemId, itemName, itemPrice , itemQty));
        LoadCart();
    }

    
});

var discountPercentage;
var totalVal;
var discount;
var cashValue;
$("#discount").on('input', function(){
    discountPercentage = parseFloat($("#discount").val());
    discount = (total * (discountPercentage/100));
    totalVal = total-discount;
    $(".total").text("Total : " +totalVal);
});

$("#cash").on('input', function(){
    cashValue = parseFloat($(this).val());
    var balance = cashValue - totalVal; 

    $("#balance").val(balance.toFixed(2));
});

$("#order-btn").on('click', () => {
        var newOrderID = nextOrderId;
        var cusId = buttonText;
        var name = $("#orderCusName").val();
        var address = $("#orderCusAddress").val();
        var salary = $("#orderCusSalary").val();

        var customerObj = new Customer(cusId, name, address, salary);

        const Order ={
            id:newOrderID,
            date: new Date().toISOString().split('T')[0],
            customer: customerObj,
            items: cartItems,
            discount: discount,
            total: totalVal
        }
        console.log(Order)
        const orderJson = JSON.stringify(Order);

        $.ajax({
            url:"http://localhost:8080/pos_backend_war_exploded/order",
                type:"POST",
                data:orderJson,
                headers:{"Content-Type":"application/json"},
                success: (res) =>{
                    if(res == "Error"){
                        alert("Duplicate Order ID")
                    }else{
                        console.log(JSON.stringify(res))
                    Swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Your work has been saved',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    getAllItems();
                    getAllCustomers();
                    getAllOrders(function () {
                        getAllCustomers();
                        getAllItems();
                        generateOrderID();
                    });
                    totalOrderCount(order_arr.length);

                    }
                                    },
                error: (err)=>{
                    console.error(err)
                }
        });
        ClearFields();
});

function ClearFields(){
    $("#orderCusName").val(null);
    $("#orderCusAddress").val(null);
    $("#orderCusSalary").val(null);
    $("#orderItemName").val(null);
    $("#orderItemPrice").val(null);
    $("#qty-on-hand").val(null);
    $("#subTotal").text("Sub Total: ");
    $(".total").text("Total : ");
    $("#qty-on-hand").val(null);
    $("#balance").val(null);
    $("#discount").val(null)
    $("#cash").val(null);
    $(`#orderedItemTBody`).empty();
    $("#orderCusId>button").text("select");
    $("#orderItemId>button").text("select");
    cartItems=[];
    total = 0;
    totalVal = 0;
}

function getAllCustomers() {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/pos_backend_war_exploded/customer",
        async: true,
        success: function (data) {

            for (let i = 0; i < data.length; i++) {
                customer_arr[i] =  data[i] ;
            }
        },
        error: function (xhr, exception) {
            alert("Error")
        }
    })
}

function getAllItems() {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/pos_backend_war_exploded/item",
        async: true,
        success: function (data) {

            for (let i = 0; i < data.length; i++) {
                item_arr[i] =  data[i] ;
            }
        },
        error: function (xhr, exception) {
            alert("Error")
        }
    })
}


function getAllOrders(callback) {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/pos_backend_war_exploded/order",
        async: true,
        success: function (data) {
            for (let i = 0; i < data.length; i++) {
                order_arr[i] = data[i];
            }
            callback();
        },
        error: function (xhr, exception) {
            alert("Error");
        }
    });
}

function generateOrderID() {
    console.log('Generate Order Id');

    const lastOrder = order_arr.length > 0 ? order_arr[order_arr.length - 1] : 'Array is empty';
    console.log(lastOrder);
    console.log(order_arr);
    const lastOrderId = lastOrder ? lastOrder.id : 'O001';

    console.log(lastOrderId);

    const lastOrderNumber = parseInt(lastOrderId.substr(1), 10);

    const nextOrderNumber = lastOrderNumber + 1;
    nextOrderId = `O${nextOrderNumber.toString().padStart(3, '0')}`;
    
    console.log(nextOrderId);
    $('#order-id-lbl').text(`Order ID : ${nextOrderId}`);
   
}

getAllOrders(function () {
    getAllCustomers();
    getAllItems();
    generateOrderID();
});
