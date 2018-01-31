package com.woowahan.webapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "qna")
public class Question extends AbstractEntity{
    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @JsonIgnore
    @OneToMany(mappedBy = "question")
    @OrderBy("id DESC")
    private List<Answer> answers;

    @JsonProperty
    private String title;
    @JsonProperty
    private String contents;

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswers(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<>();
        }

        answers.add(answer);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getAnswersSize() {
        return answers.size();
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isSameWriter(User sessionedUser) {
        if (sessionedUser == null) {
            return false;
        }

        return writer.equals(sessionedUser);
    }

    @Override
    public String toString() {
        return "Question{" +
                super.toString() +
                ", writer=" + writer +
                ", answers=" + answers +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
