package controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ControllerResponse {
	
	/*
	 * By default there're no errors.
	 */
	private int errorCode = 200;
	
	private Map<String, Object> results = new HashMap<String, Object>();
	
	private Map<String, String> errors = new HashMap<String, String>();
	
	private Map<String, String> warnings = new HashMap<String, String>();
	
	/**
	 * The error code of the application.
	 * (This will be maybe deprecated).
	 * TODO check the usability of this.
	 * @return the error code number (200 means no error).
	 */
	public int getErrorCode() {
		return this.errorCode;
	}
	
	/**
	 * Sets an error code
	 * @param errorCode
	 */
	public void setErrorCode(int errorCode){
		this.errorCode = errorCode;
	}
	
	public void putResult(String name, Object result){
		results.put(name, result);
	}
	
	public void putError(String error, String description){
		errors.put(error, description);
	}
	
	public void putWarning(String error, String description){
		warnings.put(error, description);
	}
	
	public Object getResult(String name){
		return results.get(name);
	}
	
	public String getError(String name){
		return errors.get(name);
	}
	
	public String getWarning(String name){
		return warnings.get(name);
	}
	
	public class Pair<T,K> {
		
		public Pair(T key, K value) {
			super();
			this.key = key;
			this.value = value;
		}
		
		public T key;
		public K value;
	}
		
	private class MapIterable<T, K> implements Iterable<Pair<T,K>>{
		
		private Map<T,K> toIterate;
		public MapIterable(Map<T,K> toIterate){
			this.toIterate = toIterate;
		}

		@Override
		public Iterator<Pair<T,K>> iterator() {
			
			return new Iterator<Pair<T,K>>() {
				
				private Iterator<T> mapIterator = toIterate.keySet().iterator();

				@Override
				public boolean hasNext() {
					return mapIterator.hasNext();
				}

				@Override
				public Pair<T,K> next() {
					T n = mapIterator.next();
					return new Pair<T, K>(n, toIterate.get(n));
				}
			};
		}
	}
	
	public Iterable<Pair<String,Object>> getResults(){
		return new MapIterable<String, Object>(results);
	}
	
	public Iterable<Pair<String,String>> getWarnings(){
		return new MapIterable<String, String>(warnings);
	}
	
	public Iterable<Pair<String,String>> getErrors(){
		return new MapIterable<String, String>(errors);
	}
	
	private <E, T> String MapToJson(Iterable<Pair<E,T>> iterable){
		StringBuilder sb = new StringBuilder("");
		for(Pair<E,T> pair : iterable){
			sb.append('"').append(pair.key).append("\":");
			
			if(pair.value instanceof String && !((String)pair.value).startsWith("{"))
				sb.append('"').append(pair.value).append('"');
			else
				sb.append(pair.value.toString()).append(',');
		}
		
		if(!sb.toString().equals("") && sb.charAt(sb.length()-1) == ',')
			sb.deleteCharAt(sb.length()-1);
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		
		if(this.errors.size()>0)
			this.results.put("errors", "{" + MapToJson(getErrors()) +"}");
		if(this.warnings.size()>0)
			this.results.put("warnings", "{" + MapToJson(getWarnings()) +"}");
		
		StringBuilder sb = new StringBuilder("{");

		sb.append(MapToJson(getResults()));
		
		return sb.append("}").toString();
	}

}
