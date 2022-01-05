package in.linus.busmate;

public class user {
    String email_id;
    String password;
    int userid;
    String username;

    public user() {
    }

    public user(int i, String str, String str2, String str3) {
        this.userid = i;
        this.username = str;
        this.password = str2;
        this.email_id = str3;
    }

    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int i) {
        this.userid = i;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public String getEmail_id() {
        return this.email_id;
    }

    public void setEmail_id(String str) {
        this.email_id = str;
    }
}
