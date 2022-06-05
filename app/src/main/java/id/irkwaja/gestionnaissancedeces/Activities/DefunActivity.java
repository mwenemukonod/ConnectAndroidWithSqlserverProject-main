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
import android.util.Log;
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
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import id.irkwaja.gestionnaissancedeces.Controleur.ConnectionClass;
import id.irkwaja.gestionnaissancedeces.Models.ProgressDialogs;
import id.irkwaja.gestionnaissancedeces.R;

public class DefunActivity extends AppCompatActivity {
    EditText edtnomdef,edtpostnomdef,edtprenomdef,edtdatenaiss,edtage,edtnomdelegue,edtdelegue,
            edtrechercheparent,edtnomparent,edtidparent,edtprovince,edtvilledietri,edtcomnuneterrioire,edtcheferie,
            edtquartier,edtavlocalite,
            edtnumeroparcel,edtresidenceactuel,edtcausedece,edtlieudece,edtmomentD,edtdateDece,edtnationalitedefun;
    Button btnenvoyer;
    ConnectionClass connectionClass;
    ProgressBar pbbar;
    String matriculeagent="";
    private Toolbar mtoolbar;
    Spinner spinnergenre,spinnertypedeclarent;
    DatePickerDialog picker;
    String nom="",nomdeleg;
    Long idp=0L,idp1=0L,idadd=0L,idDec=0L,idDec1=0L;
    ProgressDialog mprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defun);
        mtoolbar = findViewById(R.id.bar_allactivities);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("CERTIFICAT DECES");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mprogress = new ProgressDialog(this);
        btnenvoyer=findViewById(R.id.btnenvoyer);
        connectionClass = new ConnectionClass();
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        edtnomdef=findViewById(R.id.edtnomdef);
        edtpostnomdef=findViewById(R.id.edtpostnomdef);
        edtprenomdef=findViewById(R.id.edtprenomdef);
        edtage=findViewById(R.id.edtage);
        edtprovince=findViewById(R.id.edtprovince);
        edtvilledietri=findViewById(R.id.edtvilledietri);
        edtcomnuneterrioire=findViewById(R.id.edtcomnuneterrioire);
        edtdateDece=findViewById(R.id.edtdateDece);
        edtnomdef=findViewById(R.id.edtnomdef);
        spinnergenre=findViewById(R.id.spinnergenre);
        edtpostnomdef=findViewById(R.id.edtpostnomdef);
        edtprenomdef=findViewById(R.id.edtprenomdef);
        edtage=findViewById(R.id.edtage);
        spinnertypedeclarent=findViewById(R.id.spinnertypedeclarent);
        edtcausedece=findViewById(R.id.edtcausedece);
        edtlieudece=findViewById(R.id.edtlieudece);
        edtmomentD=findViewById(R.id.edtmomentD);
        edtcheferie=findViewById(R.id.edtcheferie);
        edtquartier=findViewById(R.id.edtquartier);
        edtavlocalite=findViewById(R.id.edtavlocalite);
        edtnumeroparcel=findViewById(R.id.edtnumeroparcel);
        edtresidenceactuel=findViewById(R.id.edtresidenceactuel);
        edtdatenaiss=findViewById(R.id.edtdatenaiss);
        edtdatenaiss.setInputType(InputType.TYPE_NULL);
        edtdateDece.setInputType(InputType.TYPE_NULL);
        edtdelegue=findViewById(R.id.edtdelegue);
        edtdelegue.setVisibility(View.GONE);
        edtnomdelegue=findViewById(R.id.edtnomdelegue);
        edtnomdelegue.setVisibility(View.GONE);
        edtdelegue.setOnEditorActionListener(editorListener);
        edtrechercheparent=findViewById(R.id.edtrechercheparent);
        edtrechercheparent.setOnEditorActionListener(editorListenerChercheParent);
        edtnomparent=findViewById(R.id.edtnomparent);
        edtidparent=findViewById(R.id.edtidparent);
        edtidparent=findViewById(R.id.edtidparent);
        edtnationalitedefun=findViewById(R.id.edtnationalitedefun);
        edtdatenaiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(DefunActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                edtdatenaiss.setText(year + "-" + (month + 1) + "-" + day);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        edtdateDece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(DefunActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                edtdateDece.setText(year + "-" + (month + 1) + "-" + day);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        ArrayAdapter<CharSequence> adaptergenre = ArrayAdapter.createFromResource(this, R.array.genre, android.R.layout.simple_spinner_item);
        adaptergenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnergenre.setAdapter(adaptergenre);

        ArrayAdapter<CharSequence> adaptertypedeclarant = ArrayAdapter.createFromResource(this, R.array.typedeclarent, android.R.layout.simple_spinner_item);
        adaptertypedeclarant.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertypedeclarent.setAdapter(adaptertypedeclarant);
        spinnertypedeclarent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinnertypedeclarent.getSelectedItem().toString().equals("Parent")){
                    edtdelegue.setVisibility(View.GONE);
                    edtnomdelegue.setVisibility(View.GONE);
                    idDec=0L;
                }
                if(spinnertypedeclarent.getSelectedItem().toString().equals("Délégué")){
                    edtdelegue.setVisibility(View.VISIBLE);
                    edtnomdelegue.setVisibility(View.VISIBLE);
                    idp=0L;
                }
                else { }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        btnenvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   EnregistrementCertificat enre=new EnregistrementCertificat(idp1);
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
    private TextView.OnEditorActionListener editorListenerChercheParent=new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            switch (i){
                case EditorInfo.IME_ACTION_SEARCH:
                    DoGetParent getparent = new DoGetParent();
                    getparent.execute("");
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
           mprogress.dismiss();
            idp1=idDec1;
            edtnomdelegue.setText(nomdeleg);
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
                        String query = "select * from Declarant where teldec='" + userid + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next())
                        {
                            nomdeleg=rs.getString("nomDec")+" "+rs.getString("postnomDec")+" "+rs.getString("prenomDec");
                            idDec1=rs.getLong("idDec");
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
    public class DoGetParent extends AsyncTask<String,String,String> {
        String z = "";
        Boolean isSuccess = false;
        String userid = edtrechercheparent.getText().toString();
        @Override
        protected void onPreExecute() {
            mprogress.setTitle("Recherche en cours !!!");
            mprogress.setMessage("Veuillez patienter svp!!");
            mprogress.setCanceledOnTouchOutside(false);
            mprogress.show();
        }

        @Override
        protected void onPostExecute(String r) {
           mprogress.dismiss();
            edtnomparent.setText(nom);
            edtidparent.setText(String.valueOf(idp));
            idp1=idp;
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
                        String query = "select * from Parent where telPere='" + userid + "' or telMere='" + userid + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if(rs.next())
                        {
                            isSuccess=true;
                            nom=rs.getString("nomPere")+" "+rs.getString("postnomPere")+" "+rs.getString("prenomPere");
                            idp=rs.getLong("idP");
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

    public class EnregistrementCertificat extends AsyncTask<String,String,String> {
        String zx = "";
        Boolean isSuccess = false;
        public EnregistrementCertificat(Long code){
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
            ProgressDialogs.showDialog(DefunActivity.this, "success", r);
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
                        Long time = System.currentTimeMillis();
                        Date result = new Date(time);
                        Long id=Long.parseLong(RandomDigits(11));
                        Long id1= Long.parseLong(RandomDigits(6));
                        String current_time = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(result);
                        String query1="exec Sp_insererCertificatDece '"+ id +"','"+edtnomdef.getText().toString().trim().toUpperCase()+"','"+edtpostnomdef.getText().toString().toUpperCase().trim()+"','"+edtprenomdef.getText().toString().toUpperCase()+"','"+spinnergenre.getSelectedItem().toString()+"','"+edtdatenaiss.getText().toString().trim()+"','"+edtage.getText().toString().trim()+"','"+edtlieudece.getText().toString().trim()+"','"+edtmomentD.getText().toString().trim()+"','"+edtcausedece.getText().toString()+"','"+edtdateDece.getText().toString()+"','"+current_time+"','"+id1 +"','"+edtprovince.getText().toString()+"','"+edtvilledietri.getText().toString()+"','"+edtcomnuneterrioire.getText().toString()+"','"+edtquartier.getText().toString()+"','"+edtcheferie.getText().toString()+"','"+edtavlocalite.getText().toString()+"','"+edtresidenceactuel.getText().toString()+"','"+edtnumeroparcel.getText().toString()+"','"+idadd+"','"+LoginStructureActivity.matricule+"','"+edtidparent.getText().toString()+"','123','"+spinnertypedeclarent.getSelectedItem().toString()+"','"+edtnationalitedefun.getText().toString()+"'";
                        Statement stmt = con.createStatement();
                        ResultSet rsb = stmt.executeQuery(query1);
                        if (rsb.next()) {

                            zx=  rsb.getString("reponse");

                            isSuccess = true;

                        } else {
                            zx =   rsb.getString("reponse");

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
        edtnomdef.setText("");
        edtpostnomdef.setText("");
        edtprenomdef.setText("");
        edtdatenaiss.setText("");
        edtlieudece.setText("");
        edtcausedece.setText("");
        edtmomentD.setText("");
        edtdelegue.setText("");
        edtnomdelegue.setText("");
        edtrechercheparent.setText("");
        edtnomparent.setText("");
    }

}
