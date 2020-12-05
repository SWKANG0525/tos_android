package deu.cse.tos;

import java.io.Serializable;

public class QnAList implements Serializable {
    private String question;
    private String answer;
    private String tag;

    public QnAList() { }

    public QnAList(String question, String answer, String tag) {
        this.question = question;
        this.answer = answer;
        this.tag = tag;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setTag(String tag) { this.tag = tag; }

    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getTag() { return tag; }
}
