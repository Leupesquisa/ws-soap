package com.demo.web;

import com.demo.jpa.Subject;
import com.demo.ws.SubjectWebService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.ws.Service;
import java.net.URL;
import java.util.Collection;
import javax.xml.namespace.QName;

/**
 *
 * @author Leu A. Manuel
 */
public class SubjectClientServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
          //methods to create a dynamic Service                    
        QName serviceQName = new QName("http://com.demo.ws", "SubjectWebService");
        URL wsdlLocation =  new URL("http://localhost:8080/transaction-ws-soap/SubjectWebService?WSDL");      
        Service service = Service.create(wsdlLocation, serviceQName);
      
        SubjectWebService subjectService = service.getPort(SubjectWebService.class);
        Collection<Subject> subjects = subjectService.getRoots();

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Subjects</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<table style='border: solid red 1px'>");
            for (Subject subject : subjects) {
                out.println("<tr><td>" + subject.getId().getDdcClass() + ": " + subject.getDescription() + "</td></tr>");
                if (subject.getSubjects() != null) {
                    out.println("<tr><td><table  style='border: solid blue 1px'>");
                    for (Subject subject2 : subject.getSubjects()) {
                        out.println("<tr><td>" + subject2.getId().getDdcClass() + ": " + subject2.getDescription() + "</td></tr>");
                        if (subject.getSubjects() != null) {
                            out.println("<tr><td><table  style='border: solid green 1px'>");
                            for (Subject subject3 : subject2.getSubjects()) {
                                out.println("<tr><td>" + subject3.getId().getDdcClass() + ": " + subject3.getDescription() + "</td></tr>");
                            }
                            out.println("</table></td></tr>");
                        }
                    }
                    out.println("</table></td></tr>");
                }
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
