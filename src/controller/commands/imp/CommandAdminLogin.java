package controller.commands.imp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

import model.Config;
import controller.ControllerActions.AdminLogin;
import controller.ControllerParams;
import controller.ControllerResponse;
import controller.commands.Command;

public class CommandAdminLogin implements Command {

	@Override
	public ControllerResponse execute(ControllerParams params) {
		ControllerResponse response = new ControllerResponse();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiViCa");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();

		String password = params.getString(AdminLogin.Params.Password,"");
		if(password.length() < 6)
			response.putWarning(AdminLogin.Warnings.PasswordWeak, "The password is too weak (shorter than 6 chars)");
		
		Config config = em.find(Config.class, "admin_password");
		if(config == null && password.length() >= 6){
			config = new Config();
			config.setId("admin_password");
			config.setValue(password);
			em.persist(config);
		}
		
		boolean success = false;
		
		if(config.getValue().equals(password)){
			HttpSession session = (HttpSession) params.get("session");
			session.setAttribute("admin_logged", true);
			success = true;
		}
		
		em.getTransaction().commit();
		response.putResult(AdminLogin.Results.Success, success);
		
		return response;
	}

}
