package com.demo.web;

import com.demo.ejb.SubjectService;
import com.demo.ejb.TestServiceEJB;
import java.util.ArrayList;
import java.util.List;
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
public class ConfigBean {
    
    @EJB SubjectService subjectService;
    @EJB TestServiceEJB testService;
    
    List<String> entity = new ArrayList<>();
    
    public boolean isAppNova() { 
        try {
            return subjectService.dataSize() == 0;
        } catch(Exception e) {
            return true; // no tables created
        }
    }

    public List<String> getEntidades() {
        return entity;
    }

    public void setEntidades(List<String> entity) {
        this.entity = entity;
    }
    
    public String configure() {
        subjectService.configure();
        return "index";
    }
    
    public String removeAllEntities() {
        testService.removeAllEntities(entity);
        return "index";
    }
    
    public String createTestData() {
        testService.createTestData();
        return "index";
    }

}
