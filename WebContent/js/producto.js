function Producto (type) {
    this._cart_id = -1;
	this._id = -1;
	this._quantity = 1;
    this._name = "";
    this._stock = 0;
    this._price = 0;
    this._image = "";

    //Style: 0 = principal style, 1 = carrito style, 2 = admin style;
    this.toHtml = function(style) {
        this.htmlproduct = "";

        switch (style){
        	case 0: {
        		this.htmlproduct='<div class="producto">'+
					'<p><img src="'+this._image+'"></p>'+
					'<p><span class="nombre">'+this._name+'</span><span class="precio">'+this._price+'€</span></p>';
				
                if(this._stock>0)
                    this.htmlproduct+='<p><input type="number" id="stock'+this._id+'" min="1" max="'+this._stock+'" value="1" onChange="changequantity(this.value,0,'+this._id+')">'+
                                     '<input type="button" value="A&ntilde;adir" onClick="addIncrementProductCart('+this._id+',false)"></p>'+
                                     '</div>';
                else
                    this.htmlproduct+='<p>Fuera de Existencias</p></div>';  
        		break;
        	}
        	case 1: {
        		this.htmlproduct='<div class="producto"><div class="texto">'+
							'<div class="left"><img src="'+this._image+'"><span>'+this._name+'</span></div>'+
							'<div class="right"><span class="precio">'+this._price+'€</span><input type="number" min="0" max="'+this._stock+'" value="'+this._quantity+'" onChange="changequantity(this.value,1,'+this._cart_id+')">'+
							'<span class="precio_multiplicado">'+(this._price*this._quantity)+'€</span><input type="button" value="X" onclick="removeProduct('+this._cart_id+')"></div></div>'+
                            '<div style="clear: both"></div></div>';
        		break;
        	}
        	case 2: {
        		this.htmlproduct='<div class="producto">'+
							'<p><img src="'+this._image+'"></p>'+
							'<p>'+this._name+'</p>'+
							'<p><span class="stock">'+this._stock+'</span><span class="precio">'+this._price+'€</span></p>'+
							'<p><input type="button" value="Modificar" onClick="fillForm('+this._id+')">'+
							'<input class="botonmodificar" type="button" value="X" onClick="deleteProduct('+this._id+')"></p></div>';
        		break;
        	}
        }

        return  this.htmlproduct;
    };

    this.fill = function(json){
        if(!json["item"]){
            this._id = json["id"];
            this._name = json["name"];
            this._stock = json["stock"];
            this._price = json["price"];
            this._image = json["image"];
            if(json["quantity"]) this._quantity = json["quantity"];
        }else{
            this.item = json["item"];

            this._id = this.item["id"];
            this._name = this.item["name"];
            this._stock = this.item["stock"];
            this._price = this.item["price"];
            this._image = this.item["image"];

            this._quantity = json["quantity"];
            this._cart_id = json["id"];
        }
    }
}