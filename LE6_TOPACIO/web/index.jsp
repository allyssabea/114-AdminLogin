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
              action="JdbcController"
              style="text-align: center;font-family: Century gothic;">
             <center>
               
               <h4 style=" font-weight: bold; color: #2D2B2B; font-family: century gothic;">
                   User Login </h4>

                Please input your credentials.
                <br><br>
                <label> Username <span class="required">*</span></label>
                <input type="text" name="uName">
                <label> Password <span class="required">*</span></label>
                <input type = "password" name="pWord">
                <br><br>
                <input type="SUBMIT" value="SUBMIT">
                <br><br>
                <a href="signup.jsp" style="color: navy;">No account yet? Sign Up here.</a>
            </center>
         </form>
        <% String footer=request.getServletContext().getInitParameter("footer");
           rd = request.getRequestDispatcher(footer);
           rd.include(request, response);
        %>
</body>
</html>