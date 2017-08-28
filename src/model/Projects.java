package model;

import dao.IdentifiedTable;

import java.util.HashSet;
import java.util.Set;

public class Projects implements IdentifiedTable<Integer> {
    public static final String TABLE_NAME = "projects";
    public static final String ID_COLUMN = "id";
    public static final String TITLE_COLUMN = "title";
    public static final String COST_COLUMN = "cost";

    private Integer id;
    private String title;
    private int cost;
    private Set<Developers> developers = new HashSet<Developers>();

    public Projects() {
    }

    public Projects(Integer id) {
        this.id = id;
    }

    public Projects(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Projects(Integer id, String title, int cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public String getTitle() {
        return title;
    }

    public Set<Developers> getDevelopers() { return developers; }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setDevelopers(Set<Developers> developers) {
        this.developers = developers;
    }

    @Override
    public String toString() {
        return "Projects:" +
                "\nid = " + this.id + "," +
                "\ntitle = " + this.title + "," +
                "\ncost = " + this.cost;
    }
}
