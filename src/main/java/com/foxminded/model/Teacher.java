package com.foxminded.model;

import lombok.Builder;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "TEACHERS")
@SqlResultSetMapping(name = "mappingTeacher", entities = @EntityResult(entityClass = Teacher.class))
public class Teacher {

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    public Teacher(String firstName, String lastName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Teacher() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Teacher))
            return false;
        Teacher teacher = (Teacher) obj;
        return this.firstName.equals(teacher.firstName) && this.lastName.equals(teacher.lastName);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
