package com.ecommerce.domain.exceptions;

public class ProductAlreadyExistsException extends Exception{

    public ProductAlreadyExistsException(){
    }

    public ProductAlreadyExistsException(String menssage){
        super(menssage);
    }
}
