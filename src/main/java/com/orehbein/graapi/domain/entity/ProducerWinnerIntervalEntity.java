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
@Table(name="producer_winner_interval" )
public class ProducerWinnerIntervalEntity implements Serializable {

    private static final long serialVersionUID = 275912986352474478L;

    @Id
    @EqualsAndHashCode.Include
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id", referencedColumnName = "id", nullable = false, insertable = true, updatable = true)
    private ProducerEntity producer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_movie_id", referencedColumnName = "id", nullable = false, insertable = true, updatable = true)
    private MovieEntity previousMovie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_movie_id", referencedColumnName = "id", nullable = false, insertable = true, updatable = true)
    private MovieEntity followingMovie;

    @Column(name="interval_years", nullable = false)
    private Integer intervalYears;

}
