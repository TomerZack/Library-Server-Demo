package Servlets;

import Model.*;
import javafx.util.Pair;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class LibraryServlet extends HttpServlet {
    BookLibrary Library1;
    public void init() {

        Library1 = new BookLibrary("Library1");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (((String)request.getParameter("flag")).equals("logout")) {
            request.getRequestDispatcher("index.html").include(request,response);
            request.getSession().invalidate();
        }
        else {
            Profile pro = (Profile)request.getSession().getAttribute("profile");
            if (((String) request.getParameter("flag")).equals("borrow")) {
                request.getRequestDispatcher("borrow.jsp").forward(request, response);
            } else {
                request.setAttribute("returned", false);
                ArrayList<Pair<Long,Integer>> map = Library1.showBorrowed(pro.getEmail());
                ArrayList<Pair<Long,String>> list = new ArrayList<>();
                ArrayList<Integer> copies = new ArrayList<>();
                for (int i = 0; i < map.size(); i++) {
                    list.add(new Pair<Long,String>(map.get(i).getKey(),Library1.getBook(map.get(i).getKey())));
                    copies.add(map.get(i).getValue());
                }
                request.setAttribute("borrowList", new BookList(list,copies));
                request.getRequestDispatcher("return.jsp").forward(request, response);
            }
        }
        out.close();
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Profile pro = (Profile)request.getSession().getAttribute("profile");
        if (((String)request.getParameter("flag")).equals("borrow")) {
            String temp = request.getParameter("isbn");
            if (pro.getEmail() == null || temp == null) return;
            long isbn = Long.parseLong(temp);
            out.println(Library1.borrowBook(isbn, pro.getEmail()));
        }
        else {
            request.setAttribute("returned", true);
            String log = "";
            Enumeration<String> en = request.getParameterNames();
            while (en.hasMoreElements()) {
                String name = en.nextElement();
                if (name.equals("flag")) continue;
                int tag = Integer.parseInt(request.getParameter(name));
                String[] arr = name.split("b");
                log += Library1.returnBook(Long.parseLong(arr[1]),tag) + "<br>\n";
            }
            request.setAttribute("log", log);
            request.getRequestDispatcher("return.jsp").forward(request,response);
        }
        out.close();
    }

    public void destroy() {
        Library1.closeConnection();
    }
}