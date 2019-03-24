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
import android.widget.Button;
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
import java.util.UUID;

import firebase.app.prueba.model.Actividad;
import firebase.app.prueba.model.Persona;

public class RegistrarActividad extends AppCompatActivity implements View.OnClickListener {

    private List<Actividad> listActividades = new ArrayList<Actividad>();
    ArrayAdapter<Actividad> arrayAdapterActividades;

    //    EditText nombreActividad, entrenadorEncargado,fechaActividad, horaInicioActividad, horaFinActividad;
    EditText nombreActividad, entrenadorEncargado, fechaActividad;
    ListView listV_personas;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    static Actividad actividadSeleccionada;

    Button btnInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_actividad);

        btnInicio = findViewById(R.id.btnRegActividad);
        btnInicio.setOnClickListener(this);

        nombreActividad = findViewById(R.id.txt_actividadNombre);
        entrenadorEncargado = findViewById(R.id.txt_nombreEntrenadorEncargado);
        fechaActividad = findViewById(R.id.txt_fecha);
        /**
         horaInicioActividad = findViewById(R.id.txt_horaInicioActividad);
         horaFinActividad = findViewById(R.id.txt_horaFinActividad);
         **/

        listV_personas = findViewById(R.id.lv_listaActvidades);

        inicializarFirebase();

        listarDatos();

        listV_personas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actividadSeleccionada = (Actividad) parent.getItemAtPosition(position);
                nombreActividad.setText(actividadSeleccionada.getNombreActividad());
                entrenadorEncargado.setText(actividadSeleccionada.getNombreEntrenador());
                fechaActividad.setText("Fecha: " + actividadSeleccionada.getFecha() + " Inicio: " + actividadSeleccionada.getHoraInicio() + " Fin: " + actividadSeleccionada.getHoraFin());
                /**
                 horaInicioActividad.setText(actividadSeleccionada.getHoraInicio());
                 horaFinActividad.setText(actividadSeleccionada.getHoraFin());
                 **/
            }
        });

    }


    private void inicializarFirebase() {

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // habilitar la persistencia
        //firebaseDatabase.setPersistenceEnabled(true);

        databaseReference = firebaseDatabase.getReference();
    }

    private void listarDatos() {
        databaseReference.child("Actividad").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listActividades.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    Actividad actividad = objSnaptshot.getValue(Actividad.class);
                    listActividades.add(actividad);

                    arrayAdapterActividades = new ArrayAdapter<Actividad>(RegistrarActividad.this, android.R.layout.simple_list_item_1, listActividades);
                    listV_personas.setAdapter(arrayAdapterActividades);
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

        String actividadNom = nombreActividad.getText().toString();
        String encargado = entrenadorEncargado.getText().toString();
        String fecha = fechaActividad.getText().toString();
        /**
         * String inicio = horaInicioActividad.getText().toString();
         String fin = horaFinActividad.getText().toString();
         **/


        switch (item.getItemId()) {
            case R.id.icon_add: {
                //if (actividadNom.equals("") || encargado.equals("") || fecha.equals("") || inicio.equals("") || fin.equals("")) {
                if (actividadNom.equals("") || encargado.equals("") || fecha.equals("")) {
                    validation();
                } else {
                    Actividad actividad = new Actividad();
                    actividad.setUid(UUID.randomUUID().toString());
                    actividad.setNombreActividad(actividadNom);
                    actividad.setNombreEntrenador(encargado);
                    actividad.setFecha(fecha);
                    /**
                     actividad.setHoraInicio(inicio);
                     actividad.setHoraFin(fin);
                     **/
                    databaseReference.child("Actividad").child(actividad.getUid()).setValue(actividad);
                    Toast.makeText(this, "Agregar", Toast.LENGTH_LONG).show();
                    limpiarCajas();
                }
                break;
            }
            case R.id.icon_back: {
                Intent siguiente = new Intent(RegistrarActividad.this, Main2Activity.class);
                startActivity(siguiente);
                break;
            }
            case R.id.icon_save: {
                //if (actividadNom.equals("") || encargado.equals("") || fecha.equals("") || inicio.equals("") || fin.equals("")) {
                if (actividadNom.equals("") || encargado.equals("") || fecha.equals("")) {
                    validation();
                } else {
                    Actividad actividad = new Actividad();
                    // en este momento el id lo tiene la personas seleccionada
                    actividad.setUid(actividadSeleccionada.getUid());
                    actividad.setNombreActividad(nombreActividad.getText().toString().trim());
                    actividad.setNombreEntrenador(entrenadorEncargado.getText().toString().trim());
                    actividad.setFecha(fechaActividad.getText().toString().trim());
                    /**
                     actividad.setHoraInicio(horaInicioActividad.getText().toString().trim());
                     actividad.setHoraFin(horaFinActividad.getText().toString().trim());
                     **/
                    databaseReference.child("Actividad").child(actividad.getUid()).setValue(actividad);
                    Toast.makeText(this, "Actualizado", Toast.LENGTH_LONG).show();
                    limpiarCajas();
                    Toast.makeText(this, "Guardar", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.icon_delete: {
                //if (actividadNom.equals("") || encargado.equals("") || fecha.equals("") || inicio.equals("") || fin.equals("")) {
                if (actividadNom.equals("") || encargado.equals("") || fecha.equals("")) {
                    validation();
                } else {
                    Persona p = new Persona();
                    p.setUid(actividadSeleccionada.getUid());
                    databaseReference.child("Actividad").child(p.getUid()).removeValue();
                    Toast.makeText(this, "Eliminado", Toast.LENGTH_LONG).show();
                    limpiarCajas();
                    Toast.makeText(this, "Eliminar", Toast.LENGTH_LONG).show();
                }
                break;
            }
            default:
                break;
        }
        return true;
    }

    private void limpiarCajas() {

        nombreActividad.setText("");
        entrenadorEncargado.setText("");
        fechaActividad.setText("");
        /**
         horaInicioActividad.setText("");
         horaFinActividad.setText("");
         **/

    }

    private void validation() {
        String actividad = nombreActividad.getText().toString();
        String encargado = entrenadorEncargado.getText().toString();
        String fecha = fechaActividad.getText().toString();
        /**
         String inicio = horaInicioActividad.getText().toString();
         String fin = horaFinActividad.getText().toString();
         **/


        if (actividad.equals("")) {
            nombreActividad.setError("Required");
        } else if (encargado.equals("")) {
            entrenadorEncargado.setError("Required");
            /**} else if (inicio.equals("")) {
             horaInicioActividad.setError("Required");
             } else if (fin.equals("")) {
             horaFinActividad.setError("Required");
             **/} else if (fecha.equals("")) {
            fechaActividad.setError("Required");
        }
    }

    @Override
    public void onClick(View v) {

        String actividadNom = nombreActividad.getText().toString();
        String encargado = entrenadorEncargado.getText().toString();
        String fecha = fechaActividad.getText().toString();

        if (actividadNom.equals("") || encargado.equals("") || fecha.equals("")) {
            validation();
        } else {
            Intent siguiente = new Intent(RegistrarActividad.this, RegistrarActividadDatos.class);
            startActivity(siguiente);
        }

    }
}
