package net.imadness.entities;

import java.util.List;

public class Respondent {
    private Long id;
    private String name;
    private String email;
    private String ipAddress;
    private List<Poll> polls;
    private List<Option> answers;

    public Respondent() {}

    public Respondent(String name, String email, String ipAddress) {
        this.name = name;
        this.email = email;
        this.ipAddress = ipAddress;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Poll> getPolls() {
        return polls;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }

    public List<Option> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Option> answers) {
        this.answers = answers;
    }
}
