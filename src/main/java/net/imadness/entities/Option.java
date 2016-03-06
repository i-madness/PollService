package net.imadness.entities;

import java.util.List;

/**
 * Класс-сущность, описывающий вариант ответа на определённый вопрос
 */
public class Option {

    private Long id;
    private String content;
    private Boolean isRight;
    private Question question;
    private List<Respondent> respondents;

    public Option() {}

    public Option(String content, Boolean isRight/*, Question question, List<Respondent> respondents*/) {
        this.content = content;
        this.isRight = isRight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Respondent> getRespondents() {
        return respondents;
    }

    public void setRespondents(List<Respondent> respondents) {
        this.respondents = respondents;
    }
}
