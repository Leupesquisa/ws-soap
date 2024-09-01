package com.demo.ejb;

/**
 *
 * @author Leu A. Manuel
 */
import com.demo.jpa.Borrower;
import java.util.Collection;
import jakarta.ejb.Local;

@Local
public interface BorrowerService {
    Borrower findByID(int id);
    
    Collection<Borrower> getAll();
    Collection<Borrower> getBorroweresFilterBy(String filter);
    
    int insert(Borrower borrower);
    void update(Borrower borrower);
    void delete(Borrower borrower);
}
