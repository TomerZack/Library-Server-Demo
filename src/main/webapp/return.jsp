<%@ page import="java.util.ArrayList" %>
<%@ page import="javafx.util.Pair" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="librarypage.css">
    <script src = "librarypage.js"></script>
    <title>Academic Library - Return</title>
</head>
<body>
<jsp:useBean class="Model.Profile" id="profile" scope="session"/>
<div style="position: absolute; margin: 5px"> Hello <jsp:getProperty name="profile" property="name"/> </div>
<form method="get" action="welcome" class="home" ><input type="submit" value=" "> </form>
<h1>Academic Web Library</h1>
<h2>Return Book</h2>
<div style= "
    height: 14em;
    width: 34em;
    text-align: center;
    margin-top: 20vh;
    margin-left: auto;
    margin-right: auto;
    line-height: 1.4em;
    background-color: yellowgreen;
    font-family: 'Arial Black';
    border-style: groove;
    color: white;
    border-color: darkgray;" >
    <form action="student" method="post" class="inputbox" style="width: 33em; padding: 1em"> <input type="hidden" name="flag" value="return">
        <% if ((boolean)request.getAttribute("returned")) {%>
            <jsp:useBean class="java.lang.String" id="log" scope="request"/> <%=log%>
        <%}
        else { %>
            <jsp:useBean class="Model.BookList" id="borrowList" scope="request"/>
        <%ArrayList<Pair<Long,String>> arr = borrowList.getList();
            for (int i = 0; i < arr.size(); i++) { %>
            <input type="checkbox" name="<%=i%>b<%=arr.get(i).getKey()%>" value="<%=borrowList.getCopies().get(i)%>"> <%=arr.get(i).getValue()%> <br>
        <%}}%>
        <input type="submit" value="Return">
    </form> </div>
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