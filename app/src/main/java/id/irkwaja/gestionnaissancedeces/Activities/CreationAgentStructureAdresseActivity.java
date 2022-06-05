package id.irkwaja.gestionnaissancedeces.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import id.irkwaja.gestionnaissancedeces.MainActivity;
import id.irkwaja.gestionnaissancedeces.R;

public class CreationAgentStructureAdresseActivity extends AppCompatActivity {
LinearLayout linajoutactedece,linajoutstructure,liajoutagent,linajoutactenaissance;
    private Toolbar mtoolbar;
    String fonctionag;
    public static  String delaicertificat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_agent_structure_adresse);
        mtoolbar = findViewById(R.id.bar_allactivities);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("MENU ETAT-CIVIL");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fonctionag=MainActivity.fonctiongent;
        liajoutagent=findViewById(R.id.liajoutagent);
        linajoutactedece=findViewById(R.id.linajoutactedece);
        linajoutstructure=findViewById(R.id.linajoutstructure);
        linajoutactenaissance=findViewById(R.id.linajoutactenaissance);

        linajoutactenaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             showAlertDialog();

            }
        });
        liajoutagent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fonctionag.equals("informaticien")){
                startActivity(new Intent(getApplicationContext(),EnreAgentActivity.class));
                finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"pas d'acces",Toast.LENGTH_LONG).show();
                }
            }
        });
        linajoutactedece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fonctionag.equals("informaticien")|| fonctionag.equals("Charger Deces")){
                startActivity(new Intent(getApplicationContext(),EnreActeDecesActivity.class));
                finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"pas d'acces",Toast.LENGTH_LONG).show();
                }
            }
        });
        linajoutstructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fonctionag.equals("informaticien")){
                startActivity(new Intent(getApplicationContext(),EnreStructureSanitaireActivity.class));
                finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"pas d'acces",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreationAgentStructureAdresseActivity.this);
        alertDialog.setTitle("LE CERTIFICAT A DEJA DEPASSE 90 JOURS ?");
        // alertDialog.setCancelable(false);
        String[] items = {"OUI","NON"};
        int checkedItem = 0;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        if (fonctionag.equals("informaticien")|| fonctionag.equals("Charger naissance")){
                            delaicertificat="1";
                            startActivity(new Intent(getApplicationContext(),EnreJugementSupplitifActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"pas d'acces",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 1:
                        if (fonctionag.equals("informaticien")|| fonctionag.equals("Charger naissance")){
                            delaicertificat="0";
                            startActivity(new Intent(getApplicationContext(),EnreActeNaissanceActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"pas d'acces !!!",Toast.LENGTH_LONG).show();
                        }
                        break;
                }
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

}
