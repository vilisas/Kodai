package lt.sutemos.kodai.Adapters;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import lt.sutemos.kodai.Model.Irasas;
import lt.sutemos.kodai.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<Irasas> listItems;

    public MyAdapter(Context context, List listItem) {
        this.context = context;
        this.listItems = listItem;
    }


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int i) {

        viewHolder.adresas.setText(listItems.get(i).getAdresas());
        viewHolder.kodas.setText(listItems.get(i).getKodas());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView adresas;
        public TextView kodas;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            adresas = itemView.findViewById(R.id.adresasID);
            kodas = itemView.findViewById(R.id.kodasID);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Irasas irasas = listItems.get(position);
            Toast.makeText(context, irasas.getAdresas() + " " + irasas.getKodas(),
                    Toast.LENGTH_LONG).show();

        }
    }
}
