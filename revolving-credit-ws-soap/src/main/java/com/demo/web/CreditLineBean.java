package com.demo.web;

import com.demo.ejb.TransactionService;
import com.demo.ejb.CreditLineService;
import com.demo.jpa.Subject;
import com.demo.jpa.Transaction;
import com.demo.jpa.TransactionMMI;
import com.demo.jpa.TransactionOTC;
import com.demo.jpa.CreditLine;
import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.math.BigDecimal;

/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
@Named
@ConversationScoped
public class CreditLineBean implements Serializable {

    @EJB
    CreditLineService service;
    @EJB
    TransactionService transactionService;

    @Inject
    private Conversation conversation;

    private CreditLine current; // creditLine usado no formulário inserir / gravar
    private Transaction transaction;
    private int typeTransaction;

    private Subject nivel1;
    private Subject nivel2;
    private BigDecimal exposure = BigDecimal.ZERO;
    private int quantity = -1;

    @PostConstruct
    public void init() {
        if (conversation.isTransient() == false) {
            conversation.end();
        }
        conversation.begin();
        current = new CreditLine();
    }

    public Locale getLocale(String idioma) {
        return Locale.forLanguageTag(idioma);
    }

    public CreditLine findByID(int id) {
        return service.findByID(id);
    }

    public CreditLine getCurrent() {
        return current;
    }

    public void setCurrent(CreditLine creditLine) {
        this.current = creditLine;
        if (creditLine.getSubject() != null && creditLine.getSubject().getContext() != null && creditLine.getSubject().getContext().getContext() != null) {
            nivel1 = creditLine.getSubject().getContext().getContext();
            nivel2 = creditLine.getSubject().getContext();
        }
    }

    public Subject getNivel1() {
        return nivel1;
    }

    public void setNivel1(Subject nivel1) {
        this.nivel1 = nivel1;
        this.nivel2 = null;
    }

    public Subject getNivel2() {
        return nivel2;
    }

    public void setNivel2(Subject nivel2) {
        this.nivel2 = nivel2;
        this.current.setSubject(null);
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public int getTipoTransaction() {
        return typeTransaction;
    }

    public void setTipoTransaction(int typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public Collection<Transaction> getTransactiones() {
        return transactionService.getByCreditLine(current);
    }

    public BigDecimal getExposure() {
        return exposure;
    }

    public void setBigDecimal(BigDecimal exposure) {
        this.exposure = exposure;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String save() {
        if (current.getId() == 0) {
            
            if (typeTransaction == TransactionType.MMI.getValue()) {
                transaction = new TransactionMMI();
                ((TransactionMMI)transaction).setExposure(exposure);
            } else if (typeTransaction == TransactionType.OTC.getValue()) {
                transaction = new TransactionOTC();
                ((TransactionOTC)transaction).setQuantity(quantity);
            }
            transaction.setCreditLine(current);
            transaction.setAvailable(true);
            transactionService.insert(transaction);
        }
        service.update(current);
        conversation.end();
        return "creditLines";
    }

    public String cancel() {
        conversation.end();
        return "creditLines";
    }

    public String delete(CreditLine creditLine) {
        service.delete(creditLine);
        return "creditLines";
    }

    public String edit(CreditLine creditLine) {
        this.setCurrent(creditLine);
        return "creditLine";
    }
}
