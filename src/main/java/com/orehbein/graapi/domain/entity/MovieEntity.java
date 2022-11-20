package com.orehbein.graapi.domain.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.DynamicUpdate;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@DynamicUpdate
@Table(name="movie" )
public class MovieEntity implements Serializable {

    private static final long serialVersionUID = 3328704578566463699L;

    @Id
    @EqualsAndHashCode.Include
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="production_year", nullable=false)
    private Integer productionYear;

    @Column(name="title", length=100, nullable=false)
    private String title;

    @Column(name="winner", nullable=false)
    private Boolean winner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_studios", referencedColumnName = "id", nullable = false, insertable = true, updatable = true)
    private Set<StudioEntity> studios = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_producer", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "producer_id"))
    private Set<ProducerEntity> producers = new HashSet<>();

}