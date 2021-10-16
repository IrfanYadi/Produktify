package com.unila.ilkomp.produktify.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.unila.ilkomp.produktify.R;
import com.unila.ilkomp.produktify.helpers.PengaturanHelper;

public class SemuaLabel extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView semuaLabel;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton fabTambahLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PengaturanHelper.applyTema(this);
        setContentView(R.layout.activity_all_label);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        setTitle(getString(R.string.all_label_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PengaturanHelper.applyTemaToolbar((Toolbar)findViewById(R.id.toolbar),this);
        muatLabel();
    }

    private void muatLabel(){
        semuaLabel =(RecyclerView)findViewById(R.id.tampilkanSemuaLabel);
        linearLayoutManager=new LinearLayoutManager(this);
        semuaLabel.setLayoutManager(linearLayoutManager);
        fabTambahLabel =(FloatingActionButton)findViewById(R.id.fabTambahLabel);
        fabTambahLabel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tampilkan_opsi_label,menu);
        MenuItem menuItem=menu.findItem(R.id.cari);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cari:
                return true;
            case R.id.pengaturan:
                startActivity(new Intent(this, PengaturanAplikasi.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
