package com.woowahan.webapp.service;

import com.woowahan.webapp.model.Question;
import com.woowahan.webapp.model.User;
import com.woowahan.webapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public void save(User writer, String title, String contents) {
        Question question = new Question(writer, title, contents);
        questionRepository.save(question);
    }

    public void update(Question question, String title, String contents) {
        question.update(title, contents);

        questionRepository.save(question);
    }

    @Transactional
    public Question findOne(long id) {
        return questionRepository.findOne(id);
    }

    @Transactional
    public List<Question> findAll() {
        return (List<Question>) questionRepository.findAll();
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }
}
