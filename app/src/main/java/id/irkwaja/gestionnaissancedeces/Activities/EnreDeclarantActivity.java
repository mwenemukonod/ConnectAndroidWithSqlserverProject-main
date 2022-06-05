package id.irkwaja.gestionnaissancedeces.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import id.irkwaja.gestionnaissancedeces.Models.ProgressDialogs;
import id.irkwaja.gestionnaissancedeces.R;

public class EnreDeclarantActivity extends AppCompatActivity {
EditText edtnom,edtpostnom,edtprenom,edtage,edttelephone;
    Button btnenvoyer;
    ConnectionClass connectionClass;
    ProgressBar pbbar;
    String matriculeagent="";
    private Toolbar mtoolbar;
    ProgressDialog mprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enre_declarant);
        mtoolbar = findViewById(R.id.bar_allactivities);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("ENREGISTRE DECLARANT");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mprogress = new ProgressDialog(this);
        edtnom=findViewById(R.id.edtnom);
        edtpostnom=findViewById(R.id.edtpostnom);
        edtprenom=findViewById(R.id.edtpostprenom);
        edtage=findViewById(R.id.edtage);
        edttelephone=findViewById(R.id.edttelephone);
        btnenvoyer=findViewById(R.id.btnenvoyer);
        connectionClass = new ConnectionClass();
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        btnenvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtnom.getText().toString().equals("")|| edtpostnom.getText().toString().equals("")|| edtprenom.getText().toString().equals("")|| edtage.getText().toString().equals("")|| edttelephone.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"remplir les champs vide s'il vous plait",Toast.LENGTH_LONG).show();
                }
                else {
                    EnregistrementDeclarant enre=new EnregistrementDeclarant();
                    enre.execute("");
                }
            }
        });

    }
    public class EnregistrementDeclarant extends AsyncTask<String,String,String> {
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
            mprogress.dismiss();
            pbbar.setVisibility(View.GONE);
            ProgressDialogs.showDialog(EnreDeclarantActivity.this, "success", r);
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
                        String query1 =("exec Sp_insererDeclarant '"+RandomDigits(11)+"','"+edtnom.getText().toString().trim().toUpperCase()+"','"+edtpostnom.getText().toString().toUpperCase().trim()+"','"+edtprenom.getText().toString().toUpperCase()+"','"+edtage.getText().toString()+"','"+edttelephone.getText().toString().trim()+"'");
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
        startActivity(new Intent(getApplicationContext(), MenuStrucureTousActivity.class));
    }
    private void netoyerzone(){
        edtnom.setText("");
        edtpostnom.setText("");
        edtprenom.setText("");
        edtage.setText("");
        edttelephone.setText("");
    }
}
