package ru.itis.springsemwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.springsemwork.models.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String email;

    private String name;

    private Date birthdate;

    private Integer personalDiscount;

//    public static UserDto from(User user) {
//        return UserDto.builder()
//                .id(user.getId())
//                .name(user.getName())
//                .birthdate(user.getBirthdate())
//                .email(user.getEmail())
//                .personalDiscount(user.getPersonalDiscount())
//                .build();
//    }
//
//    public static List<UserDto> from(List<User> users) {
//        return users.stream().map(UserDto::from).collect(Collectors.toList());
//    }
}
