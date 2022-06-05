package id.irkwaja.gestionnaissancedeces.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import id.irkwaja.gestionnaissancedeces.Controleur.ConnectionClass;
import id.irkwaja.gestionnaissancedeces.MainActivity;
import id.irkwaja.gestionnaissancedeces.Models.ProgressDialogs;
import id.irkwaja.gestionnaissancedeces.R;

public class EnreStructureSanitaireActivity extends AppCompatActivity {
EditText edtdesignation,edtsigle,edtcontact,edtnomrepo,edtpostnomrespo,edtprenom,edtqualitetire;
    Button btnenvoyer;
    ConnectionClass connectionClass;
    ProgressBar pbbar;
    String matriculeagent="";
    private Toolbar mtoolbar;
    ProgressDialog mprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enre_structure_sanitaire);
        mtoolbar = findViewById(R.id.bar_allactivities);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("STRUCTURE SANITAIRE/HOSPITALIERE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mprogress = new ProgressDialog(this);
        edtdesignation=findViewById(R.id.edtdesignation);
        edtsigle=findViewById(R.id.edtsigle);
        edtcontact=findViewById(R.id.edtcontact);
        edtnomrepo=findViewById(R.id.edtnomrepo);
        edtpostnomrespo=findViewById(R.id.edtpostnomrespo);
        edtprenom=findViewById(R.id.edtprenom);
        edtqualitetire=findViewById(R.id.edtqualitetire);
        btnenvoyer=findViewById(R.id.btnenvoyer);
        connectionClass = new ConnectionClass();
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        matriculeagent= MainActivity.matricule;
        btnenvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnregistrementStructure enrestructure=new EnregistrementStructure();
                enrestructure.execute("");
            }
        });
    }
    public class EnregistrementStructure extends AsyncTask<String,String,String> {
        String zx = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
            mprogress.setTitle("Recherche en cours !!!");
            mprogress.setMessage("Veuillez patienter svp!!");
            mprogress.setCanceledOnTouchOutside(false);
            mprogress.show();
        }
        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            mprogress.dismiss();
            ProgressDialogs.showDialog(EnreStructureSanitaireActivity.this, "success", r);
            netoyerzone();
        }
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {
            String f="s";
            if(f.equals("fg"))
                zx = "oups ! ";
            else
            {
                try {
                    Connection con = connectionClass.CONN();

                    if (con == null) {
                        zx = "Erreure de la connexion SQL server";
                    } else {
                        String mptdep= RandomDigits(5);
                        String query1 =("exec Sp_insererStructure '"+RandomDigits(10)+"','"+edtdesignation.getText().toString().trim().toUpperCase()+"','"+edtsigle.getText().toString().toUpperCase().trim()+"','"+edtcontact.getText().toString().toUpperCase()+"','"+edtnomrepo.getText().toString()+"','"+edtpostnomrespo.getText().toString().toUpperCase().trim()+"','"+edtprenom.getText().toString().toUpperCase().trim()+"','"+edtqualitetire.getText().toString().toUpperCase().trim()+"','"+mptdep+"','"+matriculeagent+"'");
                        Statement stmt = con.createStatement();
                        ResultSet rsb = stmt.executeQuery(query1);
                        if (rsb.next()) {

                            zx=  rsb.getString("reponse");
                            sendSMS(edtcontact.getText().toString()," "+edtdesignation.getText().toString()+"\n"+"Vous avez ete creer avec succes"+"\n"+"Votre mot de passe est :"+mptdep);

                            isSuccess = true;
                        } else {
                            zx =   rsb.getString("reponse");;
                            isSuccess = false;
                        }
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    zx = ex.getMessage().toString();

                }
            }
            return zx;
        }
    }
    public String RandomDigits(int length) {
        Random random = new Random();
        String s = "";
        for (int i = 0; i < length; i++)
            s = s + random.nextInt(10);
        return s;
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),CreationAgentStructureAdresseActivity.class));
    }
    private void netoyerzone(){
        edtdesignation.setText("");
        edtsigle.setText("");
        edtcontact.setText("");
        edtnomrepo.setText("");
        edtpostnomrespo.setText("");
        edtprenom.setText("");
        edtqualitetire.setText("");
    }

    protected void sendSMS(String phonNum, String message) {

        try {
            SmsManager mysms = SmsManager.getDefault();
            mysms.sendTextMessage(phonNum, null, message, null, null);
            Toast.makeText(this, "Code Envoyé", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
