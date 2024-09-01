
package com.demo.ejb;

import com.demo.jpa.Borrower;
import com.demo.jpa.CreditLine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */

@Stateless
public class CreditLineEJB implements CreditLineService {

    @PersistenceContext(unitName="revolving-PU")
    EntityManager em;

    @Override
    public CreditLine findByID(int id) {
        return em.find(CreditLine.class, id);
    }

    @Override
    public CreditLine findByContractReference(String contractReference) {
        String jpql = "select distinct creditLine from CreditLine creditLine where contractReference = :contractReference";
        TypedQuery<CreditLine> query = em.createQuery(jpql, CreditLine.class);
        query.setParameter("contractReference", contractReference);
        return query.getSingleResult();
    }
    
    @Override
    public Collection<CreditLine> getByCriteria(String matchProductType, String matchCountry, String matchBorrower, String matchLender, String matchSubject) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CreditLine> query = cb.createQuery(CreditLine.class);
        Root<CreditLine> root = query.from(CreditLine.class);
        
        List<Predicate> predicates = new ArrayList<>();

        // Se houver texto no campo productType
        if(matchProductType != null && !matchProductType.trim().isEmpty()) {
            Path<String> path = root.get("productType");
            predicates.add(cb.like(cb.lower(path), "%"+matchProductType.toLowerCase()+"%"));
        }
        
        // Se houver texto no campo country
        if(matchCountry != null && !matchCountry.trim().isEmpty()) {
            Path<String> path = root.get("country");
            predicates.add(cb.equal(cb.lower(path), matchCountry.toLowerCase()));
        }

        // Se houver texto no campo borrower
        if(matchBorrower != null && !matchBorrower.trim().isEmpty()) {
            Join<CreditLine, Borrower> borrower = root.join("borroweres");
            Path<String> path = borrower.get("bname");
            predicates.add(cb.like(cb.lower(path), "%"+matchBorrower.toLowerCase()+"%"));
        }
        
        // Se houver texto no campo lender
        if(matchLender != null && !matchLender.trim().isEmpty()) {
            Path<String> path = root.get("lender").get("nome");
            predicates.add(cb.like(cb.lower(path), "%"+matchLender.toLowerCase()+"%"));
        }
        
        // Se houver texto no campo subject
        if(matchSubject != null && !matchSubject.trim().isEmpty()) {
            List<Predicate> orPredicates = new ArrayList<>();
            Path[] paths = {root.get("subject").get("description"),
                            root.get("subject").get("context").get("description"),
                            root.get("subject").get("context").get("context").get("description")};
            for(int i = 0; i < paths.length; i++) {
                orPredicates.add(cb.like(cb.lower(paths[i]), "%"+matchSubject.toLowerCase()+"%"));
            }
            
            Path subjectPath = root.get("subject").get("id").get("ddcClass");
            orPredicates.add(cb.like(cb.lower(subjectPath), matchSubject.toLowerCase()+"%"));
            
            predicates.add(cb.or(orPredicates.toArray(new Predicate[orPredicates.size()])));
        }

        if(!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }
        
        query.select(root);
        TypedQuery<CreditLine> q = em.createQuery(query);
        
        return q.getResultList();
    }

    @Override
    public Collection<CreditLine> getAll() {
        return em.createNamedQuery("getCreditLines", CreditLine.class).getResultList();
    }

    @Override
    public int insert(CreditLine creditLine) {
        em.persist(creditLine);
        return em.merge(creditLine).getId();
    }

    @Override
    public void update(CreditLine creditLine) {
        em.merge(creditLine);
    }

    @Override
    public void delete(CreditLine creditLine) {
        em.remove(em.merge(creditLine));
    }

    @Override
    public Collection<Locale> getLocales() {
        String jpql = "select distinct creditLine.country from CreditLine creditLine";
        TypedQuery<String> query = em.createQuery(jpql, String.class);
        List<String> countrys = query.getResultList();
        List<Locale> locales = new ArrayList<>();
        for(String country : countrys) {
            Locale locale = Locale.forLanguageTag(country);
            if(locale == null) {
                locale = new Locale(country);
            }
            locales.add(locale);
        }
        return locales;
    }

}
