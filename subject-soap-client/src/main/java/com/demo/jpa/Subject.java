package com.demo.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
@Entity
@XmlRootElement // Necessário para JAXB
@XmlAccessorType(XmlAccessType.FIELD) // Necessario para JAXB (para considerar anotações nos atributos)
public class Subject implements Serializable, Comparable {

    @EmbeddedId
    private SubjectPK id;
    
    private String description;
    
    @XmlTransient // necessario para evitar ciclos na geracao do XML usado em web services
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name="context_ddcSummary", referencedColumnName="ddcSummary"),
        @JoinColumn(name="context_ddcClass", referencedColumnName="ddcClass")
    })
    private Subject context;
    
    @OneToMany(mappedBy = "context", cascade=CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(name="subjects_ddcSummary", referencedColumnName="ddcSummary", insertable = false, updatable = false),
        @JoinColumn(name="subjects_ddcClass", referencedColumnName="ddcClass", insertable = false, updatable = false)
    })
    private List<Subject> subjects = new ArrayList<>(10);
    
    public SubjectPK getId() {
        return id;
    }

    public void setId(SubjectPK id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Subject getContexto() {
        return context;
    }

    public void setContexto(Subject context) {
        this.context = context;
    }

    public List<Subject> getSubjects() {
        Collections.sort(subjects);
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subject)) {
            return false;
        }
        Subject other = (Subject) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if(context != null) {
            return context.toString() + "/" + id.getDdcClass();
        }
        return id.getDdcClass();
    }

    @Override
    public int compareTo(Object o) {
        return this.id.compareTo(((Subject)o).id);
    }

}

