package controller.commands.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Item;
import controller.ControllerActions.ItemList;
import controller.ControllerParams;
import controller.ControllerResponse;
import controller.commands.Command;

public class CommandItemList implements Command {

	@Override
	public ControllerResponse execute(ControllerParams params) {
		
		ControllerResponse response = new ControllerResponse();
		
		Integer page = params.getInteger(ItemList.Params.Page,1);
		Integer limit = params.getInteger(ItemList.Params.Limit,8);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiViCa");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Query q = em.createNamedQuery("model.Item.itemList", Item.class)
				.setFirstResult((page-1)*limit)
				.setMaxResults(limit);
		
		@SuppressWarnings("unchecked")
		List<Item> items = q.getResultList();
		
		em.getTransaction().commit();
		
		response.putResult(ItemList.Results.Items, items);
		return response;
	}

}
