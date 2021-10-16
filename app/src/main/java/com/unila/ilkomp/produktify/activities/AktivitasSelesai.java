package com.unila.ilkomp.produktify.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.unila.ilkomp.produktify.R;
import com.unila.ilkomp.produktify.helpers.PengaturanHelper;

public class AktivitasSelesai extends AppCompatActivity {

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

            }
        }).setNegativeButton("Batalkan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(AktivitasSelesai.this, "Aktivitas tidak dihapus!", Toast.LENGTH_SHORT).show();
            }
        }).create().show();
    }
}
