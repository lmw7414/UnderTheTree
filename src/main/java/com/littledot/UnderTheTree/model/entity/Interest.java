package com.littledot.UnderTheTree.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Table(name = "interest")
@Entity
@Getter
@Setter
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String major;
    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "interests")
    private Set<UserAccount> users = new LinkedHashSet<>();

    public static Interest of(String major, String name) {
        Interest interest = new Interest();
        interest.setMajor(major);
        interest.setName(name);
        return interest;
    }
}
