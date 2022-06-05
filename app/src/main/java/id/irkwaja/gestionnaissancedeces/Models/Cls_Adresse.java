package id.irkwaja.gestionnaissancedeces.Models;

public class Cls_Adresse {
    public Cls_Adresse(){}
    private Long idAdresse;
    private String province;
    private String ville_district;
    private String commune_territoire;
    private String quartier;
    private String chefferie;
    private String avenue_loclite;
    private String resiencectuele;
    private int numeroParcelle;

    public Long getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(Long idAdresse) {
        this.idAdresse = idAdresse;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getVille_district() {
        return ville_district;
    }

    public void setVille_district(String ville_district) {
        this.ville_district = ville_district;
    }



    public String getQuartier() {
        return quartier;
    }
    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }
    public int getNumeroParcelle() {
        return numeroParcelle;
    }

    public void setNumeroParcelle(int numeroParcelle) {
        this.numeroParcelle = numeroParcelle;
    }

    public String getCommune_territoire() {
        return commune_territoire;
    }

    public void setCommune_territoire(String commune_territoire) {
        this.commune_territoire = commune_territoire;
    }

    public String getChefferie() {
        return chefferie;
    }

    public void setChefferie(String chefferie) {
        this.chefferie = chefferie;
    }

    public String getAvenue_loclite() {
        return avenue_loclite;
    }

    public void setAvenue_loclite(String avenue_loclite) {
        this.avenue_loclite = avenue_loclite;
    }

    public String getResiencectuele() {
        return resiencectuele;
    }

    public void setResiencectuele(String resiencectuele) {
        this.resiencectuele = resiencectuele;
    }
}
