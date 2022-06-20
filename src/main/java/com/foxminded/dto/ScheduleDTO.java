package com.foxminded.dto;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

@Builder
public class ScheduleDTO {

    @NotNull(message = "Group cannot be empty")
    private GroupDTO groupDTO;

    @NotNull(message = "Teacher cannot be empty")
    private TeacherDTO teacherDTO;

    @NotNull(message = "Course cannot be empty")
    private CourseDTO courseDTO;

    @NotNull(message = "Classroom cannot be empty")
    private ClassroomDTO classroomDTO;

    @NotNull(message = "Start date cannot be empty")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lessonStartTime;

    @NotNull(message = "End date cannot be empty")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lessonEndTime;

    private int scheduleId;

    private static final String DATA_PATTERN = "yyyy-MM-dd HH:mm:ss";


    public ScheduleDTO(GroupDTO groupDTO, TeacherDTO teacherDTO, CourseDTO courseDTO, ClassroomDTO classroomDTO, LocalDateTime lessonStartTime, LocalDateTime lessonEndTime, int scheduleId) {
        this.groupDTO = groupDTO;
        this.teacherDTO = teacherDTO;
        this.courseDTO = courseDTO;
        this.classroomDTO = classroomDTO;
        this.lessonStartTime = lessonStartTime;
        this.lessonEndTime = lessonEndTime;
        this.scheduleId = scheduleId;
    }

    public ScheduleDTO(GroupDTO groupDTO, TeacherDTO teacherDTO, CourseDTO courseDTO, ClassroomDTO classroomDTO, String lessonStartTime, String lessonEndTime, int scheduleId) {
        this.groupDTO = groupDTO;
        this.teacherDTO = teacherDTO;
        this.courseDTO = courseDTO;
        this.classroomDTO = classroomDTO;
        this.lessonStartTime = LocalDateTime.parse(lessonStartTime, DateTimeFormatter.ofPattern(DATA_PATTERN));
        this.lessonEndTime = LocalDateTime.parse(lessonEndTime, DateTimeFormatter.ofPattern(DATA_PATTERN));
        this.scheduleId = scheduleId;
    }

    public ScheduleDTO() {
    }

    public ScheduleDTO(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public ScheduleDTO(GroupDTO groupDTO, TeacherDTO teacherDTO, CourseDTO courseDTO, ClassroomDTO classroomDTO, String lessonStartTime, String lessonEndTime) {
        this.groupDTO = groupDTO;
        this.teacherDTO = teacherDTO;
        this.courseDTO = courseDTO;
        this.classroomDTO = classroomDTO;
        this.lessonStartTime = LocalDateTime.parse(lessonStartTime, DateTimeFormatter.ofPattern(DATA_PATTERN));
        this.lessonEndTime = LocalDateTime.parse(lessonEndTime, DateTimeFormatter.ofPattern(DATA_PATTERN));
    }

    public GroupDTO getGroupDTO() {
        return groupDTO;
    }

    public void setGroupDTO(GroupDTO groupDTO) {
        this.groupDTO = groupDTO;
    }

    public TeacherDTO getTeacherDTO() {
        return teacherDTO;
    }

    public void setTeacherDTO(TeacherDTO teacherDTO) {
        this.teacherDTO = teacherDTO;
    }

    public CourseDTO getCourseDTO() {
        return courseDTO;
    }

    public void setCourseDTO(CourseDTO courseDTO) {
        this.courseDTO = courseDTO;
    }

    public ClassroomDTO getClassroomDTO() {
        return classroomDTO;
    }

    public void setClassroomDTO(ClassroomDTO classroomDTO) {
        this.classroomDTO = classroomDTO;
    }

    public LocalDateTime getLessonStartTime() {
        return lessonStartTime;
    }

    public void setLessonStartTime(LocalDateTime lessonStartTime) {
        this.lessonStartTime = lessonStartTime;
    }

    public LocalDateTime getLessonEndTime() {
        return lessonEndTime;
    }

    public void setLessonEndTime(LocalDateTime lessonEndTime) {
        this.lessonEndTime = lessonEndTime;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public String toString() {
        return new StringJoiner(" ")
                .add("schedule id =")
                .add(Integer.toString(scheduleId))
                .add("group id =")
                .add(Integer.toString(groupDTO.getId()))
                .add("teacher id =")
                .add(Integer.toString(teacherDTO.getId()))
                .add("course id =")
                .add(Integer.toString(courseDTO.getId()))
                .add("classroom id =")
                .add(Integer.toString(classroomDTO.getId()))
                .add("start time =")
                .add(lessonStartTime.toString())
                .add("end time =")
                .add(lessonEndTime.toString())
                .toString();
    }
}
