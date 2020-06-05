<%-- 
    Document   : footer
    Created on : Mar 1, 2020, 10:22:45 PM
    Author     : dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        <style>

            #footer {
              position: absolute;
              bottom: 0;
              width: 100%;
              height: 2.5rem;           
              background-color: #D1F2EB;
              font-family: Century Gothic;
            }

        </style>
        <%ServletContext context = getServletContext();%>
    </head>
    <body>
        <div id="footer">
   <center>
By: Topacio, Allyssa Bea
<br> <%=context.getAttribute("date")%></center>
   </div>
    </body>
</html>
