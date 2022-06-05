package id.irkwaja.gestionnaissancedeces.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Random;

import id.irkwaja.gestionnaissancedeces.Controleur.ConnectionClass;
import id.irkwaja.gestionnaissancedeces.MainActivity;
import id.irkwaja.gestionnaissancedeces.Models.ProgressDialogs;
import id.irkwaja.gestionnaissancedeces.R;

public class EnreActeDecesActivity extends AppCompatActivity {
    EditText edtnomdelegue,edtdelegue,edtetabli,edtrecherchecertificat;
    DatePickerDialog picker;
    Button btnenvoyer;
    ConnectionClass connectionClass;
    ProgressBar pbbar;
    String matriculeagent="";
    private Toolbar mtoolbar;
    Spinner spinnergenre,spinnertypedeclarent;
    String nom="",nomdeleg;
    Long idp=0L,idp1=0L,idadd=0L,idDec=0L,idDec1=0L,idcertificat=0L,idcertificat1=0L,idp2=0L;
    TextView txtnom,txtpostnom,txtprenom,txtgenre,txtdatenaiss,txtnomstructure,txtnomparent,txtdateetablissement;
    String nomenf,postnom,prenom,genre,daten,nomstruct,nomparent,dateetabli,delaicertificat1,idjug1;
    ProgressDialog mprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enre_acte_deces);
        setSupportActionBar(mtoolbar);
        mtoolbar = findViewById(R.id.bar_allactivities);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("ACTE DECES");
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mprogress = new ProgressDialog(this);
        btnenvoyer=findViewById(R.id.btnenvoyer);
        connectionClass = new ConnectionClass();
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        edtdelegue=findViewById(R.id.edtdelegue);
        edtnomdelegue=findViewById(R.id.edtnomdelegue);
        spinnertypedeclarent=findViewById(R.id.spinnertypedeclarent);
        pbbar.setVisibility(View.GONE);
        edtdelegue.setVisibility(View.GONE);
        edtnomdelegue.setVisibility(View.GONE);
        edtdelegue.setOnEditorActionListener(editorListener);
        edtetabli=findViewById(R.id.edtetabli);
        edtrecherchecertificat=findViewById(R.id.edtrecherchecertificat);
        edtrecherchecertificat.setOnEditorActionListener(editorListenerCertificat);
        edtetabli.setInputType(InputType.TYPE_NULL);
        edtetabli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(EnreActeDecesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                edtetabli.setText(year + "-" + (month + 1) + "-" + day);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        txtnom=findViewById(R.id.txtnom);
        txtpostnom=findViewById(R.id.txtpostnom);
        txtprenom=findViewById(R.id.txtprenom);
        txtgenre=findViewById(R.id.txtgenre);
        txtdatenaiss=findViewById(R.id.txtdatenaiss);
        txtnomstructure=findViewById(R.id.txtnomstructure);
        txtnomparent=findViewById(R.id.txtnomparent);
        txtdateetablissement=findViewById(R.id.txtdateetablissement);
        ArrayAdapter<CharSequence> adaptertypedeclarant = ArrayAdapter.createFromResource(this, R.array.typedeclarent, android.R.layout.simple_spinner_item);
        adaptertypedeclarant.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertypedeclarent.setAdapter(adaptertypedeclarant);
        spinnertypedeclarent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnertypedeclarent.getSelectedItem().toString().equals("Parent")) {
                    edtdelegue.setVisibility(View.GONE);
                    edtnomdelegue.setVisibility(View.GONE);
                    idDec = 0L;
                }
                if (spinnertypedeclarent.getSelectedItem().toString().equals("Délégué")) {
                    edtdelegue.setVisibility(View.VISIBLE);
                    edtnomdelegue.setVisibility(View.VISIBLE);
                    idp1 = 0L;
                } else {
                    //Toast.makeText(getApplicationContext(),"Selectionnez le type",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnenvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    EnregistrementActeDeces enre=new EnregistrementActeDeces(idp1);
                    enre.execute("");
            }
        });
    }
    private TextView.OnEditorActionListener editorListener=new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            switch (i){
                case EditorInfo.IME_ACTION_SEARCH:
                  DoGetDeclarant declarant = new DoGetDeclarant();
                    declarant.execute("");
            }
            return false;
        }
    };
    private TextView.OnEditorActionListener editorListenerCertificat=new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            switch (i){
                case EditorInfo.IME_ACTION_SEARCH:
                    DoGetCertificatToActeDeces certificat = new DoGetCertificatToActeDeces();
                    certificat.execute("");
            }
            return false;
        }
    };
    public class DoGetDeclarant extends AsyncTask<String,String,String> {
        String z = "";
        Boolean isSuccess = false;
        String userid = edtdelegue.getText().toString();
        @Override
        protected void onPreExecute() {
            mprogress.setTitle("Recherche en cours !!!");
            mprogress.setMessage("Veuillez patienter svp!!");
            mprogress.setCanceledOnTouchOutside(false);
            mprogress.show();
        }

        @Override
        protected void onPostExecute(String r) {
            // pbbar.setVisibility(View.GONE);
            mprogress.dismiss();
//            Intent i = new Intent(LoginStructureActivity.this, MenuStrucureTousActivity.class);
//            startActivity(i);
//            finish();
            idp1=idDec1;
            edtnomdelegue.setText(nomdeleg);
            Toast.makeText(EnreActeDecesActivity.this,String.valueOf(idp1),Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            if(userid.trim().equals(""))
                z = "rechrche delegue est vide ";
            else
            {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Erreure de la connexion SQL server";
                    } else {
                        // String query = "exec spteste '19IGGS001'";
                        String query = "select * from Declarant where teldec='" + userid + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next())
                        {
                            nomdeleg=rs.getString("nomDec")+" "+rs.getString("postnomDec")+" "+rs.getString("prenomDec");
                            idDec1=rs.getLong("idDec");
                            // z = "Login successfull";
                            isSuccess=true;

                        }
                        else
                        {
                            z = "Deleguer non trouver";
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

    public class DoGetCertificatToActeDeces extends AsyncTask<String,String,String> {
        String z = "";
        Boolean isSuccess = false;
        String userid = edtrecherchecertificat.getText().toString();
        @Override
        protected void onPreExecute() {
            mprogress.setTitle("Recherche en cours !!!");
            mprogress.setMessage("Veuillez patienter svp!!");
            mprogress.setCanceledOnTouchOutside(false);
            mprogress.show();
        }

        @Override
        protected void onPostExecute(String r) {
            // pbbar.setVisibility(View.GONE);
            mprogress.dismiss();
            txtnom.setText(nomenf);
            txtpostnom.setText(postnom);
            txtprenom.setText(prenom);
            txtgenre.setText(genre);
            txtdatenaiss.setText(daten);
            txtnomstructure.setText(nomstruct);
            txtnomparent.setText(nomparent);
            txtdateetablissement.setText(daten);
            idp1=idp;
            idp2=idp;
            idcertificat1=idcertificat;
        }

        @Override
        protected String doInBackground(String... params) {
            if(userid.trim().equals(""))
                z = "rechrche delegue est vide ";
            else
            {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Erreure de la connexion SQL server";
                    } else {
                        String query = "select * from CertificatDecesToActe where idCertificatDef='" + userid + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if(rs.next())
                        {
                            isSuccess=true;
                            nomenf=rs.getString("nomDef");
                            postnom=rs.getString("postnomDef");
                            prenom=rs.getString("prenomDef");
                            genre=rs.getString("sexDef");
                            daten=rs.getString("dateDeces");
                            nomstruct=rs.getString("designationStruct");
                            nomparent=rs.getString("nomparent");
                            dateetabli=rs.getString("dateEtablissementCert");
                            idcertificat=rs.getLong("idCertificatDef");
                            idp=rs.getLong("idP");
                            //  Log.d("DDDD",nom+" "+idp);
                            //z = "Login successfull";

                        }
                        else
                        {
                            z = "Parent non trouver";
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

    public class EnregistrementActeDeces extends AsyncTask<String,String,String> {
        String zx = "";
        Boolean isSuccess = false;
        public EnregistrementActeDeces(Long code){
            idadd=code;
        }
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
            pbbar.setVisibility(View.GONE);
            mprogress.dismiss();
            ProgressDialogs.showDialog(EnreActeDecesActivity.this, "success", r);
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
                        String query1 =("exec Sp_insererActeDeces '"+edtetabli.getText().toString().trim()+"','"+RandomDigits(7)+"','"+idcertificat1+"','"+spinnertypedeclarent.getSelectedItem().toString().toUpperCase()+"','"+idadd+"','"+ MainActivity.matricule+"','"+idp2+"','OUI'");
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
        txtnom.setText("");
        txtpostnom.setText("");
        txtprenom.setText("");
        txtgenre.setText("");
        txtdatenaiss.setText("");
        txtdateetablissement.setText("");
        edtetabli.setText("");
        edtnomdelegue.setText("");
        edtdelegue.setText("");
        txtnomstructure.setText("");
        txtnomparent.setText("");
    }
}
