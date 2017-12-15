package edu.illinois.finalproject.SimulationFiles;

/**
 * Created by vijay on 12/13/2017.
 */

public class OnlineUser {
    private String email;
    private String uid;

    /**
     * Generic constructor for Firebase compatibility
     */
    public OnlineUser() {

    }

    /**
     * @param email The email of the online user
     * @param uid   The uid of the online user
     */
    public OnlineUser(String email, String uid) {
        this.email = email;
        this.uid = uid;
    }

    /**
     * @return This user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Required for Firebase compatibility
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return This user's uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Required for Firebase compatibility
     *
     * @param uid the new uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OnlineUser that = (OnlineUser) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return uid != null ? uid.equals(that.uid) : that.uid == null;

    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        return result;
    }
}
