package com.lu.literaryassociation.entity;

import com.lu.literaryassociation.util.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "user_entity")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String city;

    private String country;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private boolean deleted = false;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private Set<Authority> authorities;

    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth_list = new ArrayList<>();
        this.authorities.forEach(authority -> auth_list.addAll(authority.getPermissions()));
        return auth_list;
    }

    public Set<Authority> getRoles() {
        return this.authorities;
    }

}
