package com.unila.ilkomp.produktify.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.unila.ilkomp.produktify.R;
import com.unila.ilkomp.produktify.adapters.AktivitasBerlangsungAdapter;
import com.unila.ilkomp.produktify.helpers.PengaturanHelper;
import com.unila.ilkomp.produktify.helpers.LabelDBHelper;
import com.unila.ilkomp.produktify.helpers.AktivitasDBHelper;
import com.unila.ilkomp.produktify.models.AktivitasBerlangsungModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{
    private RecyclerView aktivitasBerlangsung;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<AktivitasBerlangsungModel> aktivitasBerlangsungModels;
    private AktivitasBerlangsungAdapter aktivitasBerlangsungAdapter;
    private FloatingActionButton tambahAktivitasBaru;
    private LabelDBHelper labelDBHelper;
    private String getStringJudulLabel;
    private AktivitasDBHelper aktivitasDBHelper;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PengaturanHelper.applyTema(this);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        PengaturanHelper.applyTemaToolbar((Toolbar)findViewById(R.id.toolbar),this);
        setTitle(getString(R.string.judul_aplikasi));
        showDrawerLayout();
        navigationMenuInit();
        loadAktivitasBerlangsung();
    }

    private void loadAktivitasBerlangsung(){
        aktivitasBerlangsung =(RecyclerView)findViewById(R.id.aktivitas_berlangsung_view);
        linearLayout=(LinearLayout)findViewById(R.id.aktivitas_berlangsung_kosong_section);
        labelDBHelper =new LabelDBHelper(this);
        aktivitasDBHelper =new AktivitasDBHelper(this);

        if(aktivitasDBHelper.hitungAktivitas()==0){
            linearLayout.setVisibility(View.VISIBLE);
            aktivitasBerlangsung.setVisibility(View.GONE);
        }else{
            aktivitasBerlangsungModels =new ArrayList<>();
            aktivitasBerlangsungModels = aktivitasDBHelper.fetchSemuaAktivitas();
            aktivitasBerlangsungAdapter =new AktivitasBerlangsungAdapter(aktivitasBerlangsungModels,this);
        }
        linearLayoutManager=new LinearLayoutManager(this);
        aktivitasBerlangsung.setAdapter(aktivitasBerlangsungAdapter);
        aktivitasBerlangsung.setLayoutManager(linearLayoutManager);
        tambahAktivitasBaru =(FloatingActionButton)findViewById(R.id.fabTambahAktivitas);
        tambahAktivitasBaru.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabTambahAktivitas:
                if(labelDBHelper.countLabel()==0){
                    showDialog();
                }else{
                    showAktivitasBaruDialog();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void showDrawerLayout(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,(Toolbar) findViewById(R.id.toolbar) , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void navigationMenuInit(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opsi_aktivitas_berlangsung,menu);
        MenuItem menuItem=menu.findItem(R.id.cari);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText=newText.toLowerCase();
                ArrayList<AktivitasBerlangsungModel> newAktivitasBerlangsungModels =new ArrayList<>();
                for(AktivitasBerlangsungModel aktivitasBerlangsungModel : aktivitasBerlangsungModels){
                    String getJudulAktivitas = aktivitasBerlangsungModel.getJudulAktivitas().toLowerCase();
                    String getKontenAktivitas = aktivitasBerlangsungModel.getKontenAktivitas().toLowerCase();
                    String getLabelAktivitas = aktivitasBerlangsungModel.getLabelAktivitas().toLowerCase();

                    if(getJudulAktivitas.contains(newText) || getKontenAktivitas.contains(newText) || getLabelAktivitas.contains(newText)){
                        newAktivitasBerlangsungModels.add(aktivitasBerlangsungModel);
                    }
                }
                aktivitasBerlangsungAdapter.filterAktivitas(newAktivitasBerlangsungModels);
                aktivitasBerlangsungAdapter.notifyDataSetChanged();
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
            case R.id.semua_label:
                startActivity(new Intent(this, SemuaLabel.class));
                return true;
            case R.id.selesai:
                startActivity(new Intent(this, AktivitasSelesai.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(this, PengaturanAplikasi.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.aktivitas_berlangsung) {
            startActivity(new Intent(this,MainActivity.class));
        } else if (id == R.id.aktivitas_selesai) {
            startActivity(new Intent(this, AktivitasSelesai.class));
        } else if (id == R.id.label) {
            startActivity(new Intent(this, SemuaLabel.class));
        } else if (id == R.id.settings) {
            startActivity(new Intent(this, PengaturanAplikasi.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(R.string.label_create_dialog_title_text);
        builder.setMessage(R.string.label_di_db_kosong_text);
        builder.setPositiveButton(R.string.create_new_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(MainActivity.this, SemuaLabel.class));
            }
        }).setNegativeButton(R.string.label_edit_batal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create().show();
    }

    private void showAktivitasBaruDialog(){
        final Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        final int hour=calendar.get(Calendar.HOUR);
        final int minute=calendar.get(Calendar.MINUTE);

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view=layoutInflater.inflate(R.layout.dialog_tambah_aktivitas_baru,null);
        builder.setView(view);
        PengaturanHelper.applyTemaTextView((TextView)view.findViewById(R.id.judul_dialog_tambah_aktivitas),this);
        final TextInputEditText judulAktivitas =(TextInputEditText)view.findViewById(R.id.judul_aktivitas);
        final TextInputEditText kontenAktivitas =(TextInputEditText)view.findViewById(R.id.konten_aktivitas);
        Spinner labelAktivitass=(Spinner)view.findViewById(R.id.label_aktivitas);
        ArrayAdapter<String> labelModelArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, labelDBHelper.fetchLabelStrings());
        labelModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        labelAktivitass.setAdapter(labelModelArrayAdapter);
        labelAktivitass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getStringJudulLabel =adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final TextInputEditText aktivitasDate =(TextInputEditText)view.findViewById(R.id.aktivitas_date);
        final TextInputEditText aktivitasTime =(TextInputEditText)view.findViewById(R.id.aktivitas_time);

        aktivitasDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                TimePickerDialog timePickerDialog=new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.set(Calendar.HOUR_OF_DAY,i);
                        calendar.set(Calendar.MINUTE,i1);
                        String timeFormat=DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
                        aktivitasTime.setText(timeFormat);
                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });
        TextView batal=(TextView)view.findViewById(R.id.batal);
        TextView tambahAktivitas=(TextView)view.findViewById(R.id.tambah_aktivitas_baru);
        PengaturanHelper.applyTextColor(batal,this);
        PengaturanHelper.applyTextColor(tambahAktivitas,this);
        tambahAktivitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getJudulAktivitas = judulAktivitas.getText().toString();
                String getKontenAktivitas = kontenAktivitas.getText().toString();
                int aktivitasLabelID = labelDBHelper.fetchLabelID(getStringJudulLabel);
                String getAktivitasDate = aktivitasDate.getText().toString();
                String getTime= aktivitasTime.getText().toString();

                boolean isJudulEmpty = judulAktivitas.getText().toString().isEmpty();
                boolean isKontenEmpty = kontenAktivitas.getText().toString().isEmpty();
                boolean isDateEmpty= aktivitasDate.getText().toString().isEmpty();
                boolean isTimeEmpty= aktivitasTime.getText().toString().isEmpty();

                if(isJudulEmpty){
                    judulAktivitas.setError("Judul aktivitas diperlukan!");
                }else if(isKontenEmpty){
                    kontenAktivitas.setError("Isi aktivitas diperlukan!");
                }else if(isDateEmpty){
                    aktivitasDate.setError("Tanggal aktivitas diperlukan!");
                }else if(isTimeEmpty){
                    aktivitasTime.setError("Waktu aktivitas diperlukan!");
                }else if(aktivitasDBHelper.tambahAktivitasBaru(
                        new AktivitasBerlangsungModel(getJudulAktivitas, getKontenAktivitas,String.valueOf(aktivitasLabelID), getAktivitasDate,getTime)
                )){
                    Toast.makeText(MainActivity.this, R.string.aktivitas_judul_add_success_msg, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                }
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });
        builder.create().show();
    }
}
