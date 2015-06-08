package controller.commands.imp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Cart;
import model.CartLine;
import model.Item;
import controller.ControllerActions.RemoveDeductItemFromCart;
import controller.ControllerParams;
import controller.ControllerResponse;
import controller.commands.Command;

public class CommandRemoveDeductItemFromCart implements Command {

	@Override
	public ControllerResponse execute(ControllerParams params) {
		ControllerResponse response = new ControllerResponse();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiViCa");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Integer id = params.getInteger(RemoveDeductItemFromCart.Params.Id);
		String ip = params.getString(RemoveDeductItemFromCart.Params.Ip);
		Integer cuantity = params.getInteger(RemoveDeductItemFromCart.Params.Quantity);
		
		Item item = em.find(Item.class, id);
		Cart cart = em.find(Cart.class, ip);
		
		if(item==null){
			response.putError(RemoveDeductItemFromCart.Errors.ItemNotFound, "Item not found with id: " + id);
		}else if(cart==null){
			response.putError(RemoveDeductItemFromCart.Errors.CartNotFound, "There's no cart! (?)");
		}else{
			CartLine line = null;
			for(CartLine cl : cart.getLines())
				if(cl.getItem().getId() == item.getId()){
					line = cl;
					break;
				}
			
			if(line == null){
				response.putWarning(RemoveDeductItemFromCart.Warnings.ItemNotInCart, "There's no item in cart with id: "+id);
			}else if(cuantity <= 0){
				em.remove(line);
				em.persist(cart);
			}else{
				line.setQuantity(cuantity);
				em.persist(line);
				em.persist(cart);
			}
		}
		
		em.getTransaction().commit();
		
		return response;
	}

}
