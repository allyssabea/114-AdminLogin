/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jeremie
 */
public class PdfGenerate extends HttpServlet {
    Connection conn;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName(config.getInitParameter("jdbcClassName"));
            //System.out.println("jdbcClassName: " + config.getInitParameter("jdbcClassName"));
            String username = config.getInitParameter("dbUserName");
            String password = config.getInitParameter("dbPassword");
            StringBuffer url = new StringBuffer(config.getInitParameter("jdbcDriverURL"))
                    .append("://")
                    .append(config.getInitParameter("dbHostName"))
                    .append(":")
                    .append(config.getInitParameter("dbPort"))
                    .append("/")
                    .append(config.getInitParameter("databaseName"));
            conn = DriverManager.getConnection(url.toString(), username, password);
        } catch (SQLException sqle) {
            System.out.println("SQLException error occured - "
                    + sqle.getMessage());
        } catch (ClassNotFoundException nfe) {
            System.out.println("ClassNotFoundException error occured - "
                    + nfe.getMessage());
        }
    }
    
   public class HeaderFooter extends PdfPageEventHelper {
    public void onEndPage(PdfWriter writer, Document document) {
        ServletContext context = getServletContext();
        java.util.Date date=java.util.Calendar.getInstance().getTime();  
        SimpleDateFormat ft = new SimpleDateFormat ("MMMM dd, yyyy (E) hh:mm:ss");
        PdfContentByte cb = writer.getDirectContent();
        Phrase header = new Phrase("LAB ACTIVITY 6");
        Phrase footer = new Phrase(ft.format(date));
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                header,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.top() + 10, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                footer,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 10, 0);
    }
}   
        
        
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        HttpSession session = request.getSession(false);
        String user = (String)session.getAttribute("user1");
        String role = (String)session.getAttribute("role1");  
        String dt = "MMMM dd, yyyy (E) hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dt);
        String date = simpleDateFormat.format(new java.util.Date());
       
          try{
          Statement stmt=conn.createStatement();
          ResultSet result=stmt.executeQuery("SELECT * FROM UserDB WHERE UNAME NOT LIKE '%"+user+"%'");
          
          Document docu = new Document();
          PdfWriter writer = PdfWriter.getInstance(docu, new FileOutputStream(user+"_"+role+".pdf"));
          writer.setPageEvent(new HeaderFooter());
          docu.open();
          
          Paragraph title = new Paragraph();
          title.setAlignment(Element.ALIGN_CENTER);
          title.add("USER RECORDS");
          docu.add(title);
          docu.add(new Paragraph(" "));
          
            PdfPTable table = new PdfPTable(3);           
            table.addCell("Username");
            table.addCell("Password");
            table.addCell("Role");
            result.next();
                
            int i = 0;
                while (i <= result.getRow()) {
                    table.addCell(result.getString("UNAME"));
                    table.addCell(result.getString("PWORD"));
                    table.addCell(result.getString("ROLE"));
                    result.next();
                    i++;
                    }
                    docu.add(table);
                    docu.add(new Paragraph(" "));
                    docu.add(new Paragraph("Reported Generated by "+user+" on "+date));
                    writer.setPageEvent(new HeaderFooter());
                    docu.close();
                    
                    if (conn != null){
                    response.sendRedirect("success.jsp");
            }
        }
catch(Exception e){
    e.printStackTrace();
    
    }

}

}
