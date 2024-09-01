package com.demo.web;

import com.demo.ejb.UserService;
import com.demo.jpa.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
@Named
@SessionScoped
public class FakeLoginBean implements Serializable {

    private String uname;
    private String password;
    private User userLogged;
    
    @EJB
    private UserService service;
    
    @Produces
    @Named("userLogged")
    public User getLoggedInUser() {
        return userLogged;
    }

    public String getUName() {
        return uname;
    }

    public void setUName(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserLogged(User userLogged) {
        this.userLogged = userLogged;
    }

    public String login() throws IOException {
        userLogged = service.fakeLogin(uname, password);
        if(userLogged == null) {
            return logout();
        }
        return "index"; 
    }

    public String logout() throws IOException {
        userLogged = null;
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        ectx.invalidateSession();
        return "fake-login";
    }
}