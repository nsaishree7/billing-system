package com.alfa.billingApp.AOP;

import com.alfa.billingApp.DTO.ErrorResponse;
import com.alfa.billingApp.utils.FileTracker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Executable;

@Aspect
@Component
public class FileTransactionAspect {


    @Autowired
    private FileTracker fileTracker;
    //the exception thrown from target method is bound in ex variable
    @AfterThrowing(value = "execution(* com.alfa.billingApp.service.InvoiceService.generateInvoice(..))",throwing = "ex")
    public ErrorResponse fileRollback(Exception ex){
        System.out.println("aspect called"+ex);
        File file=fileTracker.getFile();
        if (file != null && file.exists()) {
            boolean deleted = file.delete();
            System.out.println("PDF file deleted due to rollback: " + deleted);
        }
        fileTracker.clear();

        return new ErrorResponse(ex.getMessage(),"Roll back done", HttpStatus.EXPECTATION_FAILED);
    }

    @Around("execution(* com.alfa.billingApp.service.InvoiceService.generateInvoice(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            System.out.println("Before method execution");
            return joinPoint.proceed(); // Continue with the method execution
        } catch (Exception ex) {
            System.out.println("Exception caught: " + ex.getMessage());
            throw ex;
        }
    }
}
