package com.orehbein.graapi.domain.repository.spec;

import com.orehbein.graapi.domain.entity.MovieEntity;
import com.orehbein.graapi.domain.entity.ProducerEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class MovieSpecs {
    public static Specification<MovieEntity> filterByProducer(ProducerEntity producerEntity) {
        return (root, query, builder) -> {


            if (MovieEntity.class.equals(query.getResultType())) {
                root.fetch("producers");
            }

            var predicates = new ArrayList<Predicate>();

            //predicates.add(builder.exists(root.get("producers"), producerEntity));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
