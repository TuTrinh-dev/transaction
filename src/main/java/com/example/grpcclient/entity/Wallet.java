package com.example.grpcclient.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "wallet")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "unit", nullable = false)
    private String currency;

    @Column(name = "current_balance", nullable = false)
    private Double currentBalance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wallet")
    private Set<Transaction> transactions;

    @OneToMany(mappedBy = "wallet")
    private Set<Event> events;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "wallet_group_category",
            joinColumns = @JoinColumn(name = "wallet_id"),
            inverseJoinColumns = @JoinColumn(name = "group_category_id")
    )
    private Set<GroupCategory> groupCategories;
}
