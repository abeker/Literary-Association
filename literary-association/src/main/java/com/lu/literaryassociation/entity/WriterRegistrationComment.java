package com.lu.literaryassociation.entity;

import com.lu.literaryassociation.util.enums.WriterStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WriterRegistrationComment extends BaseEntity {

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CommitteeMember> committeeMember;

    private String comment;

    private WriterStatus membershipApproved;

    private LocalDate dateToDeliverMaterial;

    private boolean deleted;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private WriterMembershipStatus writerMembershipStatus;

}
