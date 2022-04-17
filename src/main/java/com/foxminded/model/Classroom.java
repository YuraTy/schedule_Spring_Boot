package com.foxminded.model;

import lombok.Builder;

@Builder
public class Classroom {

    private int numberClassroom;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Classroom(int numberClassroom) {
        this.numberClassroom = numberClassroom;
    }

    public Classroom(int numberClassroom, int id) {
        this.numberClassroom = numberClassroom;
        this.id = id;
    }
    public Classroom() {
    }
    public int getNumberClassroom() {
        return numberClassroom;
    }

    public void setNumberClassroom(int numberClassroom) {
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
        return this.numberClassroom == (classroom.numberClassroom) ;
    }
}
