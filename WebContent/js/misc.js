var currentpage = 1;

function peviousPage(style){
	currentpage--;
	if(currentpage<=1)
		$("#prevbtt").prop('disabled', true);

	getProductList(currentpage,8,style);
}

function nextPage(style){
	$("#prevbtt").prop('disabled', false);
	currentpage++;
	getProductList(currentpage,8,style);
}

function generateProductList(json){
	this.products = new Array();

	for(this.i = 0; this.i<json.length; this.i++){
		this.p = new Producto();
		this.p.fill(json[this.i]);
		this.products.push(this.p);
	}

	return this.products;
}


function createProductTable(products, style){
	//Style: 0 = principal style, 1 = carrito style, 2 = admin style;
	this.table = "<tr>";

	for(this.i = 0; this.i<products.length&&this.i<8; this.i++){
		if(this.i==4) this.table+="<tr></tr>";
		this.table+="<td>"+products[this.i].toHtml(style)+"</td>";
	}
	this.table+="</tr>";

	return this.table
}

function createProductCart(products){
	this.cart ="";
	this.suma = 0;
	for(this.i = 0; this.i<products.length&&this.i<8; this.i++){
		this.cart+=products[this.i].toHtml(1);
		this.suma+=products[this.i]._quantity*products[this.i]._price;
	}

	this.price_container = $("#total_price");
	this.cartbutton = $("#cartbutton");

	if(this.suma==0) {
		this.price_container.prop("disabled",true);
		if(this.cartbutton!=null) this.cartbutton.prop("disabled",true);
	}else {
		this.price_container.prop("disabled",false);
		if(this.cartbutton!=null) this.cartbutton.prop("disabled",false);
	}

	this.price_container.val(this.suma+"€");
	this.price_container.text(this.suma+"€");
	return this.cart;
}

function removeProduct(id){
	changequantity(0,1,id);
}

function changequantity(input,iscart,id){
	if(iscart==1)
		this.producto = getProductFromCart(id);
	else
		this.producto = getProductFromProductList(id);
	
	this.resta = input - this.producto._quantity;
	this.producto._quantity = input;

	if(iscart==1)
		if(this.resta>=0) addIncrementProductCart(id,true)
		else removeDeductItemFromCart(id)
}

function updatequantity(cart_id){
	this.producto = null;
	for(this.i=0; this.i<auxCart.length; this.i++){
		if(auxCart[this.i]._cart_id==cart_id){
			this.producto = auxCart[this.i];
		}
	}
}

function getProductFromProductList(id){
	this.producto = null;
	for(this.i=0; this.i<auxProductList.length; this.i++){
		if(auxProductList[this.i]._id==id){
			this.producto = auxProductList[this.i];
		}
	}
	return this.producto;
}

function getProductFromCart(cart_id){
	this.producto = null;
	for(this.i=0; this.i<auxCart.length; this.i++){
		if(auxCart[this.i]._cart_id==cart_id){
			this.producto = auxCart[this.i];
		}
	}
	return this.producto;
}

function fillForm(id){
	this.producto = getProductFromProductList(id);
	if(this.producto!=null){
		$('#dropimage').css('background-image','url('+this.producto._image+')');
		$('#form_nuevo_producto input[name="product_id"]').val(this.producto._id);
		$('#form_nuevo_producto input[name="product_name"]').val(this.producto._name);
		$('#form_nuevo_producto input[name="product_stock"]').val(this.producto._stock);
		$('#form_nuevo_producto input[name="product_price"]').val(this.producto._price);
	}
}