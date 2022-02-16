package com.foxminded.model;

public class Classroom {

    private int numberClassroom;

    private int classroomId;

    public Classroom(int numberClassroom, int classroomId) {
        this.numberClassroom = numberClassroom;
        this.classroomId = classroomId;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

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
