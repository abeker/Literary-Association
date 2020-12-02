package com.lu.literaryassociation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BetaReader extends BaseEntity {

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Genre> genres;

    private int penaltyPoint = 0;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reader_id", referencedColumnName = "id")
    private Reader reader;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "betaReader")
    private Set<BetaReaderComment> betaReaderComments;

}
