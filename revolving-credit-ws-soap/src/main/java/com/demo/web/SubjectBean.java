package com.demo.web;

import com.demo.ejb.SubjectService;
import com.demo.jpa.Subject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
/***
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */

@Named
@ConversationScoped
public class SubjectBean implements Serializable {

    @EJB
    SubjectService service;
    
    @Inject
    private Conversation conversation;

    private String filtroDescription;
    private String filtroClasse;
    private int filtroSummary;
    
    @PostConstruct
    public void init() {
        if (conversation.isTransient() == false) {
            conversation.end();
        }
        conversation.begin();
    }
    
    @PreDestroy
    public void destroy() {
        conversation.end();
    }

    public String getFiltroDescription() {
        return filtroDescription;
    }

    public void setFiltroDescription(String filtroDescription) {
        this.filtroDescription = filtroDescription;
    }

    public String getFiltroClasse() {
        return filtroClasse;
    }

    public void setFiltroClasse(String filtroClasse) {
        this.filtroClasse = filtroClasse;
    }

    public int getFiltroSummary() {
        return filtroSummary;
    }

    public void setFiltroSummary(int filtroSummary) {
        this.filtroSummary = filtroSummary;
    }

    public Collection<Subject> getSubjects() {
        return service.getByCriteria(filtroSummary, filtroClasse, filtroDescription);
    }

}
