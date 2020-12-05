package deu.cse.tos;

import com.google.gson.annotations.SerializedName;

public class QnADTO {
    @SerializedName("question_name")
    private String question_name;

    @SerializedName("answer")
    private String answer;

    @SerializedName("tag")
    private String tag;

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public String getQuestion_name() { return question_name; }
    public void setQuestion_name(String question_name) { this.question_name = question_name; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    @Override
    public String toString() {
        return "QnADTO{" +
                "question_name" + question_name + '\'' +
                ", answer" + answer + '\'' +
                ", tag" + tag + '\'' +
                '}';
    }
}
