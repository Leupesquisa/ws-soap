package com.demo.ejb;

/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
import com.demo.jpa.Subject;
import com.demo.ws.SubjectWebService;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.jws.WebService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


@Singleton
@Startup
@WebService(portName = "SubjectWebPort", // opcional - default será SubjectEJBPort
            serviceName = "SubjectWebService", // opcional - default será SubjectEJBService
            targetNamespace = "http://revolving.ws", // opcional - default será http://revolving.ejb (nome do pacote)
            endpointInterface = "revolving.ws.SubjectWebService") // opcional - se não houver, todos os metodos serão incluidos
public class SubjectEJB implements SubjectService, SubjectWebService {

    @PersistenceContext(unitName = "revolving-PU")
    EntityManager em;

    @Override
    public void configure() {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader reader = sp.getXMLReader();
            reader.setContentHandler(new SubjectsSaxHandler(em));

            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream in = loader.getResourceAsStream("META-INF/ddc21.xml");

            reader.parse(new InputSource(in));
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(SubjectEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Collection<Subject> getAll() {
        String jpql = "select distinct a from Subject a order by a.id.ddcSummary asc, a.id.ddcClass asc";
        TypedQuery<Subject> query = em.createQuery(jpql, Subject.class);
        return query.getResultList();
    }
    
    @Override
    public Collection<Subject> getByCriteria(int filtroSummary, String filtroClasse, String filtroDescription) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Subject> query = cb.createQuery(Subject.class);
        Root<Subject> root = query.from(Subject.class);
        
        List<Predicate> predicates = new ArrayList<>();

        // Se houver texto no campo description
        if(filtroDescription != null && !filtroDescription.trim().isEmpty()) {
            Path<String> path = root.get("description");
            predicates.add(cb.like(cb.lower(path), "%"+filtroDescription.toLowerCase()+"%"));
        }
        
        // Se houver texto no campo classe DDC
        if(filtroClasse != null && !filtroClasse.trim().isEmpty()) {
            Path<String> path = root.get("id").get("ddcClass");
            predicates.add(cb.like(path, filtroClasse+"%"));
        }
        
        // Filtro por summary
        if(filtroSummary != 0) { 
            Path<String> path = root.get("id").get("ddcSummary");
            predicates.add(cb.equal(path, filtroSummary));
        }

        if(!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }
        
        query.select(root);
        query.orderBy(cb.asc(root.get("id").get("ddcClass")));
        TypedQuery<Subject> q = em.createQuery(query);
        
        return q.getResultList();
    }

    @Override
    public Subject getSubject(String cdd, int summary) {
        String jpql = "select distinct a from Subject a where a.id.ddcClass = :ddc and a.id.ddcSummary = :summary";
        TypedQuery<Subject> query = em.createQuery(jpql, Subject.class);
        query.setParameter("ddc", cdd);
        query.setParameter("summary", summary);
        return query.getSingleResult();
    }

    @Override
    public Collection<Subject> getRoots() {
        String jpql = "select distinct a from Subject a where a.id.ddcSummary = 1 order by a.id asc";
        TypedQuery<Subject> query = em.createQuery(jpql, Subject.class);
        return query.getResultList();
    }
    
    @Override
    public long dataSize() {
        TypedQuery<Long> query = em.createQuery("select count(a) from Subject a", Long.class);
        return query.getSingleResult();
    }

}
