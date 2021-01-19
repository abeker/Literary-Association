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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Book> books;

    public boolean isMembershipApproved() {
        for (WriterRegistrationComment registrationComment : writerMembershipStatus.getWriterRegistrationComment()) {
            if(registrationComment.getMembershipApproved().equals(WriterStatus.DENIED)) {
                return false;
            }
        }

        // TODO proveriti jos ako treba da se dostavi jos materijala i da li je dostavio na vreme
        return true;
    }

}
