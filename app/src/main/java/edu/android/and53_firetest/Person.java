package edu.android.and53_firetest;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Person {
    private String id;
    private String name;
    private String age;
    private String email;
    private String pw;
    private String pw2;

    public Person(){}

    public Person(String id, String name, String age, String email, String pw, String pw2) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.pw = pw;
        this.pw2 = pw2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getPw2() {
        return pw2;
    }

    public void setPw2(String pw2) {
        this.pw2 = pw2;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                ", pw2='" + pw2 + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String , Object> result = new HashMap<>();

        // TODO 회차별 저장?

        result.put("id", id);
        result.put("name", name);
        result.put("age", age);
        result.put("email", email);
        result.put("pw", pw);
        result.put("pw2", pw2);
        return result;
    }
} // end class Person
