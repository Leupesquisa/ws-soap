package com.demo.jpa;

/***
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@NamedQueries({
    @NamedQuery(name="getCreditLines", query="select o from CreditLine o")
})
public class CreditLine implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String productCL;
    
    // Bean Validation
    @Pattern(message="CRED (10 cod) ou CRED-13 (13 id start with 978 or 979)", regexp="^(97(8|9))?\\d{9}(\\d|X)$")
    private String contractReference;
    
    // Bean Validation
    @Size(min=2, max=2, message="Country (id  2 lettes) is mandatory")
    private String country;
    
    @ManyToMany
    private List<Borrower> borrowers;
    
    // Bean Validation
    @NotNull(message="Lender is mandatory")
    @ManyToOne
    private Lender lender;
    
    // Bean Validation
    @NotNull(message="Select a subject ( between 3 menu)")
    @ManyToOne
    private Subject subject;
    
    @OneToMany(mappedBy="livro", cascade=CascadeType.REMOVE)
    private List<Transaction> transaction; 

    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Lender getLender() {
        return lender;
    }

    public void setLender(Lender lender) {
        this.lender = lender;
    }

    public List<Borrower> getBorroweres() {
        return borrowers;
    }

    public void setBorroweres(List<Borrower> borrowers) {
        this.borrowers = borrowers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductCL() {
        return productCL;
    }

    public void setProductCL(String productCL) {
        this.productCL = productCL;
    }

    public String getContractReference() {
        return contractReference;
    }

    public void setContractReference(String contractReference) {
        this.contractReference = contractReference;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contractReference == null) ? 0 : contractReference.hashCode());
        return result;
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
        CreditLine other = (CreditLine) obj;
        if (contractReference == null) {
            if (other.contractReference != null) {
                return false;
            }
        } else if (!contractReference.equals(other.contractReference)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + id;
    }

}
