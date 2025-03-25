# Billing System

## Description
    A web application for managing companies, tracking their purchase orders, mapping products with company-specific pricing, and generating invoices.

## Tech stack 
    1. Backend: Spring boot,spring data JPA,SQL
    2. Frontend: react.js, redux toolkit
    3. miscellaneous: AOP,DTO,Factory design pattern,Transaction management

## Features
    a.User friendly UI for easy addition of product /company /purchase order numbers similar to this
<img src="proj1.png" alt="Description" width="500">
<img src="proj2.png" alt="Description" width="500">


    b.check status

<img src="proj3.png" alt="Description" width="500">


    c.Generating pdf invoice
        utilized OpenPdf to convert an exisiting html template to pdf.
        saving the pdf location in database considering the database performance and faster retrival of files
<img src="bill.png" alt="Description" width="500">


    d.Transaction management
        Implemented transaction management while exception in invoice generation.
        Used Aspect Oriented Programming to rollback file generated

    e.Adhered to Data Transfer Objects concept hence reducing sensitive data exposure

    e.Explored creating continuos and unique invoiceNo (with significantly less gaps (since gaps in invoice can give legal trouble))

            
        1. pk guarantees  uniqueness but not continuity

            problems faced while using generator sequence to rollback primary key
            1.not able to get the same transaction manager thread hence considered as a separate transaction and @Transactional doesn't rollback


        Generated IDs are usually incremented outside the transaction,
        or else
         could create redundant pk
                 1. nextVal -> x
                 2. saved to db x pk
                 3.nextVal ->x+1
                 4.saved to db x+1 pk entry
                 5. x pk exception tries rollback ,updates nextVal  ->x+1-1 ->x
                 6.next pk entry would be x+1 race condtn

        to solve this we have to block all other transaction that need to insert new rows.



        2. Solution came up with

            have invoiceId as pk for invoiceId table

            Table(invoice_no)
            userId invoiceId      invoiceNo  set/notSet
                                    (unique
                                        ,null)

            lock the invoice_id for userid, record alone, with pessimistic lock
            a.update invoice no after all steps in invoice db
                so that exception in other steps doesn't require rollback of invoice number
            b. update invoiceNo to max(invoiceNo)+1

