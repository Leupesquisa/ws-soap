/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.demo.ejb;

/**
 *
 * @author Leu A. Manuel
 */
import com.demo.jpa.Subject;
import java.util.Collection;
import jakarta.ejb.Local;

@Local
public interface SubjectService {
    void configure();
    Subject getSubject(String cdd, int summary);
    Collection<Subject> getRoots();
    Collection<Subject> getAll();
    Collection<Subject> getByCriteria(int summary, String classe, String description);
    long dataSize();
}
