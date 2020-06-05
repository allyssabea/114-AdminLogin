<%-- 
    Document   : error
    Created on : Sep 15, 2016, 11:46:08 AM
    Author     : lawrence
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Error Page</title>
        <style>
            html {
                font-family: century gothic;
            }
            .center {
                display: block;
                margin-left: auto;
                margin-right: auto;
                width: 50%;
            }

            @media screen and (max-width: 500px) {
            }
             .outer {
                display: table;
                position: absolute;
                top: 0;
                left: 0;
                height: 100%;
                width: 100%;
              }

              .middle {
                display: table-cell;
                vertical-align: middle;
              }

              .inner {
                background-color: white;
                margin: 0 auto;
                max-width: 350px;
                max-height: 500px;
                padding-bottom: 20px;
              } 
             
        </style>
    </head>
    <body>
        
        <%
                    String header=request.getServletContext().getInitParameter("header");
                    RequestDispatcher rd = request.getRequestDispatcher(header);
                    rd.include(request, response);
                    
            
            String error = (String)request.getAttribute("error"); %>
        <h2><font color='red'>
            <%=error%>
            </font></h2>
        <%	
                String footer=request.getServletContext().getInitParameter("footer");
                    rd = request.getRequestDispatcher(footer);
                    rd.include(request, response);
		%>
        
    </body>
</html>
