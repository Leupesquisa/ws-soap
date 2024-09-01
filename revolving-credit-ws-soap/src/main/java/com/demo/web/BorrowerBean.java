package com.demo.web;

import com.demo.ejb.CreditBookService;
import com.demo.ejb.TransactionService;
import com.demo.jpa.Transaction;
import com.demo.jpa.CreditLine;
import com.demo.jpa.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
@Named
@SessionScoped
public class BorrowerBean implements Serializable { // Login é necessário para acessar este bean

    @EJB CreditBookService creditBook;
    @EJB TransactionService transactionService;
    
    @Inject 
    private FakeLoginBean loginBean;
    
    private Map<Integer, Boolean> idsPayOff = new HashMap<>();
    private Collection<Transaction> payOffs;

    public Map<Integer, Boolean> getIdsPayOff() {
        return idsPayOff;
    }

    public Collection<Transaction> getPayOff() {
        return payOffs;
    }

    public User getUser() {
        return loginBean.getLoggedInUser();
    }
    
    public Collection<Transaction> getContent() {
        return creditBook.getContent(); 
    }
    
    public Collection<CreditLine> getcreditLinesWithTransactionesAvailable() {
        return transactionService.getCreditLinesWithTransactions(true);
    }
    
    public Collection<Transaction> getTransactionsBorrowings() {
        return transactionService.getTransactionsLoans(getUser()); 
    }
    
    public boolean selecionado(CreditLine creditLine) {
        if (creditBook.getContentMap().containsKey(creditLine.getContractReference())) {
            return true;
        }
        return false;
    }
    
    public int countOTC(CreditLine creditLine) {
        return transactionService.getOTCByCreditLine(creditLine, true).size();
    }
     
    public int countMMI(CreditLine creditLine) {
        return transactionService.getMMIByCreditLine(creditLine, true).size();
    }
    
    public String addMMI(CreditLine creditLine) {
        return addcreditLine(transactionService.getMMIByCreditLine(creditLine, true).iterator().next());
    }
    
    public String addOTC(CreditLine creditLine) {
        return addcreditLine(transactionService.getOTCByCreditLine(creditLine, true).iterator().next());
    }
    
    public Collection<Transaction> getBycreditLine(CreditLine creditLine) {
        return transactionService.getByCreditLine(creditLine);
    }
    
    public String addcreditLine(Transaction e) {
        creditBook.addCreditLine(e); 
        return "creditBook-borrow";
    }
    
    public String removecreditLine(Transaction e) {
        creditBook.removeCreditLine(e); // remove e marca como disponivel
        return null;
    }
    
    public String borrow() {
        creditBook.borrow(getUser());
        return "borrowings"; 
    }
    
    public String payOff() {
        // Get selected items.
        payOffs = new ArrayList<Transaction>();
        for (Transaction e : getTransactionsBorrowings()) {
            if (idsPayOff.get(e.getId())) {
                payOffs.add(e);
                idsPayOff.remove(e.getId());
            }
        }

        for(Transaction e : payOffs) {
            System.out.println(">>> Paying off the loan in full" + e.getId());
            transactionService.payOff(e);
        }
        return "borrowings"; 
    }
    
    public String cancel() {
        reset();
        return "borrow";
    }
    
    public void reset() {
        creditBook.reset();
    }
    
}
