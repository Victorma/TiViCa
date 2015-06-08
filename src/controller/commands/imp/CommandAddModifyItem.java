package controller.commands.imp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.Item;
import controller.ControllerActions.AddModifyItem;
import controller.ControllerActions.GeneralErrors;
import controller.ControllerParams;
import controller.ControllerResponse;
import controller.commands.Command;

public class CommandAddModifyItem implements Command {

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
			
			Integer id = params.getInteger(AddModifyItem.Params.Id);
			if(id == null || id == -1)
				item = new Item();
			else
				item = em.find(Item.class, id);
			
			if(item == null){
				response.putError(AddModifyItem.Errors.ItemNotFound, "Item not found with id: " + id);
			}else{
				item.setName(params.getString(AddModifyItem.Params.Name));
				item.setStock(params.getInteger(AddModifyItem.Params.Stock));
				item.setPrice(params.getDouble(AddModifyItem.Params.Price));
				
				Object image = params.get(AddModifyItem.Params.Image);
				if(!(image instanceof String)){
					try {
						image = createFrom((Part) image, "./WebContent/images/");
						if(image!=null)
							item.setImage("/images/"+image);
					} catch (IOException e) {e.printStackTrace();}
				}else{
					//The power of "Guardar una imagen a pincho en la base de datos" ;)
					item.setImage((String) image);
				}
				
				em.persist(item);
				
				response.putResult("id", item.getId());
			}
		}else{
			response.putError(GeneralErrors.IllegalAccess, "Not autorized.");
		}
	
		
		em.getTransaction().commit();
		
		return response;
	}
	
	private String createFrom(Part filePart, String outputPath) throws IOException{

		String toReturn = null;
		// Extract image name from content-disposition header of part
		String imageName = getFileName(filePart);
 
		System.out.println("***** imageName: " + imageName);
 
		// Copy input file to destination path
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			File outputFilePath = new File(outputPath + imageName);
			System.out.println("***** outputFilePath: " + outputFilePath.getAbsolutePath());			
			inputStream = filePart.getInputStream();
			outputStream = new FileOutputStream(outputFilePath);
 
			int read = 0;
			final byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			
			toReturn = imageName;
		} 
		catch (FileNotFoundException fne) {fne.printStackTrace();} 
		finally {
			if (outputStream != null)outputStream.close();
			if (inputStream != null)inputStream.close();
		}
		
		return toReturn;
	}

	private String getFileName(Part part) {
		final String partHeader = part.getHeader("content-disposition");
		System.out.println("***** partHeader: " + partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}

}
