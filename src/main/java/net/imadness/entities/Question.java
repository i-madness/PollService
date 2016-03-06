package net.imadness.entities;

import java.util.List;

public class Question {
    private Long id;
    private String name;
    private Poll poll;
    private List<Option> options;

    public Question() {}

    public Question(String name) {
        this.name = name;
        //this.options = options;
        //this.poll = poll;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
