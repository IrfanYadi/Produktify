package com.unila.ilkomp.produktify.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.unila.ilkomp.produktify.R;
import com.unila.ilkomp.produktify.helpers.PengaturanHelper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{
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

    }

    @Override
    public void onClick(View view) {

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

}
