package controller.commands.imp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Cart;
import model.CartLine;
import model.Item;
import controller.ControllerParams;
import controller.ControllerResponse;
import controller.ControllerActions.SubmitCart;
import controller.commands.Command;

public class CommandSubmitCart implements Command {

	@Override
	public ControllerResponse execute(ControllerParams params) {
		ControllerResponse response = new ControllerResponse();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiViCa");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();

		String ip = params.getString(SubmitCart.Params.Ip);
		String name = params.getString(SubmitCart.Params.Name);
		String address = params.getString(SubmitCart.Params.Address);
		String comments = params.getString(SubmitCart.Params.Comments);
		
		Cart cart = em.find(Cart.class, ip);
		
		boolean error = false;
		
		if(cart==null || cart.getLines().size() == 0){
			response.putError(SubmitCart.Errors.CartIncorrect, "No hay carrito o no hay suficientes productos.");
			error = true;
		}else{
			
			List<Item> avisos = new ArrayList<Item>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("Nombre: ").append(name).append("\\n");
			sb.append("Direccion: ").append(address).append("\\n");
			sb.append("Comentarios: ").append(comments).append("\\n");
			sb.append("Pedido:").append("\\n");
			
			Double total = 0.0;
			for(CartLine cl : cart.getLines()){
				Item i = cl.getItem();
				if(i.getStock() < cl.getQuantity()){
					response.putError(SubmitCart.Errors.InsufficientStock, 
							"Not enough stock to submit (" + i.getStock() + " vs " + cl.getQuantity()+").");
					error = true;
				}else{
					Double subtotal = cl.getQuantity() * i.getPrice();
					sb.append(" - ("+i.getId()+") "+i.getName()+" : " + cl.getQuantity() + " uds. x " + i.getPrice() + " Eur = " + subtotal+ " Eur \\n");
					total+=subtotal;
					i.setStock(i.getStock() - cl.getQuantity());
					em.persist(i);
					if(i.getStock() < 5)
						avisos.add(i);
				}
			}
			sb.append("TOTAL: "+total+" Eur");
			
			if(!error){
				response.putResult(SubmitCart.Results.Email, sb.toString());
				if(avisos.size() > 0){
					StringBuilder aviso = new StringBuilder("Item stock advices:\\n");
					for(Item i : avisos)
						aviso.append(" - Item (").append(i.getId()).append(" \\\"").append(i.getName()).append("\\\"")
							.append(" has stock too low (").append(i.getStock()).append("). Consider add more units soon \\n");
						
					response.putWarning(SubmitCart.Results.Advices, aviso.toString());
				}
					
				
			}
		}
		
		//response.putResult("cart", cart);
		
		if(error)
			em.getTransaction().rollback();
		else{
			em.remove(cart);
			em.getTransaction().commit();
		}
		
		return response;
	}

}
