/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.ejb;

import com.demo.jpa.Subject;
import com.demo.jpa.Borrower;
import com.demo.jpa.Lender;
import com.demo.jpa.TransactionMMI;
import com.demo.jpa.TransactionOTC;
import com.demo.jpa.CreditLine;
import com.demo.jpa.User;
import com.demo.util.LoginUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;

/**
 *
 * @author helderdarocha
 */
@Stateless
public class TestServiceEJB {

    @PersistenceContext(unitName = "revolving-PU")
    private EntityManager em;
    
    @EJB SubjectService subjectService;

    public void removeAllEntities(List<String> entities) {
        for(String entity : entities) {
            em.createQuery("delete from "+entity+" e").executeUpdate();
        }
    }

    public void createTestData() {
        // Borrower
        String[] nomesBorroweres = {"Mary West", "Karl Und Der Dog", "Anderlaine do Nascimento", "Fubano de Tal", "Zambygumba Al Zubamm"};
        Borrower[] borroweres = new Borrower[nomesBorroweres.length];
        for(int i = 0; i < nomesBorroweres.length; i++) {
            Borrower borrower = new Borrower();
            borrower.setBName(nomesBorroweres[i]);
            em.persist(borrower);
            borroweres[i] = em.merge(borrower);
        }
        
        // Lender
        String[] nameLenders = {"Nobody's Books", "CreditLines Hipopótamo", "Gumba Dumba", "Big Books"};
        Lender[] lenders = new Lender[nameLenders.length];
        for(int i = 0; i < nameLenders.length; i++) {
            Lender lender = new Lender();
            lender.setLName(nameLenders[i]);
            em.persist(lender);
            lenders[i] = em.merge(lender);
        }
        
        // CreditLine
        createCreditLine("0123456789", "Era uma vez um bicho", "pt", lenders[0], new Borrower[] {borroweres[2]}, subjectService.getSubject("592", 3));
        createCreditLine("3344556677", "The Big Foot", "en", lenders[3], new Borrower[] {borroweres[0], borroweres[1]}, subjectService.getSubject("813", 3));
        createCreditLine("9786656656654", "Bugs and Bats", "en", lenders[1], new Borrower[] {borroweres[1]}, subjectService.getSubject("590", 3));
        createCreditLine("4343434343", "O Espaguete Voador", "pt", lenders[0], new Borrower[] {borroweres[3], borroweres[4]}, subjectService.getSubject("299", 3));
        createCreditLine("9786663636363", "Elfish para iniciantes", "pt", lenders[0], new Borrower[] {borroweres[3]}, subjectService.getSubject("491", 3));
        createCreditLine("9876543210", "There was a bat in the hat", "en", lenders[1], new Borrower[] {borroweres[0]}, subjectService.getSubject("827", 3));
        createCreditLine("4343434342", "Al Timbuk Nat Zugumba", "zu", lenders[2], new Borrower[] {borroweres[2]}, subjectService.getSubject("135", 3));
        
        // User
        String[] nomesUsers = {"maria", "joaquim"};
        for(String nome: nomesUsers) {
            User user = new User();
            user.setNome(nome);
            user.setPassword(LoginUtils.getPasswordHash("java")); // a password para todos é sempre "java"
            em.persist(user);
        }
        
        // Transaction
    }
    
    private void createCreditLine(String contractReference, String productType, String country, Lender lender, Borrower[] borroweres, Subject subject) {
        CreditLine creditLine = new CreditLine();
        creditLine.setContractReference(contractReference);
        creditLine.setProductCL(productType);
        creditLine.setCountry(country);
        creditLine.setLender(lender);
        creditLine.setBorroweres(Arrays.asList(borroweres));
        creditLine.setSubject(subject);
        em.persist(creditLine);
        
        // Cada creditLine criado incluir pelo menos ums transaction em OTC
        int quantity = new Random().nextInt(100) * 3 + 50;
        criarOTC(quantity, creditLine);
        
        // Criar mais transacoes em OTC randomicamente
        for(int i = 0; i < new Random().nextInt(2); i++) {
            criarOTC(quantity, creditLine);
        }
        
        
        
        Random random = new Random();
        // Gerar um número aleatório entre 0 (inclusive) e 100000 (exclusive)
        int numbRandom = random.nextInt(100000);
        // Criar um BigDecimal a partir do número aleatório
        BigDecimal exposure = BigDecimal.valueOf(numbRandom)
                .multiply(BigDecimal.valueOf(3)) // Multiplicar por 3
                .add(BigDecimal.valueOf(50000));
        
        for(int i = 0; i < new Random().nextInt(10); i++) {
            criarMMI(exposure, creditLine);
        }
        
        
        //  Se necess]ario faça o mapeamento do cascade para não precisar realizar alterações abaixo - MERGE, PERSIST
        // (e não precisar seguir uma ordem para remover as entidades - DELETE)
        lender.getProductType().add(creditLine);
        for(Borrower a: borroweres) {
            a.getProducts().add(creditLine);
        }
    }

    private void criarOTC(int quantity, CreditLine creditLine) {
        TransactionOTC transaction = new TransactionOTC();
        transaction.setCreditLine(creditLine);
        transaction.setQuantity( quantity );
        transaction.setAvailable(true);
        em.persist(transaction);
    }

    private void criarMMI(BigDecimal exposure, CreditLine creditLine) {
        TransactionMMI transaction = new TransactionMMI();
        transaction.setCreditLine(creditLine);
        transaction.setExposure(exposure);
        transaction.setAvailable(true);
        em.persist(transaction);
    }

}
