package com.demo.web;

import com.demo.ejb.UserService;
import com.demo.jpa.User;
import com.demo.util.LoginUtils;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
@Named
@RequestScoped
public class UserBean {

    @EJB UserService service;
    
    private User current; 
    
    @PostConstruct
    public void init() {
        current = new User();
    }

    public User getCurrent() {
        return current;
    }
    
    public User findByID(int id) {
        return service.findByID(id);
    }

    public void setCurrent(User user) {
        this.current = user;
    }
    
    public String save() {
        this.current.setPassword(LoginUtils.getPasswordHash("java")); // a senha para todos os usuários é "java"
        service.update(current);
        return "users";
    }
    
    public String delete(User user) {
        service.delete(user);
        return "users";
    }
    
    public String edit(User user) {
        this.setCurrent(user);
        return "user";
    }
}
