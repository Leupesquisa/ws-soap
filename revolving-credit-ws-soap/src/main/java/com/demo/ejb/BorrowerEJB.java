package com.demo.ejb;

/***
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
import com.demo.jpa.Borrower;
import java.util.Collection;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class BorrowerEJB implements BorrowerService {

    @PersistenceContext(unitName="revolving-PU")
    EntityManager em;

    @Override
    public Borrower findByID(int id) {
        return em.find(Borrower.class, id);
    }

    @Override
    public Collection<Borrower> getAll() {
        return em.createNamedQuery("getBorroweres", Borrower.class).getResultList();
    }

    @Override
    public int insert(Borrower borrower) {
        em.persist(borrower);
        return em.merge(borrower).getId();
    }

    @Override
    public void update(Borrower borrower) {
        em.merge(borrower);
    }

    @Override
    public void delete(Borrower borrower) {
        em.remove(em.merge(borrower));
    }

    @Override
    public Collection<Borrower> getBorroweresFilterBy(String filter) {
        String jpql = "select a from Borrower a where lower(a.nome) like :filter";
        TypedQuery<Borrower> query = em.createQuery(jpql, Borrower.class);
        query.setParameter("filter", "%" + filter.toLowerCase() + "%");
        return query.getResultList();
    }

}
