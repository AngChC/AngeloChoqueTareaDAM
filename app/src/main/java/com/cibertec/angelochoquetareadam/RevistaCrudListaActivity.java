package com.cibertec.angelochoquetareadam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.cibertec.angelochoquetareadam.adapter.RevistaAdapter;
import com.cibertec.angelochoquetareadam.api.ServiceRevistaApi;
import com.cibertec.angelochoquetareadam.entity.Revista;
import com.cibertec.angelochoquetareadam.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevistaCrudListaActivity extends AppCompatActivity {
    List<Revista> lstData = new ArrayList<Revista>();
    RevistaAdapter adaptador = null;
    ListView lstView = null;
    ServiceRevistaApi api = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_crud_lista);

        lstView = findViewById(R.id.idCrudRevistaList);

        adaptador= new RevistaAdapter(this, R.layout.activity_revista_crud_lista, lstData);
        lstView.setAdapter(adaptador);

        api = ConnectionRest.getConnection().create(ServiceRevistaApi.class);

        lista();
    }

    public void lista(){
        mensaje("LOG -> Dentro de método lista 1");

        Call<List<Revista>> call = api.listaRevista();
        call.enqueue(new Callback<List<Revista>>() {
            @Override
            public void onResponse(Call<List<Revista>> call, Response<List<Revista>> response) {
                mensaje("LOG -> Dentro de método lista 2");
                if(response.isSuccessful()){
                    mensaje("LOG -> Dentro de método lista 3");

                    List<Revista> lista = response.body();
                    mensaje("LOG -> size: " + lista.size());

                    lstData.clear();
                    lstData.addAll(lista);
                    adaptador.notifyDataSetChanged();
                }else{
                    mensaje("Error -> " + "Error en la respuesta");
                }
            }

            @Override
            public void onFailure(Call<List<Revista>> call, Throwable t) {
                mensaje("ERROR -> " + "Error en la respuesta");
            }
        });
    }

    void mensaje(String msg){
        Toast toast1 = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast1.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.idMenuRevista){
            Intent intent = new Intent(this,RevistaCrudListaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuAlumno){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}