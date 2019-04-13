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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import firebase.app.prueba.model.Actividad;
import firebase.app.prueba.model.ActividadInscripciones;

public class RegistrarActividadDatos extends AppCompatActivity implements View.OnClickListener {


    Button btnInicio;
    private EditText nombreRegistroActividad;

    boolean respuesta;

    private EditText fecha, hora;
    ImageView imagen;
    TextView textView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_actividad_datos);
        //
        imagen = (ImageView) findViewById(R.id.imagenInformacionActividad);
        textView = findViewById(R.id.inicioSaludo);
        textView.setText("Hola "+Login.nombreInicio.getText().toString());
        respuesta= false;
        //
        fecha = findViewById(R.id.txt_fecha);
        hora = findViewById(R.id.txt_horaInicioActividad);

        btnInicio = findViewById(R.id.btnRegActividad);
        btnInicio.setOnClickListener(this);




        nombreRegistroActividad = findViewById(R.id.txt_nombreActividadInscribir);
        nombreRegistroActividad.setText(RegistrarActividad.actividadSeleccionada.getNombreActividad());

        inicializarFirebase();
        metodoImagen();
        metodoFecha();
        listarDatos();




    }



    private void metodoFecha() {
        fecha.setText(RegistrarActividad.actividadSeleccionada.getFecha());
        hora.setText(RegistrarActividad.actividadSeleccionada.getHoraInicio() + " - " + RegistrarActividad.actividadSeleccionada.getHoraFin());

    }

    private void metodoImagen() {
        if (nombreRegistroActividad.getText().toString().equals("Pesas")) {
            imagen.setImageResource(R.drawable.pesas);
        } else if (nombreRegistroActividad.getText().toString().equals("Spining")) {
            imagen.setImageResource(R.drawable.spinning);
        } else if (nombreRegistroActividad.getText().toString().equals("Cardio")) {
            imagen.setImageResource(R.drawable.cardio);
        } else {
            imagen.setImageResource(R.drawable.abdominales);
        }

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
        databaseReference = firebaseDatabase.getReference();
    }

    private void listarDatos() {
        databaseReference.child("ActividadInscripciones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    ActividadInscripciones actividadInscripciones = objSnaptshot.getValue(ActividadInscripciones.class);
                    if(actividadInscripciones.getNombreUsuario().equalsIgnoreCase(Login.nombreInicio.getText().toString())&& (nombreRegistroActividad.getText().toString().equalsIgnoreCase(actividadInscripciones.getNombreActividad()))){
                        respuesta=true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        if(respuesta==false) {
            final CharSequence[] opciones = {"Confirmar", "Cancelar"};
            final AlertDialog.Builder alertaOpciones = new AlertDialog.Builder(RegistrarActividadDatos.this);
            alertaOpciones.setTitle("Confirmar inscripción");
            alertaOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    if (opciones[i].equals("Confirmar")) {
                        metodoGuardar();
                    } else {
                        Intent siguiente = new Intent(RegistrarActividadDatos.this, RegistrarActividad.class);
                        startActivity(siguiente);
                    }
                }
            });
            alertaOpciones.show();

        }else {

            final CharSequence[] opciones = {"Confirmar"};
            final AlertDialog.Builder alertaOpciones = new AlertDialog.Builder(RegistrarActividadDatos.this);
            alertaOpciones.setTitle(" Ya esta registrado en esta actividad.");
            alertaOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    if (opciones[i].equals("Confirmar")) {
                        Intent siguiente = new Intent(RegistrarActividadDatos.this, RegistrarActividad.class);
                        startActivity(siguiente);
                    }
                }
            });
            alertaOpciones.show();
        }
    }

    private void metodoGuardar() {



        ActividadInscripciones actividadInscripciones = new ActividadInscripciones();
        actividadInscripciones.setUid(UUID.randomUUID().toString());
        actividadInscripciones.setNombreActividad(nombreRegistroActividad.getText().toString().trim());
        actividadInscripciones.setNombreUsuario(Login.nombreInicio.getText().toString().trim());
        /**
         actividad.setHoraInicio(inicio);
         actividad.setHoraFin(fin);
         **/
        databaseReference.child("ActividadInscripciones").child(actividadInscripciones.getUid()).setValue(actividadInscripciones);
        Toast.makeText(this, "Inscripción correcta", Toast.LENGTH_LONG).show();


        Intent siguiente = new Intent(RegistrarActividadDatos.this, Main2Activity.class);
        startActivity(siguiente);


    }
}
