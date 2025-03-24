package com.alfa.billingApp.Exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resourseId,String resourceType) {
        super("Resource of type "+resourceType +" :"+ resourseId + " not found");
    }
}
