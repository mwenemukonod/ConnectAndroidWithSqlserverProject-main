package id.irkwaja.gestionnaissancedeces.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class EnreAgentActivity extends AppCompatActivity {
EditText edtmatricule,edtnomAgent,edtpostnomAgent,edtprenomAgent,edttelephoneAgent;
    Button btnenvoyer;
    ConnectionClass connectionClass;
    String datescan;
    ProgressBar pbbar;
    private Toolbar mtoolbar;
    ProgressDialog mprogress;
    Spinner edtfonctionAgent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enre_agent);
        mtoolbar = findViewById(R.id.bar_allactivities);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("CREATION AGENT");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mprogress = new ProgressDialog(this);
        edtmatricule=findViewById(R.id.edtmatricule);
        edtnomAgent=findViewById(R.id.edtnomAgent);
        edtpostnomAgent=findViewById(R.id.edtpostnomAgent);
        edtprenomAgent=findViewById(R.id.edtprenomAgent);
        edtfonctionAgent=findViewById(R.id.edtfonctionAgent);
        edttelephoneAgent=findViewById(R.id.edttelephoneAgent);
        btnenvoyer=findViewById(R.id.btnenvoyer);
        connectionClass = new ConnectionClass();
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        ArrayAdapter<CharSequence> adaptertypedeclarant = ArrayAdapter.createFromResource(this, R.array.fomctiomagent, android.R.layout.simple_spinner_item);
        adaptertypedeclarant.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtfonctionAgent.setAdapter(adaptertypedeclarant);
        btnenvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnregistrerAgent agent=new EnregistrerAgent();
                agent.execute("");
            }
        });
    }

    public class EnregistrerAgent extends AsyncTask<String,String,String> {
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
            ProgressDialogs.showDialog(EnreAgentActivity.this, "success", r);
            edtmatricule.setText("");
            edtnomAgent.setText("");
            edtpostnomAgent.setText("");
            edtprenomAgent.setText("");
            edtfonctionAgent.setSelection(0);
            edttelephoneAgent.setText("");
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
                    DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
                    String current_time=df.format(new Date());
                    datescan=current_time;
                    String mptdep= RandomDigits(5);
                    if (con == null) {
                        zx = "Erreure de la connexion SQL server";
                    } else {
                        String query1 =("exec Sp_insererAgent '"+edtmatricule.getText().toString().trim()+"','"+edtnomAgent.getText().toString().trim().toUpperCase()+"','"+edtpostnomAgent.getText().toString().toUpperCase().trim()+"','"+edtprenomAgent.getText().toString().toUpperCase()+"','"+edtfonctionAgent.getSelectedItem().toString()+"','"+edttelephoneAgent.getText().toString().toUpperCase().trim()+"','"+mptdep+"'");

                        Statement stmt = con.createStatement();
                        ResultSet rsb = stmt.executeQuery(query1);
                        if (rsb.next()) {

                            zx=  rsb.getString("reponse");
                            sendSMS(edttelephoneAgent.getText().toString(),"Mr(Mme,Mlle): "+edtnomAgent.getText().toString()+"\n"+"Votre mot de passe est :"+mptdep);
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
