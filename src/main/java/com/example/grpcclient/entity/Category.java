package com.example.grpcclient.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Transaction> transaction;

    @ManyToOne
    @JoinColumn(name = "group_category_id", nullable = false)
    private GroupCategory groupCategory;

    public Category(String name, GroupCategory groupCategory) {
        this.name = name;
        this.groupCategory = groupCategory;
    }
}
