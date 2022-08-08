<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="librarypage.css">
    <script src = "librarypage.js"></script>
    <title>Academic Library - Borrow</title>
</head>
<body>
<form method="get" action="welcome" class="home"><input type="submit"  value=" "> </form>
<h1>Academic Web Library</h1>
<h2>Borrow Book</h2>
<jsp:useBean class="Model.Profile" id="profile" scope="session"/>
<div style="position: absolute; margin: 5px"> Hello <jsp:getProperty name="profile" property="name"/> </div>
<div style= "
     height: 14em;
     width: 24em;
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
    <form action="student" method="post">
    <br>
    Enter book's ISBN: <input type="number"  name="isbn" class="ISBN">
        <input type="hidden" name="flag" value="borrow">
    <input type="submit">
    </form>
</div>

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
