package com.foxminded.classroom;

import org.springframework.stereotype.Component;

@Component
public class Classroom {

    private int numberClassroom;

    public Classroom(int numberClassroom) {
        this.numberClassroom = numberClassroom;
    }

    public Classroom() {
    }
    public int getNumberClassroom() {
        return numberClassroom;
    }

    public void setNumberClassroom(int numberClassroom) {
        this.numberClassroom = numberClassroom;
    }

}
