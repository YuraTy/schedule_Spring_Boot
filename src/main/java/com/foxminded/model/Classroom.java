package com.foxminded.model;

import lombok.Builder;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "CLASSROOMS")
@SqlResultSetMapping(name = "mappingClassroom", entities = @EntityResult(entityClass = Classroom.class))
@NamedNativeQuery(name = "selectClassroomByNumber",query = "SELECT id,number_classroom FROM classrooms WHERE number_classroom = ?1",resultSetMapping = "mappingClassroom")
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
        return this.numberClassroom == (classroom.numberClassroom) ;
    }
}
