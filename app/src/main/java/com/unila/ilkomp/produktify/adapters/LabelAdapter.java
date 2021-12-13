package com.unila.ilkomp.produktify.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.unila.ilkomp.produktify.R;
import com.unila.ilkomp.produktify.activities.SemuaLabel;
import com.unila.ilkomp.produktify.helpers.PengaturanHelper;
import com.unila.ilkomp.produktify.helpers.LabelDBHelper;
import com.unila.ilkomp.produktify.models.LabelModel;

import java.util.ArrayList;

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.DataLabelHolder> {
    private ArrayList<LabelModel> labelModels;
    private Context context;
    private LabelDBHelper labelDBHelper;

    public LabelAdapter(ArrayList<LabelModel> labelModels, Context context) {
        this.labelModels = labelModels;
        this.context = context;
    }

    @Override
    public DataLabelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_semua_label_layout,parent,false);
        return new DataLabelHolder(view);
    }

    @Override
    public void onBindViewHolder(DataLabelHolder holder, int position) {
        final LabelModel labelModel = labelModels.get(position);
        holder.label_judul.setText(labelModel.getJudulLabel());
        labelDBHelper =new LabelDBHelper(context);
        holder.label_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(context,view);
                popupMenu.getMenuInflater().inflate(R.menu.ubah_hapus_label_option,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.ubah:
                                ubahLabel(labelModel.getLabelID());
                                return true;
                            case R.id.hapus:
                                hapusLabel(labelModel.getLabelID());
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return labelModels.size();
    }

    public class DataLabelHolder extends RecyclerView.ViewHolder{
        TextView label_judul;
        ImageView label_option;
        public DataLabelHolder(View itemView) {
            super(itemView);
            label_judul=(TextView)itemView.findViewById(R.id.label_judul);
            label_option=(ImageView)itemView.findViewById(R.id.label_option);
        }
    }

    private void hapusLabel(final int labelID){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(R.string.label_delete_dialog_title);
        builder.setMessage(R.string.judul_hapus_dialog_msg);
        builder.setPositiveButton(R.string.label_delete_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(labelDBHelper.hapusLabel(labelID)){
                    Toast.makeText(context, R.string.label_deleted_success, Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, SemuaLabel.class));
                }
            }
        }).setNegativeButton(R.string.label_delete_batal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, R.string.label_no_delete, Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, SemuaLabel.class));
            }
        }).create().show();
    }

    private void ubahLabel(final int labelID){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view=layoutInflater.inflate(R.layout.dialog_ubah_label,null);
        builder.setView(view);
        PengaturanHelper.applyTemaTextView((TextView)view.findViewById(R.id.judul_dialog_ubah_label),context);
        final TextInputEditText labelUbahJudul =(TextInputEditText)view.findViewById(R.id.ubah_label_judul);
        labelUbahJudul.setText(labelDBHelper.fetchJudulLabel(labelID));
        final TextView batal=(TextView)view.findViewById(R.id.batal);
        final TextView ubahLabelBaru=(TextView)view.findViewById(R.id.ubah_label_baru);
        PengaturanHelper.applyTextColor(batal,context);
        PengaturanHelper.applyTextColor(ubahLabelBaru,context);

        ubahLabelBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getJudulLabel= labelUbahJudul.getText().toString();
                boolean isLabelEmpty= labelUbahJudul.getText().toString().isEmpty();
                boolean labelExists= labelDBHelper.labelExists(getJudulLabel);

                if(isLabelEmpty){
                    labelUbahJudul.setError("Judul label diperlukan!");
                }else if(labelExists){
                    labelUbahJudul.setError("Judul label sudah ada!");
                }else if(labelDBHelper.saveLabel(new LabelModel(labelID,getJudulLabel))){
                    Toast.makeText(context, R.string.label_saved_success, Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, SemuaLabel.class));
                }
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, R.string.label_no_save, Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, SemuaLabel.class));
            }
        });
        builder.create().show();
    }

    public void filterLabel(ArrayList<LabelModel> newLabelModels){
        labelModels =new ArrayList<>();
        labelModels.addAll(newLabelModels);
        notifyDataSetChanged();
    }
}
