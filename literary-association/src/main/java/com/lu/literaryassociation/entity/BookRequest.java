package com.lu.literaryassociation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("SpellCheckingInspection")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest extends BaseEntity {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "book_request_writers",
            joinColumns = @JoinColumn(name = "book_request_id"),
            inverseJoinColumns = @JoinColumn(name = "writer_id")
    )
    private Set<Writer> writers = new HashSet<>();

    private String title;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Genre> genre;

    private String synopsis;

    private boolean approved = false;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="editor_comment_id", referencedColumnName="id")
    private EditorComment editorComment;

}
