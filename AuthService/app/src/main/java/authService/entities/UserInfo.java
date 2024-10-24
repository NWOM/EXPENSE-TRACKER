package authService.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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
