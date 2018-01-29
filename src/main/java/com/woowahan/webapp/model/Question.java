package com.woowahan.webapp.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "qna")
public class Question {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User writer;

    private String title;
    private String contents;
    private String time;

    public Question() {
    }

    public Question(User writer, String title, String contents, String time) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
