package com.demo.jpa;

import java.io.Serializable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
/***
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
@Entity
@NamedQueries({
    @NamedQuery(name="getTransactions", query="select o from Transaction o")
})
public abstract class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private boolean available;
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    private CreditLine creditLine;
    @ManyToOne
    private User user;
    
    public abstract String getType();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CreditLine getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(CreditLine creditLine) {
        this.creditLine = creditLine;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return "com.demo.jpa.Transaction[ id=" + id + " ]";
    }
    
}
