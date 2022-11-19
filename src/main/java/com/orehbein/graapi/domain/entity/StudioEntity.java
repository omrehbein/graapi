package com.orehbein.graapi.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@DynamicUpdate
@Table(name="studio" )
public class StudioEntity implements Serializable {

    private static final long serialVersionUID = 986327524744785946L;

    @Id
    @EqualsAndHashCode.Include
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", length=70, nullable=false)
    private String name;
}
