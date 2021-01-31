package com.lu.literaryassociation.entity;

import com.lu.literaryassociation.util.enums.WriterStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@SuppressWarnings("SpellCheckingInspection")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Writer extends User {

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Genre> genres;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private WriterMembershipStatus writerMembershipStatus;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="membership_id", referencedColumnName="id")
    private Membership membership;

    @ManyToMany(mappedBy = "writers")
    private Set<BookRequest> bookRequest;

}
