package lt.sutemos.kodai.adapters;

/**
 * List View adaptas
 * Created by Vilius Bilinkevicius on 2019.01
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lt.sutemos.kodai.InfoActivity;
import lt.sutemos.kodai.MainActivity;
import lt.sutemos.kodai.R;
import lt.sutemos.kodai.database.Code;
import lt.sutemos.kodai.models.KodaiViewModel;
import lt.sutemos.kodai.utils.Util;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<Code> listItems;
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

        viewHolder.adresas.setText(listItems.get(i).getAddress());
        viewHolder.kodas.setText(listItems.get(i).getCode());
        if (listItems.get(i).getInfo().isEmpty()) {
            viewHolder.infoArrow.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.infoArrow.setVisibility(View.VISIBLE);
        }

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
        public ImageView infoArrow;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            adresas = itemView.findViewById(R.id.adresasID);
            kodas = itemView.findViewById(R.id.kodasID);
            infoArrow = itemView.findViewById(R.id.infoArrowID);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Code irasas = listItems.get(position);
            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra("action", Util.ACTION_EDIT);
            intent.putExtra("Code", irasas);
            if (MainActivity.class.isInstance(context)) {
                ((Activity) context).startActivityForResult(intent, Util.REQUEST_EDIT_ENTRY);
            }
            updateListItems();
        }
    }
}
