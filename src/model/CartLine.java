package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class CartLine {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Item item;
	
	@ManyToOne
	private Cart cart;
	
	@Column
	private Integer quantity;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer cuantity) {
		this.quantity = cuantity;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"id\":\"").append(id).append("\",");
		sb.append("\"item\":").append(getItem().toString()).append(",");
		sb.append("\"quantity\":").append(quantity).append("");
		return sb.append("}").toString();
	}
	
}
