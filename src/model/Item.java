package model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name = "model.Item.itemList", query = "select obj from Item obj order by obj.id desc ")})
public class Item {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@OneToMany (mappedBy="item", orphanRemoval=true)
	private Set<CartLine> cartLines;
	
	@Column
	private String name;
	@Column
	private Integer stock;
	@Column
	private String image;
	@Column
	private Double price;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"id\":\"").append(id).append("\",");
		sb.append("\"name\":\"").append(name).append("\",");
		sb.append("\"stock\":").append(stock).append(",");
		sb.append("\"price\":").append(price).append(",");
		sb.append("\"image\":\"").append(image).append("\"");
		return sb.append("}").toString();
	}
	
}
