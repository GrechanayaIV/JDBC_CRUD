package model;

import dao.IdentifiedTable;

import java.util.HashSet;
import java.util.Set;

public class Customers implements IdentifiedTable<Integer> {
    public static final String TABLE_NAME = "Customers";
    public static final String ID_COLUMN = "id";
    public static final String TITLE_COLUMN = "title";

    private Integer id;
    private String title;
    private Set<Projects> projects = new HashSet<Projects>();

    public Customers() {

    }

    public Customers(Integer id, String title) { this.id = id; this.title = title; }

    public Integer getId() { return id; }

    public String getTitle() { return title; }

    public Set<Projects> getProjects() { return projects; }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProjects(Set<Projects> projects) { this.projects = projects; }

    @Override
    public String toString() {
        return "Customers:" +
                "\nid = " + this.id +
                ",\ntitle = " + this.title +
                ",\nprojects = " + this.projects;
    }

}
