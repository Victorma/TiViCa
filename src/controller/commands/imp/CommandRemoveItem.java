package controller.commands.imp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

import model.Item;
import controller.ControllerActions.GeneralErrors;
import controller.ControllerActions.RemoveItem;
import controller.ControllerParams;
import controller.ControllerResponse;
import controller.commands.Command;

public class CommandRemoveItem implements Command {

	@Override
	public ControllerResponse execute(ControllerParams params) {
		ControllerResponse response = new ControllerResponse();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiViCa");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		HttpSession session = (HttpSession) params.get("session");
		Boolean admin_logged = (Boolean) session.getAttribute("admin_logged");
		if(admin_logged != null && admin_logged == true){
			Item item = null;
			
			Integer id = params.getInteger("id");
			if(id == null)
				response.putError(RemoveItem.Errors.ItemNotFound, "Item not found with id: " + id);
			else{
				item = em.find(Item.class, id);
				em.remove(item);
			}
		}else{
			response.putError(GeneralErrors.IllegalAccess, "Not autorized.");
		}
		
		em.getTransaction().commit();
		
		return response;
	}

}
