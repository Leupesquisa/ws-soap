
package com.demo.ejb;

import com.demo.jpa.Lender;
import java.util.Collection;
import jakarta.ejb.Local;
/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
@Local
public interface LenderService {
   Lender findByID(int id);
    
    Collection<Lender> getAll();
    Collection<Lender> getLendersFilterBy(String filter);
    
    int insert(Lender lender);
    void update(Lender lender);
    void delete(Lender lender);
}
