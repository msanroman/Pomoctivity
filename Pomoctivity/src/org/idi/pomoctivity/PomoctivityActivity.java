package org.idi.pomoctivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PomoctivityActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void crearTasca(View button) {
        
        Intent crearTasca = new Intent(this, CrearTascaActivity.class);
        startActivity(crearTasca);
    }
    
    public void llistarTasques(View button) {
        
        Intent llistarTasques = new Intent(this, TaskTabActivity.class);
        startActivity(llistarTasques);
    }
}