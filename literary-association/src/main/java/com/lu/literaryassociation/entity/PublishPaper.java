package com.lu.literaryassociation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishPaper extends BaseEntity {

    private String pdf_document;        // change the type if necessary

    @ManyToOne(cascade = CascadeType.ALL)
    private WriterMembershipStatus writerMembershipStatus;

}
