package com.demo.web;

import com.demo.ejb.LenderService;
import com.demo.jpa.Lender;
import java.util.Collection;
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
public class LenderBean {
    
@EJB LenderService service;
    
    private Lender current; 
    private String search = "";
    
    @PostConstruct
    public void init() {
        current = new Lender();
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Lender getCurrent() {
        return current;
    }

    public void setCurrent(Lender lender) {
        this.current = lender;
    }
    
    public Collection<Lender> getLenders() {
        return service.getLendersFilterBy(search);
    }
    
    public Lender findByID(int id) {
        return service.findByID(id);
    }
    
    public String gravar() {
        service.update(current);
        return "lenders";
    }
    
    public String delete(Lender lender) {
        service.delete(lender);
        return "lenders";
    }
    
    public String edit(Lender lender) {
        this.setCurrent(lender);
        return "lender";
    }
}
