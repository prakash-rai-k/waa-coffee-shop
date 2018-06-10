$(document).ready(function(){
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/api/products"
      }).done(function( data  ) {
            var products = JSON.parse(JSON.stringify(data));
            products.forEach(element => {
                var el = `<div class="card" class="prodElement">
                <img  class="card-img-top" src="food.jpg" style="width:180px;"/>
                <div class="card-body"><h5 >${element.productName}</h5>
                    <p class="card-text" >${element.description}</p>
                    <a class="btn btn-primary">Order</a>
                </div>`
                $('#products').append(el);
            });
        });
});
