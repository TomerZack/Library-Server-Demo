package Servlets;

import Model.BookLibrary;
import Model.BookList;
import Model.Profile;
import javafx.util.Pair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "Welcome", urlPatterns = "Welcome")
public class WelcomeServlet extends HttpServlet {
    BookLibrary Library1;
    public void init() {
        Library1 = new BookLibrary("Library1");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (request.getSession(false) != null && request.getSession(false).getAttribute("profile") != null) {
        } else {
            String email = request.getParameter("email");
            if (email == null) out.println("hallo");
            String pass = request.getParameter("password");
            if (Library1.checkPass(email, pass)) {
                HttpSession session = request.getSession(true);
                session.setAttribute("profile", new Profile(Library1.getName(email), email));
            } else {
                out.println("email or password incorrect!");
                return;
            }
        }
        String input = request.getParameter("searchIn");
        if (input == null) input = "";
        ArrayList<Pair<Long,String>> list = Library1.searchBook(input);
        request.setAttribute("searchList", new BookList(list, null));
        request.getRequestDispatcher("home.jsp").forward(request,response);
        out.close();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        if (!Library1.hasStudent(email)) {
            String nameF = request.getParameter("firstname");
            String nameL = request.getParameter("lastname");
            long id = Long.parseLong(request.getParameter("id"));
            String password = request.getParameter("password");
            if (password == ""|| nameF == "" || nameL==""|| id == 0|| email == "") {
                out.println("all fields must be filled");
                return;
            }

            Library1.addStudent(nameF,nameL,email,id,password);
            HttpSession session = request.getSession(true);
            session.setAttribute("profile", new Profile(Library1.getName(email), email));
            doGet(request,response);
        }
        else
            out.println("email already taken!");
        out.close();
    }

    public void destroy() {
        Library1.closeConnection();
    }
}