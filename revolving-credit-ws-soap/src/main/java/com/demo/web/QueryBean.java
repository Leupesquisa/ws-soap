package com.demo.web;

import com.demo.jpa.Borrower;
import com.demo.jpa.Lender;
import com.demo.jpa.Transaction;
import com.demo.jpa.TransactionMMI;
import com.demo.jpa.CreditLine;
import java.util.Collection;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
@Named(value = "queryBean")
@RequestScoped
public class QueryBean {

    @PersistenceContext(unitName = "revolving-PU")
    private EntityManager em;

    public Collection<Borrower> getBorrowerByName() {
            String jpql="select a from Borrower a where a.bname like '%an%'";
            TypedQuery<Borrower> query = em.createQuery(jpql, Borrower.class);
            return query.getResultList();
        }

        public Collection<Lender> getLenderByName() {
            String jpql="select e from Lender e where e.lname like '%B%'";
            TypedQuery<Lender> query = em.createQuery(jpql, Lender.class);
            return query.getResultList();
        }

        public Collection<CreditLine> getCreditLineByTitulo() {
            String jpql="select o from CreditLine o where o.productType like '%The%'";
            TypedQuery<CreditLine> query = em.createQuery(jpql, CreditLine.class);
            return query.getResultList();
        }
        
        public Collection<CreditLine> getCreditLineByAssunto() {
            String jpql="select o from CreditLine o where o.subject.id.ddcClass like '5%'";
            TypedQuery<CreditLine> query = em.createQuery(jpql, CreditLine.class);
            return query.getResultList();
        }
        
        public Collection<CreditLine> getCreditLineByBorrower() {
            String jpql="select o from CreditLine o join o.borroweres borrower where borrower.bname like 'Mary%'";
            TypedQuery<CreditLine> query = em.createQuery(jpql, CreditLine.class);
            return query.getResultList();
        }

        public Collection<Borrower> getBorrowerByLender() {
            String jpql="select a from Borrower a join a.products product where product.lender.bname like '%Boo%'";
            TypedQuery<Borrower> query = em.createQuery(jpql, Borrower.class);
            return query.getResultList();
        }
        
        // Exercicio 10 
        public Long getTransactionsByCreditLine() {
            String jpql="select count(e) from Transaction e where e.creditLine.productType like '%The%'";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            return query.getSingleResult();
        }

        public Collection<Transaction> getTransactionsLargestQuantity() {
            String jpql="select e from TransactionOTC e where e.quantity > 250";
            TypedQuery<Transaction> query = em.createQuery(jpql, Transaction.class);
            return query.getResultList();
        }
       

        public Collection<Transaction> getTransactionsBorrowingsByUser() {
            String jpql="select e from Transaction e where e.user.Uname = 'joaquim'";
            TypedQuery<Transaction> query = em.createQuery(jpql, Transaction.class);
            return query.getResultList();
        }

        
        public Collection<Transaction> getTransactionsBorrowings() {
            String jpql="select e from Transaction e where e.user is not null";
            TypedQuery<Transaction> query = em.createQuery(jpql, Transaction.class);
            return query.getResultList();
        }
        
        public Collection<Transaction> getTransactionsByType() {
            String jpql="select e from Transaction e where type(e) = :type";
            TypedQuery<Transaction> query = em.createQuery(jpql, Transaction.class);
            query.setParameter("type", TransactionMMI.class);
            return query.getResultList();
        }
        
                
        public Collection<CreditLine> getCreditLinesBorrowings() {
            String jpql="select distinct o from CreditLine o where (select count(e) from Transaction e where e.creditLine = o and e.user is null) = 0";
            TypedQuery<CreditLine> query = em.createQuery(jpql, CreditLine.class);
            return query.getResultList();
        }

}
