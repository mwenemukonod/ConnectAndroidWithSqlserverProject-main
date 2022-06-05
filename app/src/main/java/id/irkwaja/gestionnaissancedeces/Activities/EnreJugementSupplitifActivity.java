package id.irkwaja.gestionnaissancedeces.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import id.irkwaja.gestionnaissancedeces.Controleur.ConnectionClass;
import id.irkwaja.gestionnaissancedeces.Models.ProgressDialogs;
import id.irkwaja.gestionnaissancedeces.R;

public class EnreJugementSupplitifActivity extends AppCompatActivity {
    EditText edtnomjug,edtdatejug,edtmotif;
    Button btnenvoyer;
    ConnectionClass connectionClass;
    ProgressBar pbbar;
    private Toolbar mtoolbar;
    String idjugemnt;
    public static String idjugement1;
    DatePickerDialog picker;
    ProgressDialog mprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enre_jugement_supplitif);
        mtoolbar = findViewById(R.id.bar_allactivities);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("ENREGISTRE JUGMENT SUPPLITIF");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mprogress = new ProgressDialog(this);
        edtnomjug=findViewById(R.id.edtnomjug);
        edtdatejug=findViewById(R.id.edtdatejug);
        edtmotif=findViewById(R.id.edtmotif);
        btnenvoyer=findViewById(R.id.btnenvoyer);
        connectionClass = new ConnectionClass();
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        edtdatejug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(EnreJugementSupplitifActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                edtdatejug.setText(day + "-" + (month + 1) + "-" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        btnenvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtnomjug.getText().toString().equals("")|| edtdatejug.getText().toString().equals("")|| edtmotif.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"remplir les champs vide s'il vous plait",Toast.LENGTH_LONG).show();
                }
                else {
                    EnregistrementJugement enre=new EnregistrementJugement();
                    enre.execute("");
                }
            }
        });
    }
    public class EnregistrementJugement extends AsyncTask<String,String,String> {
        String zx = "";
        Boolean isSuccess = false;
        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
            mprogress.setTitle("Enregistrement en cours !!!");
            mprogress.setMessage("Veuillez patienter svp!!");
            mprogress.setCanceledOnTouchOutside(false);
            mprogress.show();
        }
        @Override
        protected void onPostExecute(String r) {
            if(r.equals("Jugement suppletif enregistré avec sucès")){
                pbbar.setVisibility(View.GONE);
                mprogress.dismiss();
                idjugement1=idjugemnt;
                ProgressDialogs.showDialog(EnreJugementSupplitifActivity.this, "success", r);
                netoyerzone();
                startActivity(new Intent(getApplicationContext(),EnreActeNaissanceActivity.class));
            }
            else {
                mprogress.dismiss();
                pbbar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),r,Toast.LENGTH_LONG).show();
            }

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
                        idjugemnt=RandomDigits(6);
                        idjugement1=idjugemnt;
                        String query1 =("exec Sp_insererJugementSuppletif '"+idjugemnt+"','"+edtdatejug.getText().toString()+"','"+edtmotif.getText().toString().toUpperCase().trim()+"','"+edtnomjug.getText().toString().toUpperCase()+"'");
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
        startActivity(new Intent(getApplicationContext(), CreationAgentStructureAdresseActivity.class));
    }
    private void netoyerzone(){
        edtnomjug.setText("");
        edtdatejug.setText("");
        edtmotif.setText("");
    }
}
