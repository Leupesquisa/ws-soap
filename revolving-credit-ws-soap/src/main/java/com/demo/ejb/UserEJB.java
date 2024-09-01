package com.demo.ejb;

import com.demo.jpa.Transaction;
import com.demo.jpa.User;
import com.demo.util.LoginUtils;
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
@Stateless
public class UserEJB implements UserService {

    @PersistenceContext(unitName="revolving-PU")
    EntityManager em;

    @Override
    public User findByID(int id) {
        return em.find(User.class, id);
    }

    @Override
    public Collection<User> getAll() {
        return em.createNamedQuery("getUsers", User.class).getResultList();
    }

    @Override
    public int insert(User user) {
        em.persist(user);
        return em.merge(user).getId();
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public void delete(User user) {
        em.remove(em.merge(user));
    }

    @Override
    public User fakeLogin(String uname, String password) {
        String jpql = "select distinct u from User u where u.nome = :nome";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("nome", uname);
        User user = query.getSingleResult();
        
        if(user.getPassword().equals(LoginUtils.getPasswordHash(password))) {
            return user;
        }
        return null;
    }

}
