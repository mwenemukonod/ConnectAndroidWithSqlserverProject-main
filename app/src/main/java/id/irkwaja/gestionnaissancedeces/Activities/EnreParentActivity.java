package id.irkwaja.gestionnaissancedeces.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.Random;

import id.irkwaja.gestionnaissancedeces.Controleur.ConnectionClass;
import id.irkwaja.gestionnaissancedeces.Models.ProgressDialogs;
import id.irkwaja.gestionnaissancedeces.R;

public class EnreParentActivity extends AppCompatActivity {

    Button btnenvoyer;
    ConnectionClass connectionClass;
    ProgressBar pbbar;
    private Toolbar mtoolbar;
    Spinner spinnertypeparent;
    EditText edtnomp,edtpostnomp,edtprenomp,edtnomm,edtpostnomm,edtprenom,edtprofessionp,edtprofessionm,edtlieunaisp,
            edtlieunaism,edtagepere,edtagemere,edtnatpere,edtnatmere,edttelephonep,edttelephonem,edtprovince,
            edtvilledietri,edtcomnuneterrioire,edtqualitetire,edtcheferie,edtavlocalite,edtnumero;
    ProgressDialog mprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enre_parent);
        mtoolbar = findViewById(R.id.bar_allactivities);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("ENREGISTRE PARENT");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mprogress = new ProgressDialog(this);
        btnenvoyer=findViewById(R.id.btnenvoyer);
        connectionClass = new ConnectionClass();
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        spinnertypeparent=findViewById(R.id.spinnertypeparent);
        edtnomp=findViewById(R.id.edtnomp);
        edtpostnomp=findViewById(R.id.edtpostnomp);
        edtprenomp=findViewById(R.id.edtprenomp);
        edtnomm=findViewById(R.id.edtnomm);
        edtpostnomm=findViewById(R.id.edtpostnomm);
        edtprenom=findViewById(R.id.edtprenom);
        edtprofessionp=findViewById(R.id.edtprofessionp);
        edtprofessionm=findViewById(R.id.edtprofessionm);
        edtlieunaisp=findViewById(R.id.edtlieunaisp);
        edtlieunaism=findViewById(R.id.edtlieunaism);
        edtagepere=findViewById(R.id.edtagepere);
        edtagemere=findViewById(R.id.edtagemere);
        edtnatpere=findViewById(R.id.edtnatpere);
        edtnatmere=findViewById(R.id.edtnatmere);
        edttelephonep=findViewById(R.id.edttelephonep);
        edttelephonem=findViewById(R.id.edttelephonem);
        edtprovince=findViewById(R.id.edtprovince);
        edtvilledietri=findViewById(R.id.edtvilledietri);
        edtcomnuneterrioire=findViewById(R.id.edtcomnuneterrioire);
        edtqualitetire=findViewById(R.id.edtqualitetire);
        edtcheferie=findViewById(R.id.edtcheferie);
        edtavlocalite=findViewById(R.id.edtavlocalite);
        edtnumero=findViewById(R.id.edtnumero);
        ArrayAdapter<CharSequence> adaptertypeparent = ArrayAdapter.createFromResource(this, R.array.typeparent, android.R.layout.simple_spinner_item);
        adaptertypeparent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertypeparent.setAdapter(adaptertypeparent);
        btnenvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( edtnomp.getText().toString().equals("")|| edtpostnomp.getText().toString().equals("")|| edtprenomp.getText().toString().equals("")|| edtnomm.getText().toString().equals("")|| edtpostnomm.getText().toString().equals("")
                        || edtprenom.getText().toString().equals("")|| edtlieunaisp.getText().toString().equals("")|| edtlieunaism.getText().toString().equals("")|| edtagepere.getText().toString().equals("")
                      || edtagemere.getText().toString().equals("")|| edtnatpere.getText().toString().equals("")|| edtnatmere.getText().toString().equals("")|| spinnertypeparent.getSelectedItem().toString().equals("selectionnez type parent")|| edttelephonep.getText().toString().equals("")|| edttelephonem.getText().toString().equals("")
                || edtprovince.getText().toString().equals("")|| edtvilledietri.getText().toString().equals("")|| edtcomnuneterrioire.getText().toString().equals("")|| edtqualitetire.getText().toString().equals("")|| edtcheferie.getText().toString().equals("")
                || edtavlocalite.getText().toString().equals("")|| edtnumero.getText().toString().equals(""))

                    Toast.makeText(getApplicationContext(),"remplir les champs vide s'il vous plait",Toast.LENGTH_LONG).show();
                else{
                    EnregistrementParent enre=new EnregistrementParent();
                    enre.execute("");
                }

            }
        });
    }
    public class EnregistrementParent extends AsyncTask<String,String,String> {
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
            pbbar.setVisibility(View.GONE);
            mprogress.dismiss();
            ProgressDialogs.showDialog(EnreParentActivity.this, "success", r);
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
                        String query1 =("exec Sp_insererParent '"+RandomDigits(6)+"','"+edtnomp.getText().toString().trim().toUpperCase()+"','"+edtpostnomp.getText().toString().toUpperCase().trim()+"','"+edtprenomp.getText().toString().toUpperCase()+"','"+edtnomm.getText().toString().toUpperCase()+"','"+edtpostnomm.getText().toString().toUpperCase().trim()+"'," +
                                "'"+edtprenom.getText().toString().toUpperCase().trim()+"','"+edtprofessionp.getText().toString().toUpperCase().trim()+"','"+edtprofessionm.getText().toString().toUpperCase().trim()+"','"+edtlieunaisp.getText().toString().toUpperCase().trim()+"','"+edtlieunaism.getText().toString().toUpperCase().trim()+"','"+edtagepere.getText().toString().trim()+"'," +
                                "'"+edtagemere.getText().toString().trim()+"','"+edtnatpere.getText().toString().toUpperCase().trim()+"','"+edtnatmere.getText().toString().toUpperCase().trim()+"','"+spinnertypeparent.getSelectedItem().toString().toUpperCase().trim()+"','"+edttelephonep.getText().toString().toUpperCase().trim()+"','"+edttelephonem.getText().toString().toUpperCase().trim()+"'," +
                                "'"+edtprovince.getText().toString().toUpperCase().trim()+"','"+edtvilledietri.getText().toString().toUpperCase().trim()+"','"+edtcomnuneterrioire.getText().toString().toUpperCase().trim()+"','"+edtqualitetire.getText().toString().toUpperCase().trim()+"','"+edtcheferie.getText().toString().toUpperCase().trim()+"'," +
                                "'"+edtavlocalite.getText().toString().toUpperCase().trim()+"','"+edtnumero.getText().toString().toUpperCase().trim()+"'");
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
        edtnomp.setText("");
        edtpostnomp.setText("");
        edtprenom.setText("");
        edtnumero.setText("");
        edttelephonep.setText("");
    }
}
