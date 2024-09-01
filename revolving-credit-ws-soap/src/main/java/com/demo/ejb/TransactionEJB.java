package com.demo.ejb;

import com.demo.jpa.Transaction;
import com.demo.jpa.TransactionMMI;
import com.demo.jpa.TransactionOTC;
import com.demo.jpa.CreditLine;
import com.demo.jpa.User;
import java.util.Collection;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
@Stateless
public class TransactionEJB implements TransactionService {

    @PersistenceContext(unitName="revolving-PU")
    EntityManager em;

    @Override
    public Transaction findByID(int id) {
        return em.find(Transaction.class, id);
    }

    @Override
    public Collection<Transaction> getByCreditLine(CreditLine creditLine) {
        String jpql = "select distinct transaction from Transaction transaction where transaction.creditLine = :creditLine";
        TypedQuery<Transaction> query = em.createQuery(jpql, Transaction.class);
        query.setParameter("creditLine", creditLine);
        return query.getResultList();
    }
    
    @Override
    public Collection<CreditLine> getCreditLinesWithTransactions(boolean available) {
        String jpql = "select distinct creditLine from CreditLine creditLine, Transaction transaction where transaction.creditLine = creditLine and transaction.available = :available";
        TypedQuery<CreditLine> query = em.createQuery(jpql, CreditLine.class);
        query.setParameter("available", available);
        return query.getResultList();
    }
    
    @Override
    public Collection<Transaction> getTransactionsLoans(User user) {
        String jpql = "select distinct e from Transaction e where e.user = :user";
        TypedQuery<Transaction> query = em.createQuery(jpql, Transaction.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public Collection<Transaction> getAll() {
        return em.createNamedQuery("getTransactiones", Transaction.class).getResultList();
    }

    @Override
    public int insert(Transaction transaction) {
        em.persist(transaction);
        return em.merge(transaction).getId();
    }

    @Override
    public void update(Transaction transaction) {
        em.merge(transaction);
    }

    @Override
    public void delete(Transaction transaction) {
        em.remove(em.merge(transaction));
    }

    @Override
    public Collection<TransactionOTC> getOTCByCreditLine(CreditLine creditLine, boolean available) {
        String jpql = "select transaction from TransactionOTC transaction where transaction.creditLine = :creditLine and transaction.available = :available";
        TypedQuery<TransactionOTC> query = em.createQuery(jpql, TransactionOTC.class);
        query.setParameter("creditLine", creditLine);
        query.setParameter("available", available);
        return query.getResultList();
    }

    @Override
    public Collection<TransactionMMI> getMMIByCreditLine(CreditLine creditLine, boolean available) {
        String jpql = "select transaction from TransactionMMI transaction where transaction.creditLine = :creditLine and transaction.available = :available";
        TypedQuery<TransactionMMI> query = em.createQuery(jpql, TransactionMMI.class);
        query.setParameter("creditLine", creditLine);
        query.setParameter("available", available);
        return query.getResultList();
    }

    @Override
    public void payOff(Transaction e) {
        e = em.merge(e);
        e.setAvailable(true);
        User u = e.getUser();
        em.merge(u);
        u.getLoans().remove(e);
        e.setUser(null);
    }

}
