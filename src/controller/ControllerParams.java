package controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ControllerParams {
	
	/*
	 * attributes
	 */
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	/*
	 * setters
	 */
	
	public void set(String key, Object value) {
		params.put(key, value);
	}
	
	public void setInteger(String key, Integer value){set(key,value);}
	public void setString(String key, String value){set(key,value);}
	public void setDouble(String key, Double value){set(key,value);}
	
	/*
	 * getters
	 */
	
	public Object get(String key){ return get(key,null); }
	
	public Object get(String key, Object def){
		Object r = def;
		if(params.containsKey(key))
			r = params.get(key);
		return r;
	}
	
	public Integer getInteger(String key) { return getInteger(key,null); }
	public String getString(String key) { return getString(key,null); }
	public Double getDouble(String key) { return getDouble(key,null); }
	
	public Integer getInteger(String key, Integer def) {
		Object o = get(key,def);
		if(o != null) o = Integer.parseInt(o.toString());
		return (Integer) o; 
	}
	public String getString(String key, String def) { 
		Object o = get(key,def);
		if(o != null) o = o.toString();
		return (String) o; 
	}
	public Double getDouble(String key, Double def) { 
		Object o = get(key,def);
		if(o != null) o = Double.parseDouble(o.toString());
		return (Double) o; 
	}
	
	/*
	 * aux
	 */
	
	/**
	 * To be used to use this controller in a web-based environments.
	 * @param json the JSON file to be parsed.
	 * @return a new ConttollerParams class with the content of the JSON.
	 */
	public static ControllerParams FromRequest(HttpServletRequest request){
		
		ControllerParams cp = new ControllerParams();
		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()){
			String parameter = params.nextElement();
			cp.set(parameter, request.getParameter(parameter));
		}
		/*try { Intento de parsear imágenes :]
			for(Part part : request.getParts())
				cp.set(part.getName(), part);
		} 
		catch (IOException e) { e.printStackTrace();} 
		catch (ServletException e) { e.printStackTrace(); }*/
		
		return cp;
	}

}
