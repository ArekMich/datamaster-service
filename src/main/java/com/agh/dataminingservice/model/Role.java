package com.agh.dataminingservice.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * Role class contains an id and a name field as enum.
 * Application have a fixed set of pre-defined roles like Admin or User.
 *
 * @author Arkadiusz Michalik
 */
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    /**
     * Role identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User role for authorization.
     *
     * @see RoleName
     */
    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;

    public Role() {
    }

    public Role(RoleName name) {
        this.name = name;
    }
}