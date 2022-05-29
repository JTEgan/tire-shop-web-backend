package tireshop.entity;

import javax.persistence.*;

@Entity
@Table(name = "employee2", uniqueConstraints = {
        @UniqueConstraint(name = "id_UNIQUE", columnNames = {"id"}),
        @UniqueConstraint(name = "username_UNIQUE", columnNames = {"user_name"})

})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_name", nullable = false, length = 45)
    private String userName;

    @Column(name = "hashed_pass", nullable = false)
    private String hashedPass;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "role")
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHashedPass() {
        return hashedPass;
    }

    public void setHashedPass(String hashedPass) {
        this.hashedPass = hashedPass;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}