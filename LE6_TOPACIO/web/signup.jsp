<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>LabEx6</title>
    </head>
<body>
    
    <%
                    String header=request.getServletContext().getInitParameter("header");
                    RequestDispatcher rd = request.getRequestDispatcher(header);
                    rd.include(request, response);
                    
     %>

     <table border=1 align = "center">
         
         <form method="POST"
               action="SignUp2" 
              style="text-align: center;font-family: Century gothic;">
             <center>
               
               <h4 style=" font-weight: bold; color: #2D2B2B; font-family: century gothic;">
                   Sign Up</h4>

                Please fill up the form to sign up.
                <br><br>
                <label> Username <span class="required">*</span></label>
                <input type="text" name="uName2">
                <br><br> password can be 20 characters at most. <br><br>
                <label> Password <span class="required">*</span></label>
                <input type = "password" name="pWord2">
                <label> Confirm password <span class="required">*</span></label>
                <input type = "password" name="pWord3">
                <br><br>
                <labe>Role</labe>
                    <select name = "role" id="role">
                        <option value="admin">Admin</option>
                        <option value="guest">Guest</option>
                    </select>
                <br><br>
                
                <input type="SUBMIT" value="Sign up">
                
            </center>
         </form>
        <% String footer=request.getServletContext().getInitParameter("footer");
           rd = request.getRequestDispatcher(footer);
           rd.include(request, response);
        %>
</body>
</html>