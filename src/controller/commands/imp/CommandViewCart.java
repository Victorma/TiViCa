package controller.commands.imp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Cart;
import controller.ControllerParams;
import controller.ControllerResponse;
import controller.ControllerActions.ViewCart;
import controller.commands.Command;

public class CommandViewCart implements Command {

	@Override
	public ControllerResponse execute(ControllerParams params) {
		ControllerResponse response = new ControllerResponse();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiViCa");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();

		String ip = params.getString(ViewCart.Params.Ip);
		
		Cart cart = em.find(Cart.class, ip);
		
		if(cart==null){
			cart = new Cart();
			cart.setIp(ip);
			em.persist(cart);
		}
		
		response.putResult(ViewCart.Results.Cart, cart);
		
		em.getTransaction().commit();
		
		return response;
	}

}
