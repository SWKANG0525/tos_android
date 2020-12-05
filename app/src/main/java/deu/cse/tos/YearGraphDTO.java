package deu.cse.tos;

import com.google.gson.annotations.SerializedName;

import androidx.core.app.NotificationCompat;
public class YearGraphDTO {


    @SerializedName("1")
    private float jan;

    @SerializedName("2")
    private float feb;
    // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함

    @SerializedName("3")
    private float mar;

    @SerializedName("4")
    private float apr;

    @SerializedName("5")
    private float may;

    @SerializedName("6")
    private float june;

    @SerializedName("7")
    private float july;

    @SerializedName("8")
    private float aug;
    @SerializedName("9")
    private float sep;
    @SerializedName("10")
    private float oct;
    @SerializedName("11")
    private float nov;
    @SerializedName("12")
    private float dec;

    public float getJan() {
        return jan;
    }

    public void setJan(float jan) {
        this.jan = jan;
    }

    public float getFeb() {
        return feb;
    }

    public void setFeb(float feb) {
        this.feb = feb;
    }

    public float getMar() {
        return mar;
    }

    public void setMar(float mar) {
        this.mar = mar;
    }

    public float getApr() {
        return apr;
    }

    public void setApr(float apr) {
        this.apr = apr;
    }

    public float getMay() {
        return may;
    }

    public void setMay(float may) {
        this.may = may;
    }

    public float getJune() {
        return june;
    }

    public void setJune(float june) {
        this.june = june;
    }

    public float getJuly() {
        return july;
    }

    public void setJuly(float july) {
        this.july = july;
    }

    public float getAug() {
        return aug;
    }

    public void setAug(float aug) {
        this.aug = aug;
    }

    public float getSep() {
        return sep;
    }

    public void setSep(float sep) {
        this.sep = sep;
    }

    public float getOct() {
        return oct;
    }

    public void setOct(float oct) {
        this.oct = oct;
    }

    public float getNov() {
        return nov;
    }

    public void setNov(float nov) {
        this.nov = nov;
    }

    public float getDec() {
        return dec;
    }

    public void setDec(float dec) {
        this.dec = dec;
    }

    @Override
    public String toString() {
        return "YearGraphDTO{" +
                "jan=" + jan +
                ", feb=" + feb +
                ", mar=" + mar +
                ", apr=" + apr +
                ", may=" + may +
                ", june=" + june +
                ", july=" + july +
                ", aug=" + aug +
                ", sep=" + sep +
                ", oct=" + oct +
                ", nov=" + nov +
                ", dec=" + dec +
                '}';
    }
}
