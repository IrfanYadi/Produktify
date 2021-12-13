package com.unila.ilkomp.produktify.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unila.ilkomp.produktify.R;
import com.unila.ilkomp.produktify.helpers.PengaturanHelper;
import com.unila.ilkomp.produktify.helpers.AktivitasDBHelper;
import com.unila.ilkomp.produktify.models.AktivitasSelesaiModel;

import java.util.ArrayList;

public class AktivitasSelesaiAdapter extends RecyclerView.Adapter<AktivitasSelesaiAdapter.DataSelesaiHolder>{
    private ArrayList<AktivitasSelesaiModel> aktivitasSelesaiModels;
    private Context context;
    private AktivitasDBHelper aktivitasDBHelper;

    public AktivitasSelesaiAdapter(ArrayList<AktivitasSelesaiModel> aktivitasSelesaiModels, Context context) {
        this.aktivitasSelesaiModels = aktivitasSelesaiModels;
        this.context = context;
    }

    @Override
    public DataSelesaiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_aktivitas_selesai_layout,parent,false);
        return new DataSelesaiHolder(view);
    }

    @Override
    public void onBindViewHolder(DataSelesaiHolder holder, int position) {
        aktivitasDBHelper =new AktivitasDBHelper(context);
        AktivitasSelesaiModel aktivitasSelesaiModel = aktivitasSelesaiModels.get(position);
        holder.judulAktivitas.setPaintFlags(holder.judulAktivitas.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.judulAktivitas.setText(aktivitasSelesaiModel.getJudulAktivitas());
        PengaturanHelper.applyTextColor(holder.judulAktivitas,context);
        holder.kontenAktivitas.setPaintFlags(holder.kontenAktivitas.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.kontenAktivitas.setText(aktivitasSelesaiModel.getKontenAktivitas());
        holder.labelAktivitas.setPaintFlags(holder.labelAktivitas.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.labelAktivitas.setText(aktivitasSelesaiModel.getLabelAktivitas());
        holder.aktivitasDate.setPaintFlags(holder.aktivitasDate.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.aktivitasDate.setText(aktivitasSelesaiModel.getAktivitasDate());
        holder.aktivitasTime.setPaintFlags(holder.aktivitasTime.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.aktivitasTime.setText(aktivitasSelesaiModel.getTodoTime());
    }

    @Override
    public int getItemCount() {
        return aktivitasSelesaiModels.size();
    }

    public class DataSelesaiHolder extends RecyclerView.ViewHolder {
        TextView judulAktivitas,kontenAktivitas,labelAktivitas,aktivitasDate,aktivitasTime;
        public DataSelesaiHolder(View itemView) {
            super(itemView);
            judulAktivitas=(TextView)itemView.findViewById(R.id.judul_aktivitas_selesai);
            kontenAktivitas=(TextView)itemView.findViewById(R.id.konten_aktivitas_selesai);
            labelAktivitas=(TextView)itemView.findViewById(R.id.label_aktivitas);
            aktivitasDate=(TextView)itemView.findViewById(R.id.aktivitas_date);
            aktivitasTime=(TextView)itemView.findViewById(R.id.aktivitas_time);
        }
    }

    public void filterAktivitasSelesai(ArrayList<AktivitasSelesaiModel> newAktivitasSelesaiModels){
        aktivitasSelesaiModels =new ArrayList<>();
        aktivitasSelesaiModels.addAll(newAktivitasSelesaiModels);
        notifyDataSetChanged();
    }
}
