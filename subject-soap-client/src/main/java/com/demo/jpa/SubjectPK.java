package com.demo.jpa;

/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class SubjectPK implements Serializable, Comparable {
    private int ddcSummary;
    private String ddcClass;

    public int getDdcSummary() {
        return ddcSummary;
    }

    public void setDdcSummary(int ddcSummary) {
        this.ddcSummary = ddcSummary;
    }

    public String getDdcClass() {
        return ddcClass;
    }

    public void setDdcClass(String ddcClass) {
        this.ddcClass = ddcClass;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.ddcSummary;
        hash = 29 * hash + Objects.hashCode(this.ddcClass);
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
        final SubjectPK other = (SubjectPK) obj;
        if (this.ddcSummary != other.ddcSummary) {
            return false;
        }
        if (!Objects.equals(this.ddcClass, other.ddcClass)) {
            return false;
        }
        return true;
    }
    
        @Override
    public int compareTo(Object o) {
        SubjectPK other = (SubjectPK)o;
        return Integer.parseInt(this.ddcSummary * 1000 + this.ddcClass) - Integer.parseInt(other.ddcSummary * 1000 + other.ddcClass);
    }
}

