package deu.cse.tos;

import com.google.gson.annotations.SerializedName;

import androidx.core.app.NotificationCompat;
public class TodayGraphDTO {


    @SerializedName("morning_count")
    private int morning_count;

    @SerializedName("afternoon_count")
    private int afternoon_count;
    // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함

    @SerializedName("dinner_count")
    private int dinner_count;

    @SerializedName("night_count")
    private int night_count;

    public int getMorning_count() {
        return morning_count;
    }

    public void setMorning_count(int morning_count) {
        this.morning_count = morning_count;
    }

    public int getAfternoon_count() {
        return afternoon_count;
    }

    public void setAfternoon_count(int afternoon_count) {
        this.afternoon_count = afternoon_count;
    }

    public int getDinner_count() {
        return dinner_count;
    }

    public void setDinner_count(int dinner_count) {
        this.dinner_count = dinner_count;
    }

    public int getNight_count() {
        return night_count;
    }

    public void setNight_count(int night_count) {
        this.night_count = night_count;
    }

    @Override
    public String toString() {
        return "TodayGraphDTO{" +
                "morning_count=" + morning_count +
                ", afternoon_count=" + afternoon_count +
                ", dinner_count=" + dinner_count +
                ", night_count=" + night_count +
                '}';
    }
}
