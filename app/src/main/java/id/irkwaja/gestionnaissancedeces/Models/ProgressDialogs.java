package id.irkwaja.gestionnaissancedeces.Models;

import android.app.Activity;
import android.graphics.Color;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProgressDialogs {
    static SweetAlertDialog pDialog;
    private static boolean logout;

    public static void showDialog(final Activity activity, String type, String message){

        pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        if (type.equals("progress")){
            pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.setTitleText("");
        }else if (type.equals("success")){
            pDialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE);
            pDialog.setTitleText("REUSSIE");
        }
        else if (type.equals("update")){
            pDialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE);
            pDialog.setTitleText("location Mise à jour");
        }
        else if (type.equals("error")){
            pDialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
            pDialog.setTitleText("ECHEC...");
        }
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#3F4A68"));
        pDialog.setContentText(message);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (isLogout()){
                    activity.finish();
                }
                pDialog.dismiss();
            }
        });
    }

    public static boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean logout) {
        ProgressDialogs.logout = logout;
    }

    public static  void hideDialog(){
        pDialog.dismiss();
    }
}