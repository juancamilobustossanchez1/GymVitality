package firebase.app.prueba;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import firebase.app.prueba.model.Actividad;
import firebase.app.prueba.model.ActividadInscripciones;

public class RegistrarActividadDatos extends AppCompatActivity implements View.OnClickListener {

    Button btnInicio;
    private EditText nombreClienteRegistrarActividad, nombreRegistroActividad;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_actividad_datos);


        btnInicio = findViewById(R.id.btnRegActividad);
        btnInicio.setOnClickListener(this);

        nombreClienteRegistrarActividad = findViewById(R.id.txt_nombrePersonaIncribir);
        nombreClienteRegistrarActividad.setText(Login.nombreInicio.getText());


        nombreRegistroActividad = findViewById(R.id.txt_nombreActividadInscribir);
        nombreRegistroActividad.setText(RegistrarActividad.actividadSeleccionada.getNombreActividad());

        inicializarFirebase();

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
                Intent siguiente = new Intent(RegistrarActividadDatos.this, RegistrarActividad.class);
                startActivity(siguiente);
                break;
            }
            default:
                break;
        }

        return true;
    }

    private void inicializarFirebase() {

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // habilitar la persistencia
        //firebaseDatabase.setPersistenceEnabled(true);

        databaseReference = firebaseDatabase.getReference();
    }


    @Override
    public void onClick(View v) {

        final CharSequence []opciones= {"Confirmar","Cancelar"};
        final AlertDialog.Builder alertaOpciones= new AlertDialog.Builder(RegistrarActividadDatos.this);
        alertaOpciones.setTitle("Confirmar inscripción");
        alertaOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (opciones[i].equals("Confirmar")){
                    metodoGuardar();
                }
                else{
                    Intent siguiente = new Intent(RegistrarActividadDatos.this, RegistrarActividad.class);
                    startActivity(siguiente);
                }
            }
        });
        alertaOpciones.show();




        /**
        Intent siguiente = new Intent(RegistrarActividadDatos.this, RegistrarActividad.class);
        startActivity(siguiente);
         **/
    }

    private void metodoGuardar() {

        ActividadInscripciones actividadInscripciones = new ActividadInscripciones();
        actividadInscripciones.setUid(UUID.randomUUID().toString());
        actividadInscripciones.setNombreActividad(nombreRegistroActividad.getText().toString().trim());
        actividadInscripciones.setNombreUsuario(nombreClienteRegistrarActividad.getText().toString().trim());
        /**
         actividad.setHoraInicio(inicio);
         actividad.setHoraFin(fin);
         **/
        databaseReference.child("ActividadInscripciones").child(actividadInscripciones.getUid()).setValue(actividadInscripciones);
        Toast.makeText(this, "Agregar", Toast.LENGTH_LONG).show();


        Intent siguiente = new Intent(RegistrarActividadDatos.this, Main2Activity.class);
        startActivity(siguiente);
    }
}
