package dev.aldy.kafekat.Listview;

import java.util.Arrays;

public class User {
    String title;

    public User(String Title){
        title = Title;
    }

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = Arrays.toString(title);
    }

}
