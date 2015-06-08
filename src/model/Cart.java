package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Cart {

	@Id
	@Column
	private String ip;
	
	@OneToMany(mappedBy = "cart", orphanRemoval=true)
	private List<CartLine> lines;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public List<CartLine> getLines() {
		return lines;
	}

	public void setLines(List<CartLine> lines) {
		this.lines = lines;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		if(this.getLines() == null)
			this.setLines(new ArrayList<CartLine>());
		
		sb.append("\"lines\":").append(this.getLines().toString());
		return sb.append("}").toString();
	}
}
