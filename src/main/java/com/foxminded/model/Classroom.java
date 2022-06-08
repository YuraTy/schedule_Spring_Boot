package com.foxminded.model;

import lombok.Builder;

import javax.persistence.*;
import java.util.Objects;

@Builder
@Entity
@Table(name = "CLASSROOMS")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "NUMBER_CLASSROOM")
    private Integer numberClassroom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Classroom(Integer numberClassroom) {
        this.numberClassroom = numberClassroom;
    }

    public Classroom(Integer numberClassroom, int id) {
        this.numberClassroom = numberClassroom;
        this.id = id;
    }
    public Classroom() {
    }
    public Integer getNumberClassroom() {
        return numberClassroom;
    }

    public void setNumberClassroom(Integer numberClassroom) {
        this.numberClassroom = numberClassroom;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Classroom))
            return false;
        Classroom classroom = (Classroom) obj;
        return Objects.equals(this.numberClassroom, classroom.numberClassroom);
    }
}
