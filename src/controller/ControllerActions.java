package controller;

public class ControllerActions {
	
	public static class GeneralErrors {
		
		/**
		 * This error appears when the database is inaccessible.
		 */
		public static final String DataError = "DataError";
		
		/**
		 * A parameter is malformed, missing or critical.
		 */
		public static final String ParamError = "ParamError";
		
		/**
		 * A action is executed without having enough permissions.
		 */
		public static final String IllegalAccess = "IllegalAccess";
	}
	
	/**
	 * 
	 * @author Victorma
	 *
	 */
	public static class ItemList {
		public static final String ActionName = "ItemList";
		
		public static class Params {			
			/**
			 * [Optional, Default = 1]
			 * The page to be retrieved.
			 */
			public static final String Page = "page";
			
			/**
			 * [Optional, Default = 10]
			 * Max number of results to be retrieved.
			 */
			public static final String Limit = "limit";
		}
		
		public static class Results{
			
			/**
			 * List<Item> according to the request.
			 */
			public static final String Items = "items";
		}
		
		public static class Warnings {}
		public static class Errors {}
	}
	
	public static class PutAddItemInCart {
		public static final String ActionName = "PutAddItemInCart";
		
		public static class Params {			
			/**
			 * [Required]
			 * The item id to add into cart
			 */
			public static final String Id = "id";
			
			/**
			 * [Required]
			 * The ip of the requester.
			 */
			public static final String Ip = "ip";
			
			/**
			 * [Required]
			 * The quantity.
			 */
			public static final String Quantity = "quantity";
		}
		
		public static class Results {}
		public static class Warnings {}
		public static class Errors {
			/**
			 * When the id doesn't belong to any item.
			 */
			public static final String ItemNotFound = "itemNotFound";
			
			/**
			 * When there's not enough stock of the item to put into the cart.
			 */
			public static final String InsufficientStock = "insufficientStock";
		}
	}
	
	public static class RemoveDeductItemFromCart {
		public static final String ActionName = "removeDeductItemFromCart";
		
		public static class Params {			
			/**
			 * [Required]
			 * The item id to remove/deduct from cart
			 */
			public static final String Id = "id";
			
			/**
			 * [Required]
			 * The ip of the requester.
			 */
			public static final String Ip = "ip";
			
			/**
			 * [Required]
			 * The quantity.
			 */
			public static final String Quantity = "quantity";
		}
		
		public static class Results {}
		public static class Warnings {
			/**
			 * When the item isn't actually in the cart.
			 */
			public static final String ItemNotInCart = "itemNotInCart";
		}
		public static class Errors {
			/**
			 * When the id doesn't belong to any item.
			 */
			public static final String ItemNotFound = "itemNotFound";
			
			/**
			 * When who knows why, there's no cart.
			 */
			public static final String CartNotFound = "cartNotFound";
		}
	}
	
	public static class ViewCart {
		public static final String ActionName = "viewCart";
		
		public static class Params {			
			
			/**
			 * [Required]
			 * The ip of the requester.
			 */
			public static final String Ip = "ip";
		}
		
		public static class Results {
			
			/**
			 * The cart with all the cart lines items and quantities.
			 */
			public static final String Cart = "cart";
			
		}
		public static class Warnings {}
		public static class Errors {}
	}
	
	public static class AdminLogin {
		public static final String ActionName = "adminLogin";
		
		public static class Params {			
			
			/**
			 * [Required]
			 * The password to try.
			 */
			public static final String Password = "password";
		}
		
		public static class Results {
			
			/**
			 * True if login succeeded or false otherwise.
			 */
			public static final String Success = "success";
			
		}
		public static class Warnings {
			/**
			 * When the password is shorter than 6 characters.
			 */
			public static final String PasswordWeak = "passwordWeak";
		}
		public static class Errors {}
	}

	public static class ChangeAdminPassword {
		public static final String ActionName = "changeAdminPassword";
		
		public static class Params {			
			
			/**
			 * [Required]
			 * The password to change to.
			 */
			public static final String Password = "password";
		}
		
		public static class Results {
			
			/**
			 * True if login succeeded or false otherwise.
			 */
			public static final String Success = "success";
			
		}
		public static class Warnings {
			/**
			 * When the password is shorter than 6 characters.
			 */
			public static final String PasswordWeak = "passwordWeak";
		}
		public static class Errors {}
	}
	
	public static class AddModifyItem {
		public static final String ActionName = "addModifyItem";
		
		public static class Params {	
			
			/**
			 * [Optional]
			 * Item id, used only if you want to modify.
			 */
			public static final String Id = "id";
			
			/**
			 * [Required]
			 * Item Name
			 */
			public static final String Name = "name";
			
			/**
			 * [Required]
			 * Item stock
			 */
			public static final String Stock = "stock";
			
			/**
			 * [Required]
			 * Item price
			 */
			public static final String Price = "price";
			
			/**
			 * [Required]
			 * Image encapsulated inside string (:D)
			 */
			public static final String Image = "image";
		}
		
		public static class Results {
			
			/**
			 * Id of the item added/modified
			 */
			public static final String Id = "id";
			
		}
		public static class Warnings {}
		public static class Errors {
			
			/**
			 * When there's no item with that id to modify.
			 */
			public static final String ItemNotFound = "itemNotFound";
		}
	}
	
	public static class RemoveItem {
		public static final String ActionName = "removeItem";
		
		public static class Params {	
			
			/**
			 * [Required]
			 * Item id, used only if you want to modify.
			 */
			public static final String Id = "id";
		}
		
		public static class Results {}
		public static class Warnings {}
		public static class Errors {
			
			/**
			 * When there's no item with that id to modify.
			 */
			public static final String ItemNotFound = "itemNotFound";
		}
	}
	
	public static class SubmitCart {
		public static final String ActionName = "submitCart";
		
		public static class Params {	
			
			/**
			 * [Required]
			 * The ip of the requester.
			 */
			public static final String Ip = "ip";
			
			/**
			 * [Required]
			 * The name of the person who buys.
			 */
			public static final String Name = "name";
			
			/**
			 * [Required]
			 * The address of the person who buys.
			 */
			public static final String Address = "address";
			
			/**
			 * [Required]
			 * Any comments to the order.
			 */
			public static final String Comments = "comments";
		}
		
		public static class Results {
			/**
			 * The email to the seller.
			 */
			public static final String Email = "email";
			
			/**
			 * Warnings about stocks that are really low (bellow 5).
			 */
			public static final String Advices = "advices";
		}
		public static class Warnings {}
		public static class Errors {
			
			/**
			 * When there's no cart or it has no items in.
			 */
			public static final String CartIncorrect = "cartIncorrect";
			
			/**
			 * When there's not enough stock to sell the item.
			 */
			public static final String InsufficientStock = "insufficientStock";
		}
	}
}
