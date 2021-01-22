package com.lu.literaryassociation.entity;

import com.lu.literaryassociation.util.enums.WriterStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommitteeVote extends BaseEntity{

     private String comment;

     private String processInstanceId;

     private  int counter;

     @Enumerated(EnumType.STRING)
     private WriterStatus writerStatus;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="committeeMember_id", referencedColumnName="id")
    private CommitteeMember committeeMember;

}
