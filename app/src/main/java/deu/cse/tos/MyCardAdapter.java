package deu.cse.tos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.ViewHolder> {
    private ArrayList<QnAList> itemList;
    private Context context;
    private Intent qnaIntent;
    public CardView cvItem;


    public MyCardAdapter(Context context, ArrayList<QnAList> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.cardnews, parent, false);

        this.qnaIntent = new Intent(context, ShowQnAActivity.class);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QnAList item = itemList.get(position);

        holder.question_tv.setText(item.getQuestion());
        holder.question_tv.setTag(item.getQuestion());

        holder.answer_tv.setText(item.getAnswer());
        holder.answer_tv.setTag(item.getAnswer());

        holder.cvItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context,itemList.get(position).getQuestion(),Toast.LENGTH_SHORT).show();
                qnaIntent.putExtra("question", item.getQuestion());
                qnaIntent.putExtra("answer", item.getAnswer());
                qnaIntent.putExtra("hashtag", item.getTag());
                context.startActivity(qnaIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView answer_tv;
        public TextView question_tv;
        public CardView cvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            answer_tv = (TextView) itemView.findViewById(R.id.tv_answer);
            question_tv = (TextView) itemView.findViewById(R.id.tv_question);
            cvItem = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
