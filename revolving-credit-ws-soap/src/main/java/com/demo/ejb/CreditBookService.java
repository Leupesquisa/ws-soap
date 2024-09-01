package com.demo.ejb;

import com.demo.jpa.Transaction;
import com.demo.jpa.User;
import java.util.Collection;
import java.util.Map;
import jakarta.ejb.Local;
/*
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
@Local
public interface CreditBookService {

    void addCreditLine(Transaction e);
    void removeCreditLine(Transaction e);
    Collection<Transaction> getContent();
    Map<String, Transaction> getContentMap();
    void reset();
    void borrow(User u);
}
