package com.demo.ejb;

import com.demo.jpa.Transaction;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

/*
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */

@Singleton
@Startup
public class CrashRecoveryBean {
    
    @EJB TransactionService transactionService;

    @PostConstruct
    public void init() {
        // TODO: incluir flag para só fazer isto se houve saída anormal
        // verificar a consistencia da base de Transactiones
        for(Transaction e: transactionService.getAll()) {
            if(!e.isAvailable() && e.getUser() == null) { // credline  indisponivel e nao emprestada
                e.setAvailable(true);
                transactionService.update(e);
            }
        }
    }
}
