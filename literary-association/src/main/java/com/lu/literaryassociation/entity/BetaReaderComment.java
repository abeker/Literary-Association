package com.lu.literaryassociation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BetaReaderComment extends BaseEntity {

    private String commentText;

    private LocalDateTime commentDateTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Handwriting handwriting;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BetaReader betaReader;

}
