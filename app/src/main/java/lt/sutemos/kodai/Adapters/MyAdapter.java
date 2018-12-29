package lt.sutemos.kodai.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import lt.sutemos.kodai.Models.Irasas;
import lt.sutemos.kodai.Models.KodaiViewModel;
import lt.sutemos.kodai.R;
import lt.sutemos.kodai.Services.CodeList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<Irasas> listItems;
    private KodaiViewModel codeList;

    public MyAdapter(Context context, KodaiViewModel kodaiViewModel) {
        this.context = context;
        this.codeList = kodaiViewModel;
        this.listItems = kodaiViewModel.get();
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

    public void updateListItems(){
        this.listItems = codeList.get();
        // actually refresh visible list
        notifyDataSetChanged();

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
            codeList.delete(irasas.getId());
            updateListItems();
        }
    }
}
