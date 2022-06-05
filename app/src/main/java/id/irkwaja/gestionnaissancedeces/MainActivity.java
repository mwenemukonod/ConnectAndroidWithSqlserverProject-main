package id.irkwaja.gestionnaissancedeces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import id.irkwaja.gestionnaissancedeces.Activities.AcceuilStructureEtatCivilActivity;
import id.irkwaja.gestionnaissancedeces.Activities.CreationAgentStructureAdresseActivity;
import id.irkwaja.gestionnaissancedeces.Activities.EnreStructureSanitaireActivity;
import id.irkwaja.gestionnaissancedeces.Controleur.ConnectionClass;

public class MainActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    EditText edtuserid,edtpass;
    Button btnlogin;
    ProgressBar pbbar;
    public  static String nomut = "",fonctiongent="",matricule="";
    private Toolbar mtoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtoolbar = findViewById(R.id.bar_allactivities);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("LOGIN");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        connectionClass = new ConnectionClass();
        edtuserid = (EditText) findViewById(R.id.edtuserid);
        edtpass = (EditText) findViewById(R.id.edtpass);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoLogin doLogin = new DoLogin();
                doLogin.execute("");

            }
        });
    }
    public class DoLogin extends AsyncTask<String,String,String> {
        String z = "";
        Boolean isSuccess = false;
        String userid = edtuserid.getText().toString();
        String password = edtpass.getText().toString();
        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            //Toast.makeText(MainActivity.this,r,Toast.LENGTH_SHORT).show();
            if(isSuccess==true) {
                    Intent i = new Intent(MainActivity.this, CreationAgentStructureAdresseActivity.class);
                    startActivity(i);
                    finish();
            }
            else {
                Toast.makeText(MainActivity.this,r,Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            if(userid.trim().equals("")|| password.trim().equals(""))
                z = "entrer le nom utilisateur et le mot de passe ";
            else
            {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Erreure de la connexion SQL server";
                    } else {
                        // String query = "exec spteste '19IGGS001'";
                        String query = "select * from Agent where matricule='" + userid + "' and motDepasse='" + password + "'";
                        Prefs.putString("nomAgent",userid);
                        nomut=userid;
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if(rs.next())
                        {
                           // z = "Login successfull";
                            isSuccess=true;
                            nomut=rs.getString("nomAgent")+" "+rs.getString("postnomAgent")
                                    +" "+rs.getString("prenomAgent");
                            fonctiongent=rs.getString("fonctionAgent");
                            matricule=rs.getString("matricule");
                        }
                        else
                        {
                            z = "Nom utilisateur et/ou Mot de passe inccorect";
                            isSuccess = false;
                        }

                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = ex.getMessage().toString();
                }
            }
            return z;
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(), AcceuilStructureEtatCivilActivity.class));
    }
}
