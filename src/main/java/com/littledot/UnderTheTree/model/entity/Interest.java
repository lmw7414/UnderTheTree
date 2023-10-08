package com.littledot.UnderTheTree.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private int count;  // 해당 관심사를 선택한 인원에 대한 수

    public static Interest of(String major, String name) {
        Interest interest = new Interest();
        interest.setMajor(major);
        interest.setName(name);
        interest.setCount(0);
        return interest;
    }
}
