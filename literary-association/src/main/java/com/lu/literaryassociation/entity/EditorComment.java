package com.lu.literaryassociation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@SuppressWarnings("SpellCheckingInspection")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditorComment extends BaseEntity {

    private String commentText;

    private boolean approvedRequest;

    private LocalDateTime dateToChangeHandwrite;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Editor editor;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BookRequest bookRequest;

}
