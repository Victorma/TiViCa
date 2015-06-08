package controller.commands.imp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Cart;
import model.CartLine;
import model.Item;
import controller.ControllerActions.GeneralErrors;
import controller.ControllerActions.PutAddItemInCart;
import controller.ControllerParams;
import controller.ControllerResponse;
import controller.commands.Command;

public class CommandPutAddItemInCart implements Command {

	@Override
	public ControllerResponse execute(ControllerParams params) {
		ControllerResponse response = new ControllerResponse();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiViCa");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Integer id = params.getInteger(PutAddItemInCart.Params.Id);
		String ip = params.getString(PutAddItemInCart.Params.Ip);
		Integer cuantity = params.getInteger(PutAddItemInCart.Params.Quantity);
		
		Item item = em.find(Item.class, id);
		Cart cart = em.find(Cart.class, ip);
		
		if(cart==null){
			cart = new Cart();
			cart.setIp(ip);
		}
		
		if(item==null){
			response.putError(PutAddItemInCart.Errors.ItemNotFound, "Item not found with id: " + id);
		}else if(item.getStock() < cuantity){
			response.putError(PutAddItemInCart.Errors.InsufficientStock, "Insufficient stock: " 
					+ item.getStock() + " vs " + cuantity);
		}else if(cuantity <= 0){
			response.putError(GeneralErrors.ParamError, "The param 'quantity' stands for 0 or less.");
		}else{
			CartLine line = null;
			for(CartLine cl : cart.getLines())
				if(cl.getItem().getId() == item.getId()){
					line = cl;
					break;
				}
			
			if(line == null){
				line = new CartLine();
				line.setCart(cart);
				line.setItem(item);
			}
			
			line.setQuantity(cuantity);
			
			em.persist(cart);
			em.persist(line);
		}
		
		em.getTransaction().commit();
		
		return response;
	}

}
