package firebase.app.prueba.model;

public class ActividadInscripciones {

    private String uid;
    private String nombreActividad;
    private String nombreUsuario;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ActividadInscripciones() {
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }


    @Override
    public String toString() {
        return nombreActividad;
    }
}
