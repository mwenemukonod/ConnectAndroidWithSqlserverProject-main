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

import com.pixplicity.easyprefs.library.Prefs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import id.irkwaja.gestionnaissancedeces.Controleur.ConnectionClass;
import id.irkwaja.gestionnaissancedeces.Models.ProgressDialogs;
import id.irkwaja.gestionnaissancedeces.R;

public class EnreEnfantActivity extends AppCompatActivity {
    EditText edtnomenf,edtpostnomenf,edtprenomenf,edtdatenaiss,edtlieunais,edtheurenaiss,edtpoids,edtdelegue,edtnomdelegue,
            edtrechercheparent,edtnomparent,edtidparent;
    Button btnenvoyer;
    ConnectionClass connectionClass;
    ProgressBar pbbar;
    String matriculeagent="";
    private Toolbar mtoolbar;
    Spinner spinnergenre,spinnerstatut,spinnertypedeclarent;
    DatePickerDialog picker;
    String nom="",nomdeleg;
    Long idp=0L,idadd=0L,idDec=0L,idDec1=0L,idp1=0L;
    ProgressDialog mprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enre_enfant);
        mtoolbar = findViewById(R.id.bar_allactivities);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("CERTIFICAT NAISSANCE");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mprogress = new ProgressDialog(this);
        btnenvoyer=findViewById(R.id.btnenvoyer);
        connectionClass = new ConnectionClass();
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        edtnomenf=findViewById(R.id.edtnomenf);
        edtpostnomenf=findViewById(R.id.edtpostnomenf);
        edtprenomenf=findViewById(R.id.edtprenomenf);
        edtdatenaiss=findViewById(R.id.edtdatenaiss);
        edtlieunais=findViewById(R.id.edtlieunais);
        edtheurenaiss=findViewById(R.id.edtheurenaiss);
        edtpoids=findViewById(R.id.edtpoids);
        edtdelegue=findViewById(R.id.edtdelegue);
        edtnomdelegue=findViewById(R.id.edtnomdelegue);
        spinnergenre=findViewById(R.id.spinnergenre);
        spinnerstatut=findViewById(R.id.spinnerstatut);
        spinnertypedeclarent=findViewById(R.id.spinnertypedeclarent);
        edtdatenaiss=findViewById(R.id.edtdatenaiss);
        edtdatenaiss.setInputType(InputType.TYPE_NULL);
        edtdelegue.setVisibility(View.GONE);
        edtnomdelegue.setVisibility(View.GONE);
        edtdelegue.setOnEditorActionListener(editorListener);
        edtrechercheparent=findViewById(R.id.edtrechercheparent);
        edtrechercheparent.setOnEditorActionListener(editorListenerChercheParent);
        edtnomparent=findViewById(R.id.edtnomparent);
        edtidparent=findViewById(R.id.edtidparent);
        edtdatenaiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                    picker = new DatePickerDialog(EnreEnfantActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                edtdatenaiss.setText(year + "-" + (month + 1) + "-" + day);
                                //dateMetre2=year+"-"+month+"-"+day;
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        ArrayAdapter<CharSequence> adaptergenre = ArrayAdapter.createFromResource(this, R.array.genre, android.R.layout.simple_spinner_item);
        adaptergenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnergenre.setAdapter(adaptergenre);
        ArrayAdapter<CharSequence> adapterstatut = ArrayAdapter.createFromResource(this, R.array.statut, android.R.layout.simple_spinner_item);
        adapterstatut.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerstatut.setAdapter(adapterstatut);
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
                if(spinnertypedeclarent.getSelectedItem().toString().equals("D??l??gu??")){
                    edtdelegue.setVisibility(View.VISIBLE);
                    edtnomdelegue.setVisibility(View.VISIBLE);
                    idp=0L;
                }
                else {
                    //Toast.makeText(getApplicationContext(),"Selectionnez le type",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        btnenvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtnomenf.getText().toString().equals("")|| edtpostnomenf.getText().toString().equals("")|| edtprenomenf.getText().toString().equals("")|| edtpoids.getText().toString().equals("")|| edtnomparent.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"remplir les champs vide s'il vous plait",Toast.LENGTH_LONG).show();
                }
                else {
                    EnregistrementCertificat enre = new EnregistrementCertificat(idp1);
                    enre.execute("");
                }
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
            Toast.makeText(EnreEnfantActivity.this,String.valueOf(idDec),Toast.LENGTH_SHORT).show();
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
            mprogress.setTitle("Recherche en cours !!!");
            mprogress.setMessage("Veuillez patienter svp!!");
            mprogress.setCanceledOnTouchOutside(false);
            mprogress.show();
        }
        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            mprogress.dismiss();
            ProgressDialogs.showDialog(EnreEnfantActivity.this, "success", r);
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
                        String jug="ju1";
                        String matAgent="123";
                        Long time = System.currentTimeMillis();
                        Date result = new Date(time);
                        Long id=Long.parseLong(RandomDigits(11));
                        Long id1= Long.parseLong(RandomDigits(6));
                        String current_time = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(result);
                        String query1="exec Sp_insererCertificatNaissance '"+ id +"','"+edtnomenf.getText().toString().trim().toUpperCase()+"','"+edtpostnomenf.getText().toString().toUpperCase().trim()+"','"+edtprenomenf.getText().toString().toUpperCase()+"','"+spinnergenre.getSelectedItem().toString()+"','"+edtdatenaiss.getText().toString().trim()+"','"+edtlieunais.getText().toString().trim()+"','"+edtheurenaiss.getText().toString().trim()+"','"+edtpoids.getText().toString().trim()+"','"+current_time+"','"+id1 +"','"+spinnerstatut.getSelectedItem().toString()+"','"+spinnertypedeclarent.getSelectedItem().toString()+"','"+idadd+"','"+LoginStructureActivity.matricule+"','"+edtidparent.getText().toString()+"','ju1','123','NON','"+edtheurenaiss.getText().toString()+"'";
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
        edtnomenf.setText("");
        edtpostnomenf.setText("");
        edtprenomenf.setText("");
        edtdatenaiss.setText("");
        edtlieunais.setText("");
        edtheurenaiss.setText("");
        edtpoids.setText("");
        edtdelegue.setText("");
        edtnomdelegue.setText("");
        edtrechercheparent.setText("");
        edtnomparent.setText("");
    }
}
