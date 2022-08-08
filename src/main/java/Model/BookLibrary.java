package Model;

import javafx.util.Pair;
import java.sql.*;
import java.util.ArrayList;

public class BookLibrary {
    String  urlCn="jdbc:sqlserver://localhost\\SQLEXPRESS;database=LibraryDB";
    Connection cn;
    Statement st;



    public BookLibrary(String name) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn = DriverManager.getConnection(urlCn, "root", "admin");
            st = cn.createStatement();

        }
        catch (Exception e ){
            System.out.println("Failed To Load Library. Please Restart Program. Reason:" + e.getMessage());
        }
    }

    public boolean hasStudent(String mail) {
        try {
            ResultSet set = st.executeQuery("Select Count(email) as c from Students where email='" + mail + "';");
            set.next();
            return set.getInt("c") > 0;
        }
        catch (Exception e) {
            System.out.println("exception while retrieving name of student");
        }
        return false;
    }
    public boolean checkPass(String mail, String pass) {
        try {
            ResultSet set = st.executeQuery("Select email, pass from LibraryDB.dbo.Students where email='" + mail + "';");
            if (set.next())
            return set.getString("pass").equals(pass);
            else throw new Exception();
        }
        catch (Exception e) {
            System.out.println("exception while retrieving name of student");
        }
        return false;
    }
    public ArrayList<Pair<Long,Integer>> showBorrowed(String email) {
        try {
            ArrayList<Pair<Long, Integer>> out = new ArrayList<>();
            ResultSet set = st.executeQuery("SELECT * from Borrows where email='" + email + "';");
            while (set.next()) {
                out.add(new Pair<Long, Integer>(set.getLong("isbn"), set.getInt("copytag")));
            }
            return out;
        }
        catch (Exception e) {
            System.out.println("An exception thrown while getting borrowed books");
        }
        return null;
    }
    public String getName(String mail) {
        try {
            ResultSet set = st.executeQuery("Select firstName from Students where email='" + mail + "';");
            set.next();
            return set.getString("firstName");
        }
        catch (Exception e) {
            System.out.println("exception while retrieving name of student");
        }
        return null;
    }


    public void addStudent(String nameF, String nameL, String email, long id, String password) {
        try {
            st.executeUpdate("INSERT Into Students values ('" + email + "', '" + nameF + "', '" + nameL + "', '" + password + "');");
        }
        catch (Exception e) {
            System.out.println("An exception thrown while updating files");
        }
    }

    public String borrowBook(long isbn, String email)
    {
        try {
            ResultSet book = st.executeQuery("Select * from Books where isbn=" + isbn + ";");
            if (book.next()) {
                String name = book.getString("bookName");
                int year = book.getInt("pYear");
                String author = book.getString("author");
                int copies = book.getInt("copies");
                book.close();
                ResultSet borrows = st.executeQuery("Select * from Borrows where isbn=" + isbn + ";");
                ArrayList<Integer> tags = new ArrayList<>();
                while (borrows.next()) {
                    tags.add(borrows.getInt("copytag"));
                }
                if (tags.size() >= copies) return "All copies of "+ name+ " has been taken";
                int tag = 1;
                    while (tags.contains(tag)) tag++;
                borrows.close();
                st.executeUpdate("insert into Borrows values ('"+ email+"', "+ isbn+","+ tag +");");
                return "successfully borrowed " + name + " (" + year + ") by: " + author + " tag: " + tag;
            }
        }
        catch (Exception e) {
            System.out.println("An exception thrown while updating files");
        }
        return ("This book ISBN does not exist");
    }
    public String getBook(long isbn) {
        try {
            ResultSet book = st.executeQuery("Select * from Books where isbn=" + isbn + ";");
            book.next();
            return book.getString("bookName") + " by: " + book.getString("author") + " (" + book.getInt("pYear") + "), " + book.getString("Category");
        }
        catch (Exception e) {
            return "failed to get book";
        }

    }


    public String returnBook(long isbn, int copyTag) {
        try {
            int feed = st.executeUpdate("DELETE from Borrows where isbn=" + isbn+" AND copytag=" +copyTag + ";");
            if (feed == 0) return  "This borrow does not exist";
        }
        catch ( Exception e) {
            return("An exception thrown while updating files");
        }
        return("Successfully returned " + getBook(isbn) + " copy number: " + copyTag);
    }
    public ArrayList<Pair<Long, String>> searchBook(String input) {
        ArrayList<Pair<Long, String>> out = new ArrayList<>();
        try {
            ResultSet set = st.executeQuery("SELECT * From Books Where bookName LIKE '%" + input + "%' OR author LIKE '%" + input + "%' OR Category LIKE '%" + input + "%' OR isbn LIKE '%" + input + "%' OR pYear LIKE '%" + input + "%';");
            ArrayList<Integer> copies = new ArrayList<>();
            while (set.next()) {
                out.add(new Pair<Long,String>(set.getLong("isbn"),
                        set.getString("BookName") + " By " + set.getString("author") + " (" + set.getInt("pYear") + "), " + set.getString("Category")
                + " ISBN: " + set.getLong("isbn")));
                copies.add(set.getInt("copies"));
            }
            set.close();
            for(int i = 0; i < out.size(); i++) {
                ResultSet temp = st.executeQuery("SELECT COUNT(*) AS count From Borrows where isbn=" + out.get(i).getKey());
                temp.next();
                Pair<Long,String> p = out.get(i);
                out.set(i, new Pair<>(p.getKey(), p.getValue() + " Copies available: " + (copies.get(i)- temp.getInt("count"))));
                temp.close();
            }
        }
        catch (Exception e) {
            System.out.println("An exception thrown while updating database");
        }
            return out;
    }
    public void closeConnection() {
        try {
            st.close();
            cn.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
