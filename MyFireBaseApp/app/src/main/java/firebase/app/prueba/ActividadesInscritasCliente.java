package firebase.app.prueba;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import firebase.app.prueba.model.ActividadInscripciones;


public class ActividadesInscritasCliente extends AppCompatActivity {





    private List<ActividadInscripciones> listPerson = new ArrayList<ActividadInscripciones>();
    ArrayAdapter<ActividadInscripciones> arrayAdapterPersona;

    EditText nomP;
    ListView listV_personas;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    static ActividadInscripciones actividadSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades_inscritas_cliente);

        nomP = findViewById(R.id.txt_nombreClienteMisActividades);
        nomP.setText(Login.nombreInicio.getText().toString().trim());

        listV_personas = findViewById(R.id.lv_datosPersonas);

        inicializarFirebase();
        listarDatos();
        actividadProxima();

        listV_personas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actividadSeleccionada = (ActividadInscripciones) parent.getItemAtPosition(position);
                Intent siguiente = new Intent(ActividadesInscritasCliente.this, InformacionActividad.class);
                startActivity(siguiente);
            }
        });

    }

    private void actividadProxima() {



    }

    private void inicializarFirebase() {

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listarDatos() {
        databaseReference.child("ActividadInscripciones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPerson.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    ActividadInscripciones actividadInscripciones = objSnaptshot.getValue(ActividadInscripciones.class);

                    if (actividadInscripciones.getNombreUsuario().equals(nomP.getText().toString())){
                        listPerson.add(actividadInscripciones);
                        arrayAdapterPersona = new ArrayAdapter<ActividadInscripciones>(ActividadesInscritasCliente.this, android.R.layout.simple_list_item_1, listPerson);
                        listV_personas.setAdapter(arrayAdapterPersona);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_back: {
                Intent siguiente = new Intent(ActividadesInscritasCliente.this, Main2Activity.class);
                startActivity(siguiente);
                break;
            }
            default:
                break;
        }

        return true;
    }
}

