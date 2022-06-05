package id.irkwaja.gestionnaissancedeces.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import id.irkwaja.gestionnaissancedeces.R;

public class MenuStrucureTousActivity extends AppCompatActivity {
LinearLayout liajoutcertnaissance,linajoutcertdeces,linajoutparent,linajoutdeclarant;
    private Toolbar mtoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_strucure_tous);
        mtoolbar = findViewById(R.id.bar_allactivities);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("MENU STRUCTURE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        liajoutcertnaissance=findViewById(R.id.liajoutcertnaissance);
        linajoutcertdeces=findViewById(R.id.linajoutcertdeces);
        linajoutparent=findViewById(R.id.linajoutparent);
        linajoutdeclarant=findViewById(R.id.linajoutdeclarant);
        liajoutcertnaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),EnreEnfantActivity.class));
            }
        });
        linajoutcertdeces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),DefunActivity.class));
            }
        });
        linajoutparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),EnreParentActivity.class));
            }
        });
        linajoutdeclarant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),EnreDeclarantActivity.class));
            }
        });

    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(), LoginStructureActivity.class));
    }
}
