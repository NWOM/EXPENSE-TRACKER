package authService.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import authService.entities.UserInfo;

import lombok.*;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoDTO extends UserInfo {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private Long phoneNumber;
    private String email;
}
