import { totalItemCount } from "./DashboardController.js";
let row_index;
let item_arr = [];

const itemCodeRegex = /I\d{3}/;
const itemNameRegex = /^[A-Za-z\s]+$/;
const itemPriceRegex = /^[0-9]\d*$/;
const itemQtyRegex = /^[1-9]\d*$/;

//load table
getAllItems();

//table on click
$(`#itemTable`).on('click', 'tr', function(){
    let data_col = $(this).find('td');
    $('#itemId').val(data_col.eq(0).text());
    $('#itemName').val(data_col.eq(1).text());
    $('#itemPrice').val(data_col.eq(2).text());
    $('#itemQty').val(data_col.eq(3).text());

    row_index = $(this).index();
});

//add Item
$(`#item-save`).on('click', ()=>{
   let itemId = $('#itemId').val();
   let itemName =$('#itemName').val();
   let itemPrice =  $('#itemPrice').val();
   let itemQty =$('#itemQty').val();
    

    let val = validateValues(itemId, itemName, itemPrice, itemQty);
    if(val){

        const itemData ={
            id:itemId,
            name: itemName,
            price: itemPrice,
            qty: itemQty
        }
        console.log(itemData)
        const itemJson = JSON.stringify(itemData);
        console.log(itemJson)

        $.ajax({
            url:"http://localhost:8080/pos_backend_war_exploded/item",
                type:"POST",
                data:itemJson,
                headers:{"Content-Type":"application/json"},
                success: (res) =>{
                    if(res == "Error"){
                        alert("Duplicate Entry")
                    }else{
                        console.log(JSON.stringify(res))
                    Swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Your work has been saved',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    }
                    
                },
                error: (err)=>{
                    console.error(err)
                }
        });
    
      $(`#item-reset`).click();
 
     getAllItems();
     totalItemCount(item_arr.length);
    }else{
     return;
    }
   }

    
);

//update Item
$(`#item-update`).on('click', ()=>{
   let itemId = $('#itemId').val();
   let itemName =$('#itemName').val();
   let itemPrice =  $('#itemPrice').val();
   let itemQty =$('#itemQty').val();

        let val = validateValues(itemId, itemName, itemPrice, itemQty);
        if(val){
            const itemData ={
                id:itemId,
                name: itemName,
                price: itemPrice,
                qty: itemQty
            }
            console.log(itemData)
            const itemJson = JSON.stringify(itemData);
            console.log(itemJson)
    
            $.ajax({
                url:"http://localhost:8080/pos_backend_war_exploded/item",
                    type:"PUT",
                    data:itemJson,
                    headers:{"Content-Type":"application/json"},
                    success: (res) =>{
                        if(res == "Error"){
                            alert("No matching Item found")
                        }else{
                            console.log(JSON.stringify(res))
                        Swal.fire({
                            position: 'top-end',
                            icon: 'success',
                            title: 'Your work has been updated',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        getAllItems();
                        }
                        
                    },
                    error: (err)=>{
                        console.error(err)
                    }
            });
            

         $(`#item-reset`).click();
        
         getAllItems();
        }else{
         return;
        }
    

    
});

//delete customer
$(`#item-delete`).on('click', ()=>{

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
            let itemID = $('#itemId').val();
            const itemData ={
                id:itemID
            }
            console.log(itemData)
            const itemJson = JSON.stringify(itemData);
            console.log(itemJson)
    
            $.ajax({
                url:"http://localhost:8080/pos_backend_war_exploded/item",
                    type:"DELETE",
                    data:itemJson,
                    headers:{"Content-Type":"application/json"},
                    success: (res) =>{

                        if(res == "Error"){
                            alert("No matching Item found")
                        }else{
                            console.log(JSON.stringify(res))
                        Swal.fire({
                            position: 'top-end',
                            icon: 'success',
                            title: 'Your work has been deleted',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        }
                        
                    },
                    error: (err)=>{
                        console.error(err)
                    }
            });
            
    $(`#item-reset`).click();
         
          getAllItems();
        }
      })


    
});

//search Item
$('#btn-search-item').on('click', ()=>{
    let requestId = $('#search-item-input').val();
    let index = item_arr.findIndex(item => item.id == requestId);

    if(index !==-1){

        $('#itemId').val(item_arr[index].id);
        $('#itemName').val(item_arr[index].name);
        $('#itemPrice').val(item_arr[index].price);
        $('#itemQty').val(item_arr[index].qty);

        row_index = item_arr.findIndex((item => item.id == requestId));
        $('#search-item-input').val('');
    }else{
        Swal.fire('No matching Customer Found!');
        $('#search-item-input').val('');
    }

});

//get all Items
function getAllItems(){
    $.ajax({
        method:"GET",
        url:"http://localhost:8080/pos_backend_war_exploded/item",
        async:true,
        success: function (data) {
            
            $('#itemTable').empty();
            for(let item of data){
                let itemID = item.id;
                let itemName = item.name;
                let itemPrice = item.price;
                let itemQty = item.qty;

                var row = `<tr><td>${itemID}</td><td>${itemName}</td><td>${itemPrice}</td><td>${itemQty}</td></tr>`;
                $('#itemTable').append(row);
        }
            
        for (let i = 0; i < data.length; i++) {
            item_arr[i] =  data[i] ;
        }

        },
        error: function (xhr, exception) {
          alert("Error")
        }
    })
}

//validation
function validateValues(itemCode, itemName, itemPrice, itemQty){
    const regexarr = [itemCodeRegex, itemNameRegex, itemPriceRegex, itemQtyRegex];
    const fieldsarr = [itemCode, itemName, itemPrice, itemQty];

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