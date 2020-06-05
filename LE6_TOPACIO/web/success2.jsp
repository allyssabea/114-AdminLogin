<%-- 
    Document   : displayresult
    Created on : Sep 15, 2016, 11:45:17 AM
    Author     : lawrence
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>success</title>
        <style>
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
              html{
                  font-family: century gothic;
              }
        </style>
    </head>
<body>

	
		<%
                    String header=request.getServletContext().getInitParameter("header");
                    RequestDispatcher rd = request.getRequestDispatcher(header);
                    rd.include(request, response);
                    
                  { %>
   <div class="outer">
        <div class="middle">
            <div class="inner">            
				
                <h2 align="center"> you have successfully signed up. </h2><br>
                
                <a href="index.jsp" style="color: navy;">Log in now.</a>
        
            </div>
        </div>
    </div>
  	<%	}
                String footer=request.getServletContext().getInitParameter("footer");
                    rd = request.getRequestDispatcher(footer);
                    rd.include(request, response);
		%>
	
	</table>
</body>
</html>
