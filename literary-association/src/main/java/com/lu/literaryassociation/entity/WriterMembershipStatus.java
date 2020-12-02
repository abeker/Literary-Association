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
public class WriterMembershipStatus extends BaseEntity {

    private int tryCounter;

    @OneToMany(mappedBy = "writerMembershipStatus", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PublishPaper> publishPapers;

    @OneToOne(mappedBy = "writerMembershipStatus", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Writer writer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<WriterRegistrationComment> writerRegistrationComment;

}
