package com.lu.literaryassociation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BetaReaderComment extends BaseEntity {

    private String commentText;

    private String processInstance;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Handwrite handwrite;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="beta_reader_id", referencedColumnName="id")
    private BetaReader betaReader;
}
