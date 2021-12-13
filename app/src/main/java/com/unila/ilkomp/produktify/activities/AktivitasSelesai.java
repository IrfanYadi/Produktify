package com.unila.ilkomp.produktify.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
=======
>>>>>>> ee5d9e587fa11f5831bdd5c6b10fb4205dd785db
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
<<<<<<< HEAD
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.unila.ilkomp.produktify.R;
import com.unila.ilkomp.produktify.adapters.AktivitasSelesaiAdapter;
import com.unila.ilkomp.produktify.helpers.PengaturanHelper;
import com.unila.ilkomp.produktify.helpers.AktivitasDBHelper;
import com.unila.ilkomp.produktify.models.AktivitasSelesaiModel;

import java.util.ArrayList;

public class AktivitasSelesai extends AppCompatActivity {
    private RecyclerView aktivitasSelesai;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<AktivitasSelesaiModel> aktivitasSelesaiModels;
    private AktivitasSelesaiAdapter aktivitasSelesaiAdapter;
    private LinearLayout linearLayout;
    private AktivitasDBHelper aktivitasDBHelper;
=======
import android.widget.Toast;

import com.unila.ilkomp.produktify.R;
import com.unila.ilkomp.produktify.helpers.PengaturanHelper;

public class AktivitasSelesai extends AppCompatActivity {
>>>>>>> ee5d9e587fa11f5831bdd5c6b10fb4205dd785db

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PengaturanHelper.applyTema(this);
        setContentView(R.layout.activity_aktivitas_selesai);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PengaturanHelper.applyTemaToolbar((Toolbar)findViewById(R.id.toolbar),this);
        setTitle(getString(R.string.complete_aktivitas_activity_title));
        loadAktivitasSelesai();
    }

    private void loadAktivitasSelesai(){
<<<<<<< HEAD
        aktivitasSelesai =(RecyclerView)findViewById(R.id.aktivitas_selesai_view);
        aktivitasDBHelper =new AktivitasDBHelper(this);
        linearLayout=(LinearLayout)findViewById(R.id.aktivitas_kosong_section) ;
        if(aktivitasDBHelper.hitungAktivitasSelesai()==0){
            linearLayout.setVisibility(View.VISIBLE);
            aktivitasSelesai.setVisibility(View.GONE);
        }else{
            linearLayout.setVisibility(View.GONE);
            aktivitasSelesai.setVisibility(View.VISIBLE);
            aktivitasSelesaiModels =new ArrayList<>();
            aktivitasSelesaiModels = aktivitasDBHelper.fetchAktivitasSelesai();
            aktivitasSelesaiAdapter =new AktivitasSelesaiAdapter(aktivitasSelesaiModels,this);
        }
        linearLayoutManager=new LinearLayoutManager(this);
        aktivitasSelesai.setAdapter(aktivitasSelesaiAdapter);
        aktivitasSelesai.setLayoutManager(linearLayoutManager);
=======

>>>>>>> ee5d9e587fa11f5831bdd5c6b10fb4205dd785db
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opsi_aktivitas_selesai,menu);
        MenuItem menuItem=menu.findItem(R.id.cari);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
<<<<<<< HEAD
                newText=newText.toLowerCase();
                ArrayList<AktivitasSelesaiModel> newAktivitasSelesaiModels =new ArrayList<>();
                for(AktivitasSelesaiModel aktivitasSelesaiModel : aktivitasSelesaiModels){
                    String getJudulAktivitas = aktivitasSelesaiModel.getJudulAktivitas();
                    String getKontenAktivitas = aktivitasSelesaiModel.getKontenAktivitas();
                    String getLabelAktivitas = aktivitasSelesaiModel.getLabelAktivitas();

                    if(getJudulAktivitas.contains(newText) || getKontenAktivitas.contains(newText) || getLabelAktivitas.contains(newText)){
                        newAktivitasSelesaiModels.add(aktivitasSelesaiModel);
                    }
                }
                aktivitasSelesaiAdapter.filterAktivitasSelesai(newAktivitasSelesaiModels);
                aktivitasSelesaiAdapter.notifyDataSetChanged();
=======
>>>>>>> ee5d9e587fa11f5831bdd5c6b10fb4205dd785db
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cari:
                return true;
            case R.id.hapus_semua:
                dialogHapus();
                return true;
            case R.id.settings:
                startActivity(new Intent(this, PengaturanAplikasi.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void dialogHapus(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Hapus aktivitas");
        builder.setMessage("Yakin hapus semua aktivitas selesai?");
        builder.setPositiveButton("Hapus Semua", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
<<<<<<< HEAD
                if(aktivitasDBHelper.removeAktivitasSelesai()){
                    startActivity(new Intent(AktivitasSelesai.this, AktivitasSelesai.class));
                    Toast.makeText(AktivitasSelesai.this, "Semua aktivitas selesai berhasil dihapus!", Toast.LENGTH_SHORT).show();
                }
=======

>>>>>>> ee5d9e587fa11f5831bdd5c6b10fb4205dd785db
            }
        }).setNegativeButton("Batalkan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(AktivitasSelesai.this, "Aktivitas tidak dihapus!", Toast.LENGTH_SHORT).show();
            }
        }).create().show();
    }
}
