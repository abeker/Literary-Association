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

    private String comment;

    private WriterStatus membershipApproved;

    private boolean deleted;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private WriterMembershipStatus writerMembershipStatus;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="committee_member_id", referencedColumnName="id")
    private CommitteeMember committeeMember;

}
