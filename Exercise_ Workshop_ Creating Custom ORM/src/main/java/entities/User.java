package entities;

import orm.annotations.Column;
import orm.annotations.PrimaryKey;

import java.text.SimpleDateFormat;

public class User {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");

    @PrimaryKey(name = "id")
    private long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "age")
    private int age;

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return String.format("%d | %s | %d",
                getId(), getUsername(), getAge());
    }
}
