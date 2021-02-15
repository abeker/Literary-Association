package com.lu.literaryassociation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginAttempts extends BaseEntity {

    @Column(unique = true)
    private String ipAddress;

    private LocalDateTime firstMistakeDateTime;

    private int attempts;
}
