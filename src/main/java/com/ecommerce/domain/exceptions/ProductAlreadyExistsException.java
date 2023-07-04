package com.ecommerce.domain.exceptions;

public class ProductAlreadyExistsException extends Exception{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductAlreadyExistsException(){
    }

    public ProductAlreadyExistsException(String menssage){
        super(menssage);
    }
}
