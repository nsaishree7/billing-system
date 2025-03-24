package com.alfa.billingApp.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.EventType;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Component
public class DbSequenceGenerator implements IdentifierGenerator {

    //session factory responsible for creating sessions to communicate with db
    //entitymanager therad to do crud ops
    //new entitymanager is not created ,using the existing one
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional // Transactional annotation ensures rollback on failure
    public synchronized Serializable generate(SharedSessionContractImplementor session, Object o) {
        // Fetch the next ID using pessimistic locking
        Long nextId = (Long) entityManager.createQuery(
                        "SELECT s.nextVal FROM InvoiceIdGenerator s WHERE s.entityName = :entity")
                .setParameter("entity", "Invoice")
                .setLockMode(LockModeType.PESSIMISTIC_WRITE) // Prevents concurrent modifications
                .getSingleResult();

        // Update the sequence value
        entityManager.createQuery(
                        "UPDATE InvoiceIdGenerator s SET s.nextVal = :next WHERE s.entityName = :entity")
                .setParameter("next", nextId + 1)
                .setParameter("entity", "Invoice")
                .executeUpdate();

        return nextId;
    }



}
