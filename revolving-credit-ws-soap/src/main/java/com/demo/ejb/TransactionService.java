package com.demo.ejb;

import com.demo.jpa.CreditLine;
import com.demo.jpa.TransactionMMI;
import com.demo.jpa.TransactionOTC;
import com.demo.jpa.Transaction;
import com.demo.jpa.User;
import java.util.Collection;
import jakarta.ejb.Local;

/**
 *
 * @author Leu A. Manuel
 */
@Local
public interface TransactionService {
     Transaction findByID(int id);

    Collection<Transaction> getAll();
    Collection<Transaction> getByCreditLine(CreditLine creditLine);
    Collection<CreditLine> getCreditLinesWithTransactions(boolean avaialable);
    
    Collection<TransactionOTC> getOTCByCreditLine(CreditLine creditLine, boolean avaialable);
    Collection<TransactionMMI> getMMIByCreditLine(CreditLine creditLine, boolean avaialable);
    
    int insert(Transaction transaction);
    void update(Transaction transaction);
    void delete(Transaction transaction);

    public Collection<Transaction> getTransactionsLoans(User user);
    
    public void payOff(Transaction e);
}
