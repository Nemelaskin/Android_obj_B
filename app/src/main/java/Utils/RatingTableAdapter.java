package Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andforobject_b.R;

import Models.RatingTable;
import java.util.List;

public class RatingTableAdapter extends RecyclerView.Adapter<RatingTableAdapter.ViewHolder> {

    Context context;
    List<RatingTable> rating_list;

    public RatingTableAdapter(Context context, List<RatingTable> rating_list){
        this.context = context;
        this.rating_list = rating_list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(rating_list != null && rating_list.size() > 0){
            RatingTable model = rating_list.get(position);
            holder.id_tv.setText(String.valueOf(model.Position));
            holder.name_tv.setText(model.Name);
            holder.payment_tv.setText(model.Email);
        }else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return rating_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_tv;
        TextView name_tv;
        TextView payment_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_tv = itemView.findViewById(R.id.id_tv);
            name_tv = itemView.findViewById(R.id.name_tv);
            payment_tv = itemView.findViewById(R.id.payment_tv);
        }
    }
}
