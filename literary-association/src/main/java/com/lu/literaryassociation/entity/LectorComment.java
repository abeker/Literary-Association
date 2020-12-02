package com.lu.literaryassociation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@SuppressWarnings("SpellCheckingInspection")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectorComment extends BaseEntity {

    private String commentText;

    private LocalDate dateOfLastChange;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Handwriting handwriting;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Lector lector;
}
