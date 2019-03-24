package firebase.app.prueba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity implements View.OnClickListener {

    static EditText nombreInicio;
    static EditText passwordInicio;

    Button btnInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nombreInicio= findViewById(R.id.txt_nombrePersonaInicio);
        passwordInicio= findViewById(R.id.txt_passwordPersonaInicio);

        btnInicio=  findViewById(R.id.btnIniciar);
        btnInicio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String nombre= nombreInicio.getText().toString();
        String password= passwordInicio.getText().toString();

        switch (v.getId()) {
            case R.id.btnIniciar:
                if (nombre.equals("") || password.equals("")) {
                    validation();
                } else {
                    Intent siguiente= new Intent(Login.this,Main2Activity.class);
                    startActivity(siguiente);
                    break;
                }
        }
    }


    private void validation() {
        String nombre= nombreInicio.getText().toString();
        String password= passwordInicio.getText().toString();

        if (nombre.equals("")) {
            nombreInicio.setError("Required");
        }
        else if (password.equals("")) {
            passwordInicio.setError("Required");
        }
    }

}
