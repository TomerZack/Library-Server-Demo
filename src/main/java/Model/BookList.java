package Model;

import javafx.util.Pair;
import java.io.Serializable;
import java.util.ArrayList;

public class BookList implements Serializable {
    private ArrayList<Pair<Long,String>> list;
    private ArrayList<Integer> copies;

    public ArrayList<Pair<Long, String>> getList() {
        return list;
    }

    public void setList(ArrayList<Pair<Long, String>> list) {
        this.list = list;
    }

    public ArrayList<Integer> getCopies() {
        return copies;
    }

    public void setCopies(ArrayList<Integer> copies) {
        this.copies = copies;
    }

    public BookList() {
    }
    public BookList(ArrayList<Pair<Long,String>> arr, ArrayList<Integer> carr) {
        list = arr;
        copies = carr;
    }


}
