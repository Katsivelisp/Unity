package SocialObjects;

/**
 *
 * @author UNITY Developers
 */
public class TeacherObject extends PersonObject {
    
    private String officeHours;
    private String officeNo;
    private String position;
    private String courses;
    private String researchFocus;

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }

    public String getOfficeNo() {
        return officeNo;
    }

    public void setOfficeNo(String officeNo) {
        this.officeNo = officeNo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getResearchFocus() {
        return researchFocus;
    }

    public void setResearchFocus(String researchFocus) {
        this.researchFocus = researchFocus;
    }
}