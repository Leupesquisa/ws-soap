package com.demo.ws;

/**
 *
 * @author Leu A. Manuel
 */

import com.demo.jpa.Subject;
import java.util.Collection;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://revolving.ws")
public interface SubjectWebService {
    Subject getSubject(String cdd, int summary);
    Collection<Subject> getRoots();
    Collection<Subject> getAll();
    Collection<Subject> getByCriteria(int summary, String classe, String description);
    long dataSize();
    
}