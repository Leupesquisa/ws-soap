package com.demo.ejb;

import com.demo.jpa.Transaction;
import com.demo.jpa.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/*
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */


@Stateful
public class CreditBookEJB implements CreditBookService{
    private final Map<String, Transaction> transactions = new HashMap<>(); // state
    
    @PersistenceContext(unitName="revolving-PU")
    EntityManager em;

    @Override
    public void addCreditLine(Transaction e) {
        if(!transactions.containsKey(e.getCreditLine().getContractReference())) { // permite apenas uma transacao de cada linha de credito
            transactions.put(e.getCreditLine().getContractReference(), e);
            e.setAvailable(false);
            em.merge(e);
        }
    }

    @Override
    public void removeCreditLine(Transaction e) {
        e.setAvailable(true);
        if(transactions.containsKey(e.getCreditLine().getContractReference())) {
            transactions.remove(e.getCreditLine().getContractReference());
            em.merge(e);
        }
    }

    @Override
    public Collection<Transaction> getContent() {
        return transactions.values();
    }
    
    @Override
    public Map<String, Transaction> getContentMap() {
        return transactions;
    }

    @Override
    public void reset() {
        for(Transaction e : transactions.values()) {
            e.setAvailable(e.getUser() == null);
            em.merge(e);
        }
        transactions.clear();
    }

    @Override
    public void borrow(User u) {
        u = em.merge(u);
        for(Transaction e : this.getContent()) {
            e.setUser(u);
            u.getLoans().add(e);
            em.merge(e);
        }
        reset();
    }

}