package mx.finerio.demo.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.finerio.demo.R;
import mx.finerio.demo.models.Movement;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private List<Movement> moves;

    RecyclerAdapter() {
        moves = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movement_row, parent, false);
        return new MovementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovementViewHolder moveHolder = (MovementViewHolder) holder;
        moveHolder.bind(moves.get(position));
    }

    @Override
    public int getItemCount() {
        return moves.size();
    }

    public void addItems(List<Movement> newItems) {
        int position = moves.size();
        moves.addAll(newItems);
        notifyItemInserted(position);
    }
}

class MovementViewHolder extends RecyclerView.ViewHolder {
    private TextView amount;
    private TextView date;
    private TextView description;

    MovementViewHolder(View itemView) {
        super(itemView);
        amount = itemView.findViewById(R.id.amount);
        date = itemView.findViewById(R.id.date);
        description = itemView.findViewById(R.id.description);
    }

    public void bind(Movement move) {
        amount.setText("Monto: "+ move.amount);
        date.setText("TimeStamp: "+ move.date);
        description.setText("Detalle: "+ move.description);
    }
}
