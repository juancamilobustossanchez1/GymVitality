package firebase.app.prueba;

import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;

public class MyFarebaseApp extends android.app.Application{




    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
