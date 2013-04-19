package Beans;

import SocialObjects.StaffObject;
import SocialObjects.StudentObject;
import SocialObjects.TeacherObject;

/**
 *
 * @author UNITY Developers
 */
public class PersonBean {
    private String viewer = null;
    private String password;
    private StudentObject student = null;
    private TeacherObject teacher = null;
    private StaffObject staff = null;
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getViewer() {
        return viewer;
    }

    public void setViewer(String viewer) {
        this.viewer = viewer;
    }

    public StaffObject getStaff() {
        return staff;
    }

    public void setStaff(StaffObject staff) {
        this.staff = staff;
    }

    public StudentObject getStudent() {
        return student;
    }

    public void setStudent(StudentObject student) {
        this.student = student;
    }

    public TeacherObject getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherObject teacher) {
        this.teacher = teacher;
    }   
}