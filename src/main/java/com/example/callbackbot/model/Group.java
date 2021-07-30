package com.example.callbackbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "groups_info")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "token")
    private String token;

    @Column(name = "version")
    private Double version;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "confirmation")
    private String confirmation;

    public Group(Long groupId, String token, String confirmation, Double version, Boolean active) {
        this.groupId = groupId;
        this.token = token;
        this.version = version;
        this.confirmation = confirmation;
        this.active = active;
    }
}
