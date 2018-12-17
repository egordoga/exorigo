package ex.entity;

import java.io.Serializable;

public class User implements Serializable {

    private Long id;
    private String name;
    private String lastName;
    private String pass;

    public User() {
    }

    public User(String name, String lastName, String pass) {
        this.name = name;
        this.lastName = lastName;
        this.pass = pass;
    }

    public User(Long id, String name, String lastName, String pass) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " " + lastName;
    }
}
