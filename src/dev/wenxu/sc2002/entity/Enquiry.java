package dev.wenxu.sc2002.entity;

/**
 * An enquiry is a question that is raised by a user. They can be answered by committee members and staff.
 */
public class Enquiry {
    /**
     * The user ID of the person who created the enquiry.
     */
    private final String askerID;
    /**
     * The question that was proposed by the enquirer.
     */
    private String question;
    /**
     * The user ID of the person who provided an answer to the enquiry, if available. Null otherwise.
     */
    private String answererID;
    /**
     * The answer of the question, if available. Null otherwise.
     */
    private String answer;

    /**
     * Creates a new enquiry.
     * @param askerID The user ID of the person creating the enquiry.
     * @param question The question that is being asked.
     */
    public Enquiry(String askerID, String question) {
        this.askerID = askerID;
        this.question = question;
    }

    /**
     * @return The user ID of the person who created the enquiry.
     */
    public String getAskerID() {
        return this.askerID;
    }

    /**
     * @return The question that was proposed by the enquirer.
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * Updates the question asked by the enquirer.
     * @param question The new question.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return The user ID of the person who answered the enquiry, if available. Null otherwise.
     */
    public String getAnswererID() {
        return this.answererID;
    }

    /**
     * @return The answer of the question, if available. Null otherwise.
     */
    public String getAnswer() {
        return this.answer;
    }

    /**
     * Updates the answer of the question.
     * @param answererID The user ID of the answerer.
     * @param answer The new answer.
     */
    public void setAnswer(String answererID, String answer) {
        this.answererID = answererID;
        this.answer = answer;
    }
}
