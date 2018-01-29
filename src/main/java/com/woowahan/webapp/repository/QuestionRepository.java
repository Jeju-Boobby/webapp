package com.woowahan.webapp.repository;

import com.woowahan.webapp.model.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long>{
}
