package firebase.app.prueba;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import firebase.app.prueba.model.ActividadInscripciones;

public class ListAdapter extends ArrayAdapter<ActividadInscripciones> {

    private  List<ActividadInscripciones> myList;
    private  Context myContext;
    private int resourceLayout;

    public ListAdapter( @NonNull Context context, int resource, List<ActividadInscripciones> objects) {
        super(context, resource, objects);
        this.myList=objects;
        this.myContext=context;
        this.resourceLayout=resource;
    }


    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view==null){
            view = LayoutInflater.from(myContext).inflate(resourceLayout,null);

        }
        ActividadInscripciones actividadInscripciones= myList.get(position);

        TextView txtNombreActividad = view.findViewById(R.id.txt_actividadNombreItemL);
        txtNombreActividad.setText(actividadInscripciones.getNombreActividad());


        return view;
    }
}