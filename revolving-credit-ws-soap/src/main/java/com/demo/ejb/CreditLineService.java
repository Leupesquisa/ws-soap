
package com.demo.ejb;

import com.demo.jpa.CreditLine;
import java.util.Collection;
import java.util.Locale;
import jakarta.ejb.Local;

/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */

@Local
public interface CreditLineService {
    CreditLine findByID(int id);
    CreditLine findByContractReference(String contractReference);
    
    Collection<CreditLine> getAll();
    Collection<CreditLine> getByCriteria(String productType, String country, String borrower, String lender, String subject);
    
    Collection<Locale> getLocales();
    
    int insert(CreditLine creditLine);
    void update(CreditLine creditLine);
    void delete(CreditLine creditLine);
}
