package net.imadness.entities;

import java.util.List;

/**
 * Класс-сущность, описывающий опрос
 */
public class Poll {

    private Long id;
    private String name;
    private String description;
    private List<Respondent> respondents;
    private List<Question> questions;

    public Poll() {}

    public Poll(String name, String description) {
        this.name = name;
        this.description = description;
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

    public List<Respondent> getRespondents() {
        return respondents;
    }

    public void setRespondents(List<Respondent> respondents) {
        this.respondents = respondents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}