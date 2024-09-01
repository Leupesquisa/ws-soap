package com.demo.ejb;

import java.util.Collection;

import com.demo.jpa.Lender;
import java.util.Collection;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
public class LenderEJB implements LenderService {

    @PersistenceContext(unitName="revolving-PU")
    EntityManager em;

    @Override
    public Lender findByID(int id) {
        return em.find(Lender.class, id);
    }

    @Override
    public Collection<Lender> getAll() {
        return em.createNamedQuery("getLenders", Lender.class).getResultList();
    }

    @Override
    public int insert(Lender lender) {
        em.persist(lender);
        return em.merge(lender).getId();
    }

    @Override
    public void update(Lender lender) {
        em.merge(lender);
    }

    @Override
    public void delete(Lender lender) {
        em.remove(em.merge(lender));
    }

    @Override
    public Collection<Lender> getLendersFilterBy(String filter) {
        String jpql = "select e from Lender e where lower(e.nome) like :filter";
        TypedQuery<Lender> query = em.createQuery(jpql, Lender.class);
        query.setParameter("filter", "%" + filter.toLowerCase() + "%");
        return query.getResultList();
    }

}
