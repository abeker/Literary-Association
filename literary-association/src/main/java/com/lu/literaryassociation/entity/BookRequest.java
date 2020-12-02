package com.lu.literaryassociation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@SuppressWarnings("SpellCheckingInspection")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest extends BaseEntity {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Writer> writers;

    private String title;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Genre> genre;

    private String sinopsis;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private EditorComment editorComment;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bookRequest")
    private Book book;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Handwriting handwriting;

}
