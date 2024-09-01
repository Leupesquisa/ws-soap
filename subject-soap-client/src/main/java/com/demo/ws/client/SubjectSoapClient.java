package com.demo.ws.client;

/**
 *
 * @author Leu A. Manuel
 */
import com.demo.ws.SubjectWebService;
import com.demo.jpa.Subject;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

public class SubjectSoapClient {

    public static void main(String[] args) throws MalformedURLException {
        //methods to create a dynamic Service                    
        QName serviceQName = new QName("http://com.demo.ws", "SubjectWebService");
        URL wsdlLocation =  new URL("http://localhost:8080/transaction-ws-soap/SubjectWebService?WSDL");
        
        Service service = Service.create(wsdlLocation, serviceQName);
       
        SubjectWebService subjectService = service.getPort(SubjectWebService.class);
        Collection<Subject> subjects = subjectService.getRoots();
        for (Subject subject : subjects) {
            System.out.println(subject.getId().getDdcClass() + ": " + subject.getDescription());
        }
    }
}