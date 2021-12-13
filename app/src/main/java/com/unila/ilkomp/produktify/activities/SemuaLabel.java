package com.unila.ilkomp.produktify.activities;

<<<<<<< HEAD
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
=======
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
>>>>>>> ee5d9e587fa11f5831bdd5c6b10fb4205dd785db
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
<<<<<<< HEAD
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unila.ilkomp.produktify.R;
import com.unila.ilkomp.produktify.adapters.LabelAdapter;
import com.unila.ilkomp.produktify.helpers.PengaturanHelper;
import com.unila.ilkomp.produktify.helpers.LabelDBHelper;
import com.unila.ilkomp.produktify.models.LabelModel;

import java.util.ArrayList;

public class SemuaLabel extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView semuaLabel;
    private ArrayList<LabelModel> labelModels;
    private LabelAdapter labelAdapter;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton fabTambahLabel;
    private LabelDBHelper labelDBHelper;
    private LinearLayout linearLayout;
=======
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.unila.ilkomp.produktify.R;
import com.unila.ilkomp.produktify.helpers.PengaturanHelper;

public class SemuaLabel extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView semuaLabel;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton fabTambahLabel;
>>>>>>> ee5d9e587fa11f5831bdd5c6b10fb4205dd785db

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
<<<<<<< HEAD
        linearLayout=(LinearLayout)findViewById(R.id.label_kosong);
        labelDBHelper =new LabelDBHelper(this);
        if(labelDBHelper.countLabel()==0){
            linearLayout.setVisibility(View.VISIBLE);
            semuaLabel.setVisibility(View.GONE);
        }else{
            semuaLabel.setVisibility(View.VISIBLE);
            labelModels =new ArrayList<>();
            labelModels = labelDBHelper.fetchLabel();
            labelAdapter =new LabelAdapter(labelModels,this);
            linearLayout.setVisibility(View.GONE);
        }
        linearLayoutManager=new LinearLayoutManager(this);
        semuaLabel.setAdapter(labelAdapter);
=======
        linearLayoutManager=new LinearLayoutManager(this);
>>>>>>> ee5d9e587fa11f5831bdd5c6b10fb4205dd785db
        semuaLabel.setLayoutManager(linearLayoutManager);
        fabTambahLabel =(FloatingActionButton)findViewById(R.id.fabTambahLabel);
        fabTambahLabel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
<<<<<<< HEAD
        switch (view.getId()){
            case R.id.fabTambahLabel:
                tampilkanDialogLabelBaru();
                break;
        }
=======

>>>>>>> ee5d9e587fa11f5831bdd5c6b10fb4205dd785db
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
<<<<<<< HEAD
                newText=newText.toLowerCase();
                ArrayList<LabelModel> newLabelModels =new ArrayList<>();
                for(LabelModel labelModel : labelModels){
                    String judulLabel= labelModel.getJudulLabel().toLowerCase();
                    if(judulLabel.contains(newText)){
                        newLabelModels.add(labelModel);
                    }
                }
                labelAdapter.filterLabel(newLabelModels);
                labelAdapter.notifyDataSetChanged();
=======
>>>>>>> ee5d9e587fa11f5831bdd5c6b10fb4205dd785db
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
<<<<<<< HEAD

    private void tampilkanDialogLabelBaru(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view=layoutInflater.inflate(R.layout.tambah_dialog_label_baru,null);
        builder.setView(view);
        PengaturanHelper.applyTemaTextView((TextView)view.findViewById(R.id.judul_dialog_tambah_label),this);
        final TextInputEditText judulLabel=(TextInputEditText)view.findViewById(R.id.label_judul);
        final TextView batal=(TextView)view.findViewById(R.id.batal);
        final TextView tambahLabelBaru=(TextView)view.findViewById(R.id.tambah_label_baru);
        PengaturanHelper.applyTextColor(batal,this);
        PengaturanHelper.applyTextColor(tambahLabelBaru,this);

        tambahLabelBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getJudulLabel=judulLabel.getText().toString();
                boolean isLabelEmpty=judulLabel.getText().toString().isEmpty();
                boolean labelAda= labelDBHelper.labelExists(getJudulLabel);

                if(isLabelEmpty){
                    judulLabel.setError("Judul label dibutuhkan!");
                }else if(labelAda){
                    judulLabel.setError("Judul label sudah ada!");
                }else {
                    if(labelDBHelper.tambahLabelBaru(new LabelModel(getJudulLabel))){
                        Toast.makeText(SemuaLabel.this, R.string.label_judul_add_success_msg, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SemuaLabel.this, SemuaLabel.class));
                    }
                }
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SemuaLabel.this, SemuaLabel.class));
            }
        });
        builder.create().show();
    }
=======
>>>>>>> ee5d9e587fa11f5831bdd5c6b10fb4205dd785db
}
