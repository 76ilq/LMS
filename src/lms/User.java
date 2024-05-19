
package lms;

public class User implements java.io.Serializable{
    private String Uname;
    private String email;
    private String userType;
    private String Password ;

    public User( String name, String email,String password, String userType) {
        this.userType = userType;
        this.Uname = name;
        this.email = email;
        this.Password = password;
    }

    // Getters and setters

    public String getuserType() {
        return userType;
    }

    public void setuserType(String userType) {
        this.userType = userType;
    }

    public String getUName() {
        return Uname;
    }

    public void setUName(String name) {
        this.Uname = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Additional methods

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    @Override
    public String toString() {
        return "User{" + "Uname=" + Uname + ", email=" + email + ", userType=" + userType + ", Password=" + Password + '}';
    }
}
