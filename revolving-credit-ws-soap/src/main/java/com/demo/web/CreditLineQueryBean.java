package com.demo.web;

import com.demo.ejb.CreditLineService;
import com.demo.jpa.CreditLine;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
@Named
@ConversationScoped
public class CreditLineQueryBean implements Serializable {

   @EJB
    CreditLineService service;
    
    @Inject
    private Conversation conversation;

    private String matchProductType;
    private String matchBorrower;
    private String matchLender;
    private String matchSubject;
    private String matchCountry;
    
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

    public String getMatchCountry() {
        return matchCountry;
    }

    public void setMatchCountry(String matchCountry) {
        this.matchCountry = matchCountry;
    }

    public String getMatchProductType() {
        return matchProductType;
    }

    public void setMatchProductType(String matchProductType) {
        this.matchProductType = matchProductType;
    }

    public String getMatchBorrower() {
        return matchBorrower;
    }

    public void setMatchBorrower(String matchBorrower) {
        this.matchBorrower = matchBorrower;
    }

    public String getMatchLender() {
        return matchLender;
    }

    public void setMatchLender(String matchLender) {
        this.matchLender = matchLender;
    }

    public String getMatchSubject() {
        return matchSubject;
    }

    public void setMatchSubject(String matchSubject) {
        this.matchSubject = matchSubject;
    }

    public Collection<CreditLine> getCreditLines() {
        return service.getByCriteria(matchProductType, matchCountry, matchBorrower, matchLender, matchSubject);
    }
    
    public Collection<Locale> getLocales() {
        return service.getLocales();
    }

}
