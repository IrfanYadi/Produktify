package com.unila.ilkomp.produktify.adapters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.unila.ilkomp.produktify.R;
import com.unila.ilkomp.produktify.activities.AktivitasSelesai;
import com.unila.ilkomp.produktify.activities.MainActivity;
import com.unila.ilkomp.produktify.helpers.PengaturanHelper;
import com.unila.ilkomp.produktify.helpers.LabelDBHelper;
import com.unila.ilkomp.produktify.helpers.AktivitasDBHelper;
import com.unila.ilkomp.produktify.models.AktivitasBerlangsungModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AktivitasBerlangsungAdapter extends RecyclerView.Adapter<AktivitasBerlangsungAdapter.DataBerlangsungHolder>{
    private ArrayList<AktivitasBerlangsungModel> aktivitasBerlangsungModels;
    private Context context;
    private String getJudulLabelString;
    private LabelDBHelper labelDBHelper;
    private AktivitasDBHelper aktivitasDBHelper;

    public AktivitasBerlangsungAdapter(ArrayList<AktivitasBerlangsungModel> aktivitasBerlangsungModels, Context context) {
        this.aktivitasBerlangsungModels = aktivitasBerlangsungModels;
        this.context = context;
    }

    @Override
    public DataBerlangsungHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_aktivitas_berlangsung_layout,parent,false);
        return new DataBerlangsungHolder(view);
    }

    @Override
    public void onBindViewHolder(DataBerlangsungHolder holder, int position) {
        aktivitasDBHelper =new AktivitasDBHelper(context);
        final AktivitasBerlangsungModel aktivitasBerlangsungModel = aktivitasBerlangsungModels.get(position);
        holder.judulAktivitas.setText(aktivitasBerlangsungModel.getJudulAktivitas());
        holder.kontenAktivitas.setText(aktivitasBerlangsungModel.getKontenAktivitas());
        holder.aktivitasDate.setText(aktivitasBerlangsungModel.getAktivitasDate());
        holder.labelAktivitas.setText(aktivitasBerlangsungModel.getLabelAktivitas());
        holder.aktivitasTime.setText(aktivitasBerlangsungModel.getAktivitasTime());
        holder.option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(context,view);
                popupMenu.getMenuInflater().inflate(R.menu.ubah_hapus_aktivitas_options,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.ubah:
                                showDialogUbah(aktivitasBerlangsungModel.getAktivitasID());
                                return true;
                            case R.id.hapus:
                                showHapusDialog(aktivitasBerlangsungModel.getAktivitasID());
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
        holder.makeSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSelesai(aktivitasBerlangsungModel.getAktivitasID());
            }
        });
    }

    private void showHapusDialog(final int labelID){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Hapus aktivitas");
        builder.setMessage("Yakin hapus?");
        builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(aktivitasDBHelper.removeAktivitas(labelID)){
                    Toast.makeText(context, "Aktivitas berhasil dihapus!", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, MainActivity.class));
                }
            }
        }).setNegativeButton("Batalkan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Aktivitas tidak dihapus!", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, MainActivity.class));
            }
        }).create().show();
    }

    @Override
    public int getItemCount() {
        return aktivitasBerlangsungModels.size();
    }

    private void showDialogUbah(final int aktivitasID){
        aktivitasDBHelper =new AktivitasDBHelper(context);
        labelDBHelper =new LabelDBHelper(context);
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view=layoutInflater.inflate(R.layout.dialog_ubah_aktivitas,null);
        builder.setView(view);
        PengaturanHelper.applyTemaTextView((TextView)view.findViewById(R.id.judul_dialog_ubah_aktivitas),context);
        final TextInputEditText judulAktivitas=(TextInputEditText)view.findViewById(R.id.judul_aktivitas);
        final TextInputEditText kontenAktivitas=(TextInputEditText)view.findViewById(R.id.konten_aktivitas);
        Spinner labelAktivitass=(Spinner)view.findViewById(R.id.label_aktivitas);
        ArrayAdapter<String> labelModelArrayAdapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item, labelDBHelper.fetchLabelStrings());
        labelModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        labelAktivitass.setAdapter(labelModelArrayAdapter);
        labelAktivitass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getJudulLabelString =adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final TextInputEditText aktivitasDate=(TextInputEditText)view.findViewById(R.id.aktivitas_date);
        final TextInputEditText aktivitasTime=(TextInputEditText)view.findViewById(R.id.aktivitas_time);

        judulAktivitas.setText(aktivitasDBHelper.fetchJudulAktivitas(aktivitasID));
        kontenAktivitas.setText(aktivitasDBHelper.fetchKontenAktivitas(aktivitasID));
        aktivitasDate.setText(aktivitasDBHelper.fetchAktivitasDate(aktivitasID));
        aktivitasTime.setText(aktivitasDBHelper.fetchAktivitasTime(aktivitasID));

        final Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        final int hour=calendar.get(Calendar.HOUR);
        final int minute=calendar.get(Calendar.MINUTE);

        aktivitasDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR,i);
                        calendar.set(Calendar.MONTH,i1);
                        calendar.set(Calendar.DAY_OF_MONTH,i2);
                        aktivitasDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        aktivitasTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Calendar newCalendar=Calendar.getInstance();
                        newCalendar.set(Calendar.HOUR,i);
                        newCalendar.set(Calendar.MINUTE,i1);
                        String timeFormat= DateFormat.getTimeInstance(DateFormat.SHORT).format(newCalendar.getTime());
                        aktivitasTime.setText(timeFormat);
                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });
        TextView batal=(TextView)view.findViewById(R.id.batal);
        TextView tambahAktivitas=(TextView)view.findViewById(R.id.tambah_aktivitas_baru);
        PengaturanHelper.applyTextColor(batal,context);
        PengaturanHelper.applyTextColor(tambahAktivitas,context);
        tambahAktivitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getJudulAktivitas=judulAktivitas.getText().toString();
                String getKontenAktivitas=kontenAktivitas.getText().toString();
                int labelAktivitasID= labelDBHelper.fetchLabelID(getJudulLabelString);
                String getAktivitasDate=aktivitasDate.getText().toString();
                String getTime=aktivitasTime.getText().toString();

                boolean isJudulEmpty=judulAktivitas.getText().toString().isEmpty();
                boolean isKontenEmpty=kontenAktivitas.getText().toString().isEmpty();
                boolean isDateEmpty=aktivitasDate.getText().toString().isEmpty();
                boolean isTimeEmpty=aktivitasTime.getText().toString().isEmpty();

                if(isJudulEmpty){
                    judulAktivitas.setError("Judul aktivitas diperlukan!");
                }else if(isKontenEmpty){
                    kontenAktivitas.setError("Isi aktivitas diperlukan!");
                }else if(isDateEmpty){
                    aktivitasDate.setError("Tanggal aktivitas diperlukan!");
                }else if(isTimeEmpty){
                    aktivitasTime.setError("Waktu aktivitas diperlukan!");
                }else if(aktivitasDBHelper.updateAktivitas(
                        new AktivitasBerlangsungModel(aktivitasID,getJudulAktivitas,getKontenAktivitas,String.valueOf(labelAktivitasID),getAktivitasDate,getTime)
                )){
                    Toast.makeText(context, R.string.aktivitas_judul_add_success_msg, Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context,MainActivity.class));
                }
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,MainActivity.class));
            }
        });
        builder.create().show();
    }

    private void showDialogSelesai(final int labelID){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Penyelesaian aktivitas");
        builder.setMessage("Selesaikan aktivitas?");
        builder.setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(aktivitasDBHelper.makeSelesai(labelID)){
                    context.startActivity(new Intent(context, AktivitasSelesai.class));
                }
            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create().show();
    }

    public class DataBerlangsungHolder extends RecyclerView.ViewHolder {
        TextView judulAktivitas,kontenAktivitas,labelAktivitas,aktivitasDate,aktivitasTime;
        ImageView option,makeSelesai;
        public DataBerlangsungHolder(View itemView) {
            super(itemView);
            judulAktivitas=(TextView)itemView.findViewById(R.id.judul_aktivitas_berlangsung);
            kontenAktivitas=(TextView)itemView.findViewById(R.id.konten_aktivitas_berlangsung);
            labelAktivitas=(TextView)itemView.findViewById(R.id.label_aktivitas);
            aktivitasDate=(TextView)itemView.findViewById(R.id.aktivitas_date);
            aktivitasTime=(TextView)itemView.findViewById(R.id.aktivitas_time);
            option=(ImageView)itemView.findViewById(R.id.option);
            makeSelesai=(ImageView)itemView.findViewById(R.id.make_completed);
        }
    }

    public void filterAktivitas(ArrayList<AktivitasBerlangsungModel> newAktivitasBerlangsungModels){
        aktivitasBerlangsungModels =new ArrayList<>();
        aktivitasBerlangsungModels.addAll(newAktivitasBerlangsungModels);
        notifyDataSetChanged();
    }
}
