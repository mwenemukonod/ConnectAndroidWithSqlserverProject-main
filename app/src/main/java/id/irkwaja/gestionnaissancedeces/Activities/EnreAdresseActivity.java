package id.irkwaja.gestionnaissancedeces.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import id.irkwaja.gestionnaissancedeces.R;

public class EnreAdresseActivity extends AppCompatActivity {
    EditText edtprovince,edtvilledietri,edtcomnuneterrioire,
            edtquartier,edtcheferie,edtavlocalite,edtresidenceactuel,edtnumero;
    Button btnenvoyer;
    ConnectionClass connectionClass;
    ProgressBar pbbar;
    private Toolbar mtoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enre_adresse);
        mtoolbar = findViewById(R.id.bar_allactivities);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("PARAMETRE ADRESSE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtprovince=findViewById(R.id.edtprovince);
        edtvilledietri=findViewById(R.id.edtvilledietri);
        edtcomnuneterrioire=findViewById(R.id.edtcomnuneterrioire);
        edtquartier=findViewById(R.id.edtquartier);
        edtcheferie=findViewById(R.id.edtcheferie);
        edtnumero=findViewById(R.id.edtnumero);
        edtavlocalite=findViewById(R.id.edtavlocalite);
        edtresidenceactuel=findViewById(R.id.edtresidenceactuel);
        btnenvoyer=findViewById(R.id.btnenvoyer);
        connectionClass = new ConnectionClass();
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        btnenvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParamettreAdresse paramettreAdresse=new ParamettreAdresse();
                paramettreAdresse.execute("");
            }
        });
    }
    public class ParamettreAdresse extends AsyncTask<String,String,String> {
        String zx = "";
        Boolean isSuccess = false;
        //        int cote=Integer.parseInt(edtcote.getText().toString());
        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(EnreAdresseActivity.this,r,Toast.LENGTH_SHORT).show();
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
                        //  String query1 ="insert into Agent(matricule,nomAgent,postnomAgent,prenomAgent,fonctionAgent,telephoneAgent,motDepasse)VALUES('"+edtmatricule.getText().toString().trim()+"','"+edtnomAgent.getText().toString().trim().toUpperCase()+"','"+edtpostnomAgent.getText().toString().toUpperCase().trim()+"','"+edtprenomAgent.getText().toString().toUpperCase()+"','"+edtfonctionAgent.getText().toString()+"','"+edttelephoneAgent.getText().toString().toUpperCase().trim()+"','"+RandomDigits(5)+"')";
                        String query1 =("exec Sp_insererAdresse '"+RandomDigits(10)+"','"+edtprovince.getText().toString().trim().toUpperCase()+"','"+edtvilledietri.getText().toString().toUpperCase().trim()+"','"+edtcomnuneterrioire.getText().toString().toUpperCase()+"','"+edtquartier.getText().toString()+"','"+edtcheferie.getText().toString().toUpperCase().trim()+"','"+edtavlocalite.getText().toString().toUpperCase().trim()+"','"+edtresidenceactuel.getText().toString().toUpperCase().trim()+"','"+edtnumero.getText().toString()+"'");
                        Statement stmt = con.createStatement();
                        ResultSet rsb = stmt.executeQuery(query1);
                        if (rsb.next()) {

                            zx=  rsb.getString("reponse");
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
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
