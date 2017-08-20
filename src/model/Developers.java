package model;

import dao.IdentifiedTable;

import java.util.HashSet;
import java.util.Set;

public class Developers implements IdentifiedTable<Integer> {
    public static final String TABLE_NAME = "Developers";
    public static final String ID_COLUMN = "id";
    public static final String FNAME_COLUMN = "fname";
    public static final String LNAME_COLUMN = "lname";
    public static final String SALARY_COLUMN = "salary";

    private Integer id;
    private String fname;
    private String lname;
    private int salary;
    private Companies company;
    private Projects project;
    private Set<Skills> skills = new HashSet<Skills>();

    public Developers(){

    }

    public Developers(Integer id, String fname, String lname, int salary) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.salary = salary;
    }

    public Developers(Integer id, String fname, String lname, int salary, Companies company, Projects project) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.salary = salary;
        this.company = company;
        this.project = project;
    }

    public Integer getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public int getSalary() {
        return salary;
    }

    public Set<Skills> getSkills() { return skills; }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setSkills(Set<Skills> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Developers:" +
                "\nid = "+ this.id +
                ",\nfname = "+this.fname +
                ",\nlname = "+ this.lname +
                ",\nsalary = "+ this.salary+
                ",\ncompany=" + company +
                ",\nproject=" + project +
                ",\nskills=" + skills;
    }

}
