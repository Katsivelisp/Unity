package SocialObjects;

import java.util.ArrayList;

/**
 *
 * @author UNITY Developers
 */
public class GroupObject {

    private Long oid;
    private String id;
    private Long person_id;
    private String type;
    private String status;
    private String picture;
    private String description;
    private ArrayList<Long> members;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Long> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Long> members) {
        this.members = members;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}