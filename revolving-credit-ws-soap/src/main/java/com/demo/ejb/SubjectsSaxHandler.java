package com.demo.ejb;

/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
import com.demo.jpa.Subject;
import com.demo.jpa.SubjectPK;
import java.util.Map;
import java.util.TreeMap;
import jakarta.persistence.EntityManager;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SubjectsSaxHandler extends DefaultHandler {
    
    EntityManager em;
            
    public SubjectsSaxHandler(EntityManager em) {
        this.em = em;
    }
    
    int ddcSummary;
    String ddcClass;
    String ddcDescription = "";
    boolean entry = false;
    Map<String, Subject> ddc = new TreeMap<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if(qName.equals("entry")) {
            entry = true;
            try {
                String d = atts.getValue("summary");
                if (d != null) {
                    ddcSummary = Integer.parseInt(d); 
                }
            } catch (NumberFormatException e) {}
            
            ddcClass = atts.getValue("class");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(entry) {
            ddcDescription += new String(ch, start, length);
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("entry")) {
           entry = false;
           Subject subject = new Subject();
           SubjectPK key = new SubjectPK();
           key.setDdcClass(ddcClass);
           key.setDdcSummary(ddcSummary);
           subject.setId(key);
           subject.setDescription(ddcDescription);
           
           if(subject.getId().getDdcSummary() == 1) {
               ddc.put(subject.getId().getDdcClass(), subject);
           }
           
           if(subject.getId().getDdcSummary() == 2) {
               String group = subject.getId().getDdcClass().substring(0,1) + "00";
               int position = Integer.parseInt(subject.getId().getDdcClass().substring(1,2));
               Subject root = ddc.get(group);
               root.getSubjects().add(position, subject);
               subject.setContext(root);
           }
           
           if(subject.getId().getDdcSummary() == 3) {
               String group = subject.getId().getDdcClass().substring(0,1) + "00";
               int position = Integer.parseInt(subject.getId().getDdcClass().substring(1,2));
               Subject root = ddc.get(group);
               Subject level2 = root.getSubjects().get(position);
               int position2 = Integer.parseInt(subject.getId().getDdcClass().substring(2,3));
               level2.getSubjects().add(position2, subject);
               subject.setContext(level2);
           }
           
           ddcDescription = "";
           ddcClass = null;
           ddcSummary = 0;
        }
    }
    
    @Override
    public void endDocument() throws SAXException {
        for(Subject a : ddc.values()) {
            em.persist(a);
        }
    }
    
}

