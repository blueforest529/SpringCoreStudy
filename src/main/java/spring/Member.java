package spring;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Member {
    private Long id;
    private String email;
    private String password;
    private String name;
    private LocalDateTime registerDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getRegisterDateTime() {
        return registerDateTime;
    }

    public Member(String email, String password, String name, LocalDateTime regDateTime) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.registerDateTime = regDateTime;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!password.equals(oldPassword)) {
            throw new WrongIdPasswordException();
        }
        this.password = newPassword;
    }

    public ArrayList getAll() {
        ArrayList list = new ArrayList();
        list.add(getId());
        list.add(getEmail());
        list.add(getPassword());
        list.add(getName());

        return list;
    }

}
