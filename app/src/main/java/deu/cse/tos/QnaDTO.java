package deu.cse.tos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QnaDTO {

    @SerializedName("result")
    public List<Question> data = null;

    public List<Question> getData() {
        return data;
    }

    public void setData(List<Question> data) {
        this.data = data;
    }

    public class Question {

        @SerializedName("question_name")
        public String question_name;
        @SerializedName("answer")
        public String answer;
        @SerializedName("tag")
        public String tag;

        @Override
        public String toString() {
            return "Question{" +
                    "question_name='" + question_name + '\'' +
                    ", answer='" + answer + '\'' +
                    ", tag='" + tag + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "QnaDTO{" +
                "data=" + data +
                '}';
    }
}
