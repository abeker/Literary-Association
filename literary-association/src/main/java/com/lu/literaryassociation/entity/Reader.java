package com.lu.literaryassociation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reader extends User {

    @OneToOne(mappedBy = "reader", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private BetaReader betaReader;



}
