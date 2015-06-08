var auxProductList;
var auxCart;
var host = "/";

function getProductList(page, limit, style){
	this.parameters = {
		page: page,
		limit: limit
	}

	$.post(host+"TiViCa/Controller?action=itemlist", this.parameters, function( data ) {
		this.html = "";
		if(data!==null){
			this.json = jQuery.parseJSON( data );
			alertWarnings(this.json);
    		if(this.json["errors"]){
    			this.errors = this.json["errors"];
    			output="";
    			for (property in this.errors) output += property + ': ' + this.errors[property]+'; ';
				alert(output);
    		}else if(this.json["items"]){
				auxProductList = generateProductList(this.json["items"]);
				this.html = createProductTable(auxProductList,style);
		    }else{
	    		this.html+="<tr><td>No hay libros</td><tr>";
    		}
    	}else
    	this.html += "<tr><td>No se pudo conectar con el servidor</td><tr>";


    	$("#products_table").html(this.html);
	}); 
}

function addModifyProduct(){
	this.bg = $('#dropimage').css('background-image');
	this.bg = this.bg.replace('url(','').replace(')','');

	this.parameters = {
		id: $('#form_nuevo_producto input[name="product_id"]').val(),
		name: $('#form_nuevo_producto input[name="product_name"]').val(),
		stock: $('#form_nuevo_producto input[name="product_stock"]').val(),
		price: $('#form_nuevo_producto input[name="product_price"]').val(),
		image: this.bg
	}

	$.post(host+"TiViCa/Controller?action=addmodifyitem", this.parameters, function( data ) {
		this.html = "";
		if(data!==null){
			this.json = jQuery.parseJSON( data );
			alertWarnings(this.json);
    		if(this.json["errors"]){
    			this.errors = this.json["errors"];
    			output="";
    			for (property in this.errors) output += property + ': ' + this.errors[property]+'; ';
				alert(output);
    		}else if(this.json["id"]){
    			getProductList(1,8,2);
    			$('#form_nuevo_producto input[name="product_id"]').val(-1);
				$('#form_nuevo_producto input[name="product_name"]').val("");
				$('#form_nuevo_producto input[name="product_stock"]').val(1);
				$('#form_nuevo_producto input[name="product_price"]').val("");
				$('#dropimage').css('background-image','url(img/noimage.png)');
				alert("ID nuevo producto: "+this.json["id"]);
		    }else{
	    		this.html+="<tr><td>No hay libros</td><tr>";
    		}
    	}else
    	this.html += "<tr><td>No se pudo conectar con el servidor</td><tr>";


    	$("#products_table").html(this.html);
	}); 
}

function deleteProduct(id){

	this.parameters = {
		id: id
	}

	$.post(host+"TiViCa/Controller?action=removeitem", this.parameters, function( data ) {
		this.html = "";
		if(data!==null){
			this.json = jQuery.parseJSON( data );
			alertWarnings(this.json);
    		if(this.json["errors"]){
    			this.errors = this.json["errors"];
    			output="";
    			for (property in this.errors) output += property + ': ' + this.errors[property]+'; ';
				alert(output);
    		}else{
	    		getProductList(1,8,2);
    		}
    	}else
    	this.html += "<tr><td>No se pudo conectar con el servidor</td><tr>";
	}); 
}

function addIncrementProductCart(id, iscart){
	this.quantity = 0;
	if(iscart==true){ this.p =  getProductFromCart(id); this.quantity =p._quantity; id = p._id;}
	else this.quantity = getProductFromProductList(id)._quantity;

	this.parameters = {
		id: id,
		quantity: this.quantity
	}

	$.post(host+"TiViCa/Controller?action=putadditemincart", this.parameters, function( data ) {
		this.html = "";
		if(data!==null){
			this.json = jQuery.parseJSON( data );
			alertWarnings(this.json);
    		if(this.json["errors"]){
    			this.errors = this.json["errors"];
    			output="";
    			for (property in this.errors) output += property + ': ' + this.errors[property]+'; ';
				alert(output);
    		}else{
	    		viewCart();
    		}
    	}else
    	this.html += "<tr><td>No se pudo conectar con el servidor</td><tr>";
	}); 
}

function removeDeductItemFromCart(id){
	this.p = getProductFromCart(id);
	this.parameters = {
		id: p._id,
		quantity: p._quantity
	}

	$.post(host+"TiViCa/Controller?action=removedeductitemfromcart", this.parameters, function( data ) {
		this.html = "";
		if(data!==null){
			this.json = jQuery.parseJSON( data );
			alertWarnings(this.json);
    		if(this.json["errors"]){
    			this.errors = this.json["errors"];
    			output="";
    			for (property in this.errors) output += property + ': ' + this.errors[property]+'; ';
				alert(output);
    		}else{
	    		viewCart();
    		}
    	}else
    	this.html += "<tr><td>No se pudo conectar con el servidor</td><tr>";
	}); 
}

function viewCart(){
	this.parameters = {}

	$.post(host+"TiViCa/Controller?action=viewcart", this.parameters, function( data ) {
		this.html = "";
		if(data!==null){
			this.json = jQuery.parseJSON( data );
			alertWarnings(this.json);
    		if(this.json["errors"]){
    			this.errors = this.json["errors"];
    			output="";
    			for (property in this.errors) output += property + ': ' + this.errors[property]+'; ';
				alert(output);
    		}else if(this.json["cart"]){
    			this.cart = this.json["cart"];
				auxCart = generateProductList(this.cart["lines"]);
				this.html = createProductCart(auxCart);
		    }else{
	    		this.html+="<tr><td>No hay productos en el carrito</td><tr>";
    		}
    	}else
    	this.html += "<tr><td>No se pudo conectar con el servidor</td><tr>";


    	$("#carrito").html(this.html);
	}); 
}


function submitCart(){
	this.parameters = {
		name: $('#form_client_data input[name="client_name"]').val(),
		address: $('#form_client_data textarea[name="client_address"]').val(),
		comments: $('#form_client_data textarea[name="client_comments"]').val()
	}

	$.post(host+"TiViCa/Controller?action=submitcart", this.parameters, function( data ) {
		this.html = "";
		if(data!==null){
			this.json = jQuery.parseJSON( data );
			alertWarnings(this.json);
    		if(this.json["errors"]){
    			this.errors = this.json["errors"];
    			output="";
    			for (property in this.errors) output += property + ': ' + this.errors[property]+'; ';
				alert(output);
    		}else if(this.json["email"]){
    			alert(this.json["email"]);
    			$("#carrito").html("<p>Pedido realizado con Exito</p>");
    			$("#total_price").text("0€");
    			$("#cartbutton").prop("disabled",true);
    		}else
    			alert("Ha ocurrido algun error");
    	}
	}); 
}

function logIn(){
	this.parameters = {
		password: $('#login_form input[name="lf_pass"]').val()
	}

	$.post(host+"TiViCa/Controller?action=adminlogin", this.parameters, function( data ) {
		this.html = "";
		if(data!==null){
			this.json = jQuery.parseJSON( data );
			alertWarnings(this.json);
    		if(this.json["errors"]){
    			this.errors = this.json["errors"];
    			output="";
    			for (property in this.errors) output += property + ': ' + this.errors[property]+'; ';
				alert(output);
    		}else if(this.json["success"]){
    			if(this.json["success"]==true)
    				window.location.href = 'admin.html';
    			else
    				alert("Constraseña erronea");
    		}
    	}
	}); 
}

function alertWarnings(json){
	 if(json["warnings"]){
		 this.warnings = json["warnings"];
		 output="";
		 for (property in this.warnings) 
			 output += property + ': ' + this.warnings[property]+'; ';
		 alert(output);
	 }
}

