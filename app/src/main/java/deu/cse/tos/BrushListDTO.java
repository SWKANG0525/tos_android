package deu.cse.tos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrushListDTO {
    @SerializedName("block")
    private List<BrushDTO> data = null;

    public List<BrushDTO> getData() {
        return data;
    }

    public void setData(List<BrushDTO> data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return "BrushListDTO{" +
                "data=" + data +
                '}';
    }

    public class BrushDTO {
        public BrushDTO() {}

        @SerializedName("hash_key")
        public String hash_key;

        @SerializedName("item_name")
        public String item_name;

        @SerializedName("buy_date")
        public String buy_date;

        @SerializedName("recommend_date")
        public String recommend_date;

        @SerializedName("using_date")
        public int using_date;

        @SerializedName("remain_recommend_date")
        public int remain_recommend_date;

        @Override
        public String toString() {
            return "BrushList{" +
                    "hash_key='" + hash_key + '\'' +
                    ", item_name='" + item_name + '\'' +
                    ", buy_date='" + buy_date + '\'' +
                    ", recommend_date='" + recommend_date + '\'' +
                    ", using_date='" + using_date + '\'' +
                    ", remain_recommend_date='" + remain_recommend_date + '\'' +
                    '}';
        }
    }
}
