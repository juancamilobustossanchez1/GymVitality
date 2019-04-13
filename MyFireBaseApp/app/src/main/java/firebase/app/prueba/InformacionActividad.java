package firebase.app.prueba;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import firebase.app.prueba.model.Actividad;
import firebase.app.prueba.model.ActividadInscripciones;

public class InformacionActividad extends AppCompatActivity implements View.OnClickListener {

    private List<Actividad> listActividades = new ArrayList<Actividad>();
    ArrayAdapter<Actividad> arrayAdapterActividades;

    EditText nombreActividad;
    EditText entrenadorEncargado;
    EditText fechaActividad;
    EditText horaInicioActividad;
    EditText horaFinActividad;

    ActividadesInscritasCliente actividadesInscritasClienteSeleccionada;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Button btnELiminarbtnELiminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_actividad);


        btnELiminarbtnELiminar = findViewById(R.id.btnEliminarActividad);
        btnELiminarbtnELiminar.setOnClickListener(this);

        nombreActividad = findViewById(R.id.txt_actividadNombre);
        entrenadorEncargado = findViewById(R.id.txt_nombreEntrenadorEncargado);
        fechaActividad = findViewById(R.id.txt_fecha);
        horaInicioActividad = findViewById(R.id.txt_horaInicioActividad);
        horaFinActividad = findViewById(R.id.txt_horaFinActividad);

        inicializarFirebase();
        listarDatos();
        valoresActividad();

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void valoresActividad() {
        //nombreActividad.setText(actividadSeleccionada.getNombreActividad());
        //entrenadorEncargado.setText(actividadSeleccionada.getNombreEntrenador());

        //nombreActividad.setText(ActividadesInscritasCliente.actividadSeleccionada.getNombreActividad());
        //Toast.makeText(InformacionActividad.this, "numero que se a encontrado"+listActividades.size(), Toast.LENGTH_SHORT).show();
    }

    private void listarDatos() {
        databaseReference.child("Actividad").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listActividades.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    Actividad actividad = objSnaptshot.getValue(Actividad.class);

                    if (actividad.getNombreActividad().equals(ActividadesInscritasCliente.actividadSeleccionada.getNombreActividad())){

                        nombreActividad.setText(actividad.getNombreActividad());
                        entrenadorEncargado.setText(actividad.getNombreEntrenador());
                        fechaActividad.setText(actividad.getFecha());
                        horaInicioActividad.setText(actividad.getHoraInicio());
                        horaFinActividad.setText(actividad.getHoraFin());
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
                Intent siguiente = new Intent(InformacionActividad.this, ActividadesInscritasCliente.class);
                startActivity(siguiente);
                break;
            }
            default:
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {


        final CharSequence[] opciones = {"Confirmar", "Cancelar"};
        final AlertDialog.Builder alertaOpciones = new AlertDialog.Builder(InformacionActividad.this);
        alertaOpciones.setTitle("¿Realmente desea salir de la actividad?");
        alertaOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (opciones[i].equals("Confirmar")) {
                    metodoEliminar();
                    Intent siguiente = new Intent(InformacionActividad.this, ActividadesInscritasCliente.class);
                    startActivity(siguiente);
                }
            }
        });
        alertaOpciones.show();


    }
    public void metodoEliminar(){
        ActividadInscripciones actividadInscripciones = new ActividadInscripciones();
        actividadInscripciones.setUid(ActividadesInscritasCliente.actividadSeleccionada.getUid());
        databaseReference.child("ActividadInscripciones").child(actividadInscripciones.getUid()).removeValue();
        Toast.makeText(this, "Saliste de la activividad", Toast.LENGTH_LONG).show();

        Intent siguiente = new Intent(InformacionActividad.this, Main2Activity.class);
        startActivity(siguiente);
    }
}
