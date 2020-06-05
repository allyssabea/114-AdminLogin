<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
         <style>
/*        .center {
                display: block;
                margin-left: auto;
                margin-right: auto;
                width: 50%;
            }*/

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
              td {
                  padding: 5px;
              }
        </style>
        5
    </head>
    <center>
    <%  
        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");                            

        String header=request.getServletContext().getInitParameter("header");
        RequestDispatcher rd = request.getRequestDispatcher(header);
        rd.include(request, response);     
                  
                    session = request.getSession(false);

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
                        Connection conn = DriverManager.getConnection(url.toString(), username, password);
                        Statement stmt = conn.createStatement();%>
    
        
            <%
                String user = (String)session.getAttribute("user1");
                
                  if (user.equals("null")){
                      request.setAttribute("error", "Records Can't be Viewed"); 
                      request.getRequestDispatcher("error.jsp").include(request,response);  
                  } else {
                    %> 
    <h1>All Records</h1>
        <table border="1" style="border-collapse: collapse">
                        
                    <tr style="font-weight: bold";>
                        <td align="center">USERNAME</td>
                        <td align="center">PASSWORD</td>
                        <td align="center">ROLE</td>
                    </tr>
                    
                    <tr>
                        <%
                        ResultSet rs = stmt.executeQuery("SELECT * FROM UserDB WHERE UNAME NOT LIKE '%"+user +"%'");
                        while (rs.next()) {
                        %>
                        <td><%=rs.getString("UNAME")%></td>
                        <td><%=rs.getString("PWORD")%></td>
                        <td><%=rs.getString("ROLE")%></td>
                    </tr>
            <%
                }
            %>
        </table>
        <br>
        <table>
            <tr>
                <td> 
                    <form method="Post" action="PdfGenerate">
                    <input type="submit" class="button" name="generatesuccess" value="Generate PDF Report">
                </form>  
                </td>
                <td>
                    <form action="success.jsp">
                    <button onclick="goBack()">back</button> 
                    
                    <script>
                        function goBack() {
                               window.history.back();
                                      }
                    </form>
                </td>
            </tr>    
        </table>
                                       
                                       <%}%>                   
</body>

</html>

