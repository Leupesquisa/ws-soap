package com.demo.jpa;

/***
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name="getBorroweres", query="select o from Borrower o")
})
public class Borrower implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String bname;
    
    @ManyToMany(mappedBy = "borroweres")
    private Collection<CreditLine> products;

    public String getBName() {
        return bname;
    }

    public void setBName(String bname) {
        this.bname = bname;
    }

    public Collection<CreditLine> getProducts() {
        return products;
    }

    public void setProducts(Collection<CreditLine> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Borrower)) {
            return false;
        }
        Borrower other = (Borrower) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + id;
    }
    
}
