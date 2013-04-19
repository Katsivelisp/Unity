package SocialObjects;

/**
 *
 * @author UNITY Developers
 */
public class StaffObject extends PersonObject {
    private Long staffOid;
    private String position;
   

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getStaffOid() {
        return staffOid;
    }

    public void setStaffOid(Long staffOid) {
        this.staffOid = staffOid;
    }
}