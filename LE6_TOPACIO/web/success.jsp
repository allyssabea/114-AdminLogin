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
                max-height: 400px;
                padding-bottom: 20px;
              } 
              html{
                  font-family: century gothic;
              }
        </style>
    </head>
<body>

	
		<%

                    RequestDispatcher rd;
                    response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");                            
                    response.setHeader("Pragma","no-cache");
                    response.setDateHeader("Expires", 25);
                    
                    //session = request.getSession(false);
                    
                    String username = (String)session.getAttribute("user1");
                    String role = (String)session.getAttribute("role1");   

                        if (username ==null){
                            response.sendRedirect("index.jsp");
                        } else {
                            String header=request.getServletContext().getInitParameter("header");
                            rd  = request.getRequestDispatcher(header);
                            rd.include(request, response);                    
			 %>
                         
    <div class="outer">
        <div class="middle">
            <div class="inner">            
		<h1 align="center">Welcome, <%=username%></h1>
                <br>
                <h2 align="center"> Your role is <%=role%></h2><br>
                <center>
                <table>
                <tr>
                    <td> 
                        <form method="Post" action="view.jsp">
                            <input type="hidden" name="user" value="<%=username%>">
                            <button type="submit" class="button">View Records</button> 
                        </form>
                    </td>
                    <td>
                        <form method="Post" action="PdfGenerate">
                            <input type="submit" class="button" name="generatesuccess" value="Generate PDF Report">
                        </form> 
                    </td>
                </tr>
                </table>
                    <br>
                        <form method="Post" action="Logout">
                             <button type="submit" class="button">Logout</button> 
                        </form>
                    <br>
                </center>
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
