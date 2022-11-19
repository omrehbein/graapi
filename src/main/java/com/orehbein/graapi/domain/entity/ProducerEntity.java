package com.orehbein.graapi.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@DynamicUpdate
@Table(name="producer" )
public class ProducerEntity implements Serializable {

    private static final long serialVersionUID = 502478597524749863L;

    @Id
    @EqualsAndHashCode.Include
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", length=70, nullable=false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "producer_movie", joinColumns = @JoinColumn(name = "producer_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<MovieEntity> movies = new HashSet<>();
}
