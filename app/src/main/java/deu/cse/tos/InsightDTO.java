package deu.cse.tos;

import com.google.gson.annotations.SerializedName;

import androidx.core.app.NotificationCompat;
public class InsightDTO {


    @SerializedName("tooth_score")
    private int tooth_score;

    @SerializedName("month_tooth_number")
    private int month_tooth_number;
    // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함


    public int getTooth_score() {
        return tooth_score;
    }

    public void setTooth_score(int tooth_score) {
        this.tooth_score = tooth_score;
    }

    public int getMonth_tooth_number() {
        return month_tooth_number;
    }

    public void setMonth_tooth_number(int month_tooth_number) {
        this.month_tooth_number = month_tooth_number;
    }

    @Override
    public String toString() {
        return "InsightDTO{" +
                "tooth_score=" + tooth_score +
                ", month_tooth_number=" + month_tooth_number +
                '}';
    }
}
