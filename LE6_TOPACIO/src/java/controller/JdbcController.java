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
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base64;


public class JdbcController extends HttpServlet {

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
        String user = request.getParameter("uName");
        String pass = request.getParameter("pWord");
        
        if (user.isEmpty()){
            if (pass.isEmpty()){
                  GoToErrorPage(request, response, "NO CREDENTIALS ENTERED");
            } else {
                  GoToErrorPage(request, response, "No username");
            }
        }               
        else
        {
            try {	
                if (UserExist(user)){
                    ValidateAcc(request, response, user, pass);
                } else {     
                    GoToErrorPage(request, response, "User does not exist: " + user);
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
        
    public boolean UserExist(String user) throws SQLException{
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,  ResultSet.CONCUR_READ_ONLY);
       ResultSet records = stmt.executeQuery("SELECT * FROM UserDB WHERE UNAME = '"+user+"'");
       records.last();
        return records.getRow()> 0;
    }
    
    void ValidateAcc  (HttpServletRequest request, HttpServletResponse response, String user, String pass)
            throws ServletException, IOException, SQLException{
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,  ResultSet.CONCUR_READ_ONLY);
          ResultSet records2 = stmt.executeQuery("SELECT * FROM UserDB WHERE UNAME = '"+user+"'");
                                records2.first();
                                Security decr = new Security();
                                String decrPass = decr.decrypt(records2.getString("pword"));
                                if (decrPass.equals(pass)) {
                                records2.beforeFirst();
                                
                                 HttpSession session = request.getSession();
                                
                                records2.next();
                                session.setAttribute("user1", records2.getString("UNAME"));
                                session.setAttribute("role1", records2.getString("ROLE"));

                                response.sendRedirect("success.jsp");
                                }
                                else {
                                    request.setAttribute("error", "Incorrect Password");
                                    request.getRequestDispatcher("error.jsp").include(request,response);  
                                }
    }
        public class Security {

        private byte[] key = {
            0x4A, 0x4F, 0x48, 0x78, 0x43, 0x48, 0x52, 0x49,
            0x53, 0x54, 0x49, 0x41, 0x4E, 0x46, 0x55, 0x44
        };

       
        public String decrypt(String codeDecrypt) {
            String decryptedString = null;
            try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
                final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                decryptedString = new String(cipher.doFinal(Base64.decodeBase64(codeDecrypt)));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            return decryptedString;
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
