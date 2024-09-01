package com.demo.web; // Adjust the package name if needed

import com.demo.ejb.TransactionService;
import com.demo.jpa.Transaction;
import com.demo.jpa.TransactionMMI;
import com.demo.jpa.TransactionOTC;
import java.io.Serializable;
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
public class TransactionBean implements Serializable {

    @EJB
    TransactionService service;

    @Inject
    private Conversation conversation;

    private Transaction current;

    private int type;

    @PostConstruct
    public void init() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        conversation.begin();
    }

    private void setup() {
        if (type == TransactionType.MMI.getValue()) {
            current = new TransactionMMI();
            this.type = TransactionType.MMI.getValue();
        } else {
            current = new TransactionOTC();
            this.type = TransactionType.OTC.getValue();
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        setup();
    }

    public Transaction findByID(int id) {
        return service.findByID(id);
    }

    public BigDecimal getExposure() {
        if (current instanceof TransactionMMI) {
            return TransactionMMI.class.cast(current).getExposure();
        }
        return BigDecimal.ZERO;
    }

    public void setExposure(BigDecimal exposure) {
        if (current instanceof TransactionMMI) {
            TransactionMMI.class.cast(current).setExposure(exposure);
        }
    }

    public int getQuantity() {
        if (current instanceof TransactionOTC) {
            return TransactionOTC.class.cast(current).getQuantity();
        }
        return -1;
    }

    public void setQuantity(int quantity) {
        if (current instanceof TransactionOTC) {
            TransactionOTC.class.cast(current).setQuantity(quantity);
        }
    }

    public Transaction getCurrent() {
        return current;
    }

    public void setCurrent(Transaction transaction) {
        this.current = transaction;
        if (current instanceof TransactionOTC) {
            this.type = TransactionType.OTC.getValue();
        } else {
            this.type = TransactionType.MMI.getValue();
        }
    }

    public String save() {
        service.update(current);
        conversation.end();
        return "transactions";
    }

    public String cancel() {
        conversation.end();
        return "transactions";
    }

    public String delete(Transaction transaction) {
        service.delete(transaction);
        return "transactions";
    }

    public String edit(Transaction transaction) {
        this.setCurrent(transaction);
        return "transaction";
    }
}