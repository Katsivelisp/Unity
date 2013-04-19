package SocialObjects;

import java.sql.Timestamp;

/**
 *
 * @author UNITY Developers
 */
public class PostObject {

    private Long oid;
    private Long person_id;
    private Long posted_to;
    private String message;
    private String posted_type;
    private String posted_target;
    private Timestamp posted_time;

    public PostObject(Long oid, Long person_id, Long posted_to, String message, 
              String posted_type, String posted_target, Timestamp posted_time) {
        this.oid = oid; 
        this.person_id = person_id;
        this.posted_to = posted_to;
        this.message = message;
        this.posted_type = posted_type;
        this.posted_target = posted_target;
        this.posted_time = posted_time;
    }

    public PostObject() {
    }
    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getOid() {
        return oid;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }

    public String getPosted_target() {
        return posted_target;
    }

    public void setPosted_target(String posted_target) {
        this.posted_target = posted_target;
    }

    public Long getPosted_to() {
        return posted_to;
    }

    public void setPosted_to(Long posted_to) {
        this.posted_to = posted_to;
    }

    public String getPosted_type() {
        return posted_type;
    }

    public void setPosted_type(String posted_type) {
        this.posted_type = posted_type;
    }

    public Timestamp getPosted_time() {
        return posted_time;
    }
}