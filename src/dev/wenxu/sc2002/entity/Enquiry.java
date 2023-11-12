package dev.wenxu.sc2002.entity;

public class Enquiry {
    private final String askerID;
    private String question;
    private String answererID;
    private String answer;

    public Enquiry(String askerID, String question) {
        this.askerID = askerID;
        this.question = question;
    }

    public String getAskerID() {
        return this.askerID;
    }
    public String getQuestion() {
        return this.question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswererID() {
        return this.answererID;
    }
    public String getAnswer() {
        return this.answer;
    }
    public void setAnswer(String answererID, String answer) {
        this.answererID = answererID;
        this.answer = answer;
    }
}
