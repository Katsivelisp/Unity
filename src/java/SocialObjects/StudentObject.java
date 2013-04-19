package SocialObjects;

/**
 *
 * @author UNITY Developers
 */
public class StudentObject extends PersonObject {
    
    private String studentCode;
    private Integer admissionYear;
    private String favCourse;
    private String hateCourse;
    private String graduatedFrom;

    public Integer getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(Integer admissionYear) {
        this.admissionYear = admissionYear;
    }

    public String getFavCourse() {
        return favCourse;
    }

    public void setFavCourse(String favCourse) {
        this.favCourse = favCourse;
    }

    public String getGraduatedFrom() {
        return graduatedFrom;
    }

    public void setGraduatedFrom(String graduatedFrom) {
        this.graduatedFrom = graduatedFrom;
    }

    public String getHateCourse() {
        return hateCourse;
    }

    public void setHateCourse(String hateCourse) {
        this.hateCourse = hateCourse;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }
}