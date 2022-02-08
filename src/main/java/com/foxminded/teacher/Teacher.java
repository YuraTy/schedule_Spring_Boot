package com.foxminded.teacher;

public class Teacher {

    private String firstName;
    private String lastName;
    private int teacherId;

    public Teacher(String firstName, String lastName, int teacherId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.teacherId = teacherId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Teacher() {}

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
        return this.firstName.equals(teacher.firstName) && this.lastName.equals(teacher.lastName) ;
    }
}
