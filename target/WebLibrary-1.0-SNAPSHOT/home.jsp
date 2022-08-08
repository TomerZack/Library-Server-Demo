
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2/17/2022
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="librarypage.css">
    <script src = "librarypage.js"></script>
    <title>Academic Library</title>
    <style>
        #search {
            font-family: monospace;
            position: relative;
            Top: 50px;
            padding: 1em;
        }
    </style>
</head>
<body>
    <form method="get" action="welcome" class="home" ><input type="submit" value=" "> </form>
        <h1>Academic Web Library</h1>
        <h2>Search</h2>
    <form method="get" action="welcome" id="search">
        <input type="submit" value="Search" style="font-family: 'Arial Black'">
        <input type="search" name="searchIn" style="font-size: 1em">
    </form>
    <jsp:useBean class="Model.Profile" id="profile" scope="session"/>
    <jsp:useBean class="Model.BookList" id="searchList" scope="request"/>
    <div style="position: absolute; margin: 5px"> Hello <jsp:getProperty name="profile" property="name"/> </div>
    <form action="student" method="post" style="padding: 4em">
        <% for (int i = 0; i < searchList.getList().size(); i++) { %>
            <input type="radio" name="isbn" value="<%=searchList.getList().get(i).getKey()%>"> <%=searchList.getList().get(i).getValue()%> <br>
        <%}%>
        <input type="submit" value="Borrow">
        <input type="hidden" name="flag" value="borrow">
     </form>
<footer>
    <form action="student" method="get" style="width: 6em; height: 0; display: inline;">
        <input type="submit" value="Borrow Book"> <input type="hidden" name="flag" value="borrow"></form> |
    <form action="student" method="get" style="width: 6em; height: 0; display: inline;">
        <input type="submit" value="Return Book"> <input type="hidden" name="flag" value="return"></form> |
    <form action="student" method="get" style="width: 6em; height: 0; display: inline;">
        <input type="submit" value="Logout"> <input type="hidden" name="flag" value="logout"></form>
</footer>
</body>
</html>
