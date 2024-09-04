package authService.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {
    @Id
    @Column(name="user_id")
    private String userId;
    private String username;
    private String password;
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name= "users_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRoles> roles=new HashSet<>();
}
