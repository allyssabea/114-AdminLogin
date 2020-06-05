/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
//import javax.crypto.Cipher;
//import javax.crypto.spec.SecretKeySpec;
//import org.apache.commons.codec.binary.*;

public class SignUp2 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    Connection conn;

    @Override
     public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            Class.forName(config.getInitParameter("jdbcClassName"));
            String username = config.getInitParameter("dbUserName");
            String password = config.getInitParameter("dbPassword");
            StringBuffer url = new StringBuffer(config.getInitParameter("jdbcDriverURL"))
                    .append("://")
                    .append(config.getInitParameter("dbHostName"))
                    .append(":")
                    .append(config.getInitParameter("dbPort"))
                    .append("/")
                    .append(config.getInitParameter("databaseName"));
            conn= DriverManager.getConnection(url.toString(), username, password);
        } 
        catch (SQLException sqle) {
            System.out.println("SQLException error occured - "
                    + sqle.getMessage());
        } 
        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException error occured - "
                    + ex.getMessage());
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String user2 = request.getParameter("uName2");
        String pass2 = request.getParameter("pWord2");
        String pass3 = request.getParameter("pWord3");
        String role = request.getParameter("role");
        
        if (user2.isEmpty()){
            if (pass2.isEmpty() && pass3.isEmpty()){
                  GoToErrorPage(request, response, "form not filled");
            }
            else {
                  GoToErrorPage(request, response, "No username");
            }
        }
        else if (pass2.isEmpty() && pass3.isEmpty()){
            GoToErrorPage(request, response, "Password not set");
        }
        else
        {
            try {	
                if (UserExist(user2)){
                    GoToErrorPage(request, response, "The username " + user2 + " is already taken.");
                    
                }
                else {  
                    String cp = (confirmPass(pass2, pass3));
                    if (cp.equals("ok")){
                        Security encr = new Security();
                        String encrPass = encr.encrypt(pass2); 
                        addAccount(request, response, user2, encrPass, role);
                        request.getRequestDispatcher("success2.jsp").include(request,response);
                    }
                    else {
                          GoToErrorPage(request, response, cp);
                    }
                }

            } catch (SQLException sqle){
               GoToErrorPage(request, response, sqle.getMessage());
            }
        }
    }

    void GoToErrorPage(HttpServletRequest request, HttpServletResponse response, String errorMsg)
     throws ServletException, IOException{
                request.setAttribute("error", errorMsg);
                request.getRequestDispatcher("error.jsp").include(request,response); 
     }
        
    public boolean UserExist(String user2) throws SQLException{
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,  ResultSet.CONCUR_READ_ONLY);
   
       ResultSet records = stmt.executeQuery("SELECT * FROM UserDB WHERE UNAME = '"+user2+"'");
       records.last();
        return records.getRow()> 0;
    }
    
     public String confirmPass(String pass2, String pass3){
   
       if (pass2.length() <= 20){
           if (pass2.equals(pass3)){
               return "ok";
           }
           else {
               return "passwords dont match";
           }
       } else {
           return "the password exceeds limit";
       }
    }
    
    void addAccount(HttpServletRequest request, HttpServletResponse response, String user2, String encrPass, String role)
            throws ServletException, IOException, SQLException{
         Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO UserDB (UNAME, PWORD, ROLE) VALUES ('" + user2 + "', '" + 
            encrPass + "', '" + role + "')");
    }

    public class Security {

        private byte[] key = {
            0x4A, 0x4F, 0x48, 0x78, 0x43, 0x48, 0x52, 0x49,
            0x53, 0x54, 0x49, 0x41, 0x4E, 0x46, 0x55, 0x44
        };

        public String encrypt(String strToEncrypt) {
            String encryptedString = null;
            try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            return encryptedString;
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
