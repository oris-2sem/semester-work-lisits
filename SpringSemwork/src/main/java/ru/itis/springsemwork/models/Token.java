package ru.itis.springsemwork.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Token {

    @Id
    @Column(name = "user_id")
    private Long userId;

    private String accessToken;

    private String refreshToken;

}
