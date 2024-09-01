package com.demo.web;

import com.demo.ejb.SubjectService;
import com.demo.ejb.BorrowerService;
import com.demo.ejb.LenderService;
import com.demo.ejb.TransactionService;
import com.demo.jpa.CreditLine;
import java.util.Collection;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import com.demo.ejb.CreditLineService;
import com.demo.ejb.UserService;
import com.demo.jpa.Subject;
import com.demo.jpa.Borrower;
import com.demo.jpa.Lender;
import com.demo.jpa.Transaction;
import com.demo.jpa.User;

/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
@Named
@RequestScoped
public class RevolvingBean {
   @EJB CreditLineService creditLineService;
    @EJB BorrowerService borrowerService;
    @EJB LenderService lenderService;
    @EJB SubjectService subjectService;
    @EJB UserService userService;
    @EJB TransactionService transactionService;
    
    // filter Subject by description
    // max level: 1, 2, 3
    // filter by group 100, 10, 1
    
    public Subject getSubject(String cdd, int summary) {
        return subjectService.getSubject(cdd, summary);
    }
    
    public Collection<Subject> getSubjects() {
        return subjectService.getRoots();
    }
    
    public Collection<Transaction> getTransactiones() {
        return transactionService.getAll();
    }
    
    public Collection<Transaction> getTransactiones(CreditLine creditLine) {
        return transactionService.getByCreditLine(creditLine);
    }
    
    public Collection<User> getUsers() {
        return userService.getAll();
    }
    
    public Collection<CreditLine> getCreditLines() {
        return creditLineService.getAll();
    }
    
    public Collection<Borrower> getBorroweres() {
        return borrowerService.getAll();
    }
    
    public Collection<Lender> getLenders() {
        return lenderService.getAll();
    }

}