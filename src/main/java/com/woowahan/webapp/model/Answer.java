package com.woowahan.webapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer extends AbstractEntity{
    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_answer_question"))
    @JsonProperty
    private Question question;

    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @JsonProperty
    private User writer;

    @Lob
    @JsonProperty
    private String contents;

    public Answer() {
    }

    public Answer(Question question, User writer, String contents) {
        this.question = question;
        this.writer = writer;
        this.contents = contents;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean isSameWriter(User sessionedUser) {
        if (sessionedUser == null) {
            return false;
        }

        return writer.equals(sessionedUser);
    }

    public void update(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Answer{" +
                super.toString() +
                ", question=" + question +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                '}';
    }
}
