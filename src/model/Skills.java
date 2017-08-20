package model;

import dao.IdentifiedTable;

public class Skills implements IdentifiedTable<Integer> {
    public static final String TABLE_NAME = "Skills";
    public static final String ID_COLUMN = "id";
    public static final String TITLE_COLUMN = "title";

    private Integer id;
    private String title;

    public Skills() {
    }

    public Skills(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Skills:" +
                "\nid = " + this.id +
                ",\ntitle = " +
                this.title;
    }

}
