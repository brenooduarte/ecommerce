package com.api.ecommerce.exceptions;

public class ProductAlreadyExistsException extends Exception{

    public ProductAlreadyExistsException(){
    }

    public ProductAlreadyExistsException(String menssage){
        super(menssage);
    }
}
