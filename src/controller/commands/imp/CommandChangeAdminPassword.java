package controller.commands.imp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

import model.Config;
import controller.ControllerParams;
import controller.ControllerResponse;
import controller.ControllerActions.ChangeAdminPassword;
import controller.ControllerActions.GeneralErrors;
import controller.commands.Command;

public class CommandChangeAdminPassword implements Command {

	@Override
	public ControllerResponse execute(ControllerParams params) {
		ControllerResponse response = new ControllerResponse();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiViCa");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		HttpSession session = (HttpSession) params.get("session");
		Boolean admin_logged = (Boolean) session.getAttribute("admin_logged");
		if(admin_logged != null && admin_logged == true){
			Config config = em.find(Config.class, "admin_password");
			
			String newPass = params.getString("new_password");
			if(newPass != null && newPass.length() >= 6){
				config.setValue(newPass);
				em.persist(config);
			}else
				response.putError(ChangeAdminPassword.Warnings.PasswordWeak, "Password is too weak to change ( < 6 chars).");
		}else
			response.putError(GeneralErrors.IllegalAccess, "Not autorized.");
			
		return response;
	}

}
