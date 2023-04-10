package com.example.movieba.repository.Specification;

import com.example.movieba.entities.News;
import org.springframework.data.jpa.domain.Specification;

public class NewSpecification {
//    public static Specification<News> filterAll(Long mv_id){
//        return Specification.where(withMovieId(mv_id));
//    }
//
//    public static Specification<CommentSection> withMovieId(Long mv_id){
//        if (mv_id == 0 || mv_id == null) return null;
//        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("movie").get("id_movie"),mv_id);
//    }
}
