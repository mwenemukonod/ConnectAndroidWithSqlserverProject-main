package id.irkwaja.gestionnaissancedeces.Models;

public class Cls_Agent {
    public Cls_Agent(){}
    private Long matricule;
    private String nomAgent;
    private String postnomAgent;
    private String prenomAgent;
    private String fonction;
    private String telephone;
    private String motDepasse;

    public String getMotDepasse() {
        return motDepasse;
    }

    public void setMotDepasse(String motDepasse) {
        this.motDepasse = motDepasse;
    }

    public Long getMatricule() {
        return matricule;
    }

    public void setMatricule(Long matricule) {
        this.matricule = matricule;
    }

    public String getNomAgent() {
        return nomAgent;
    }

    public void setNomAgent(String nomAgent) {
        this.nomAgent = nomAgent;
    }

    public String getPostnomAgent() {
        return postnomAgent;
    }

    public void setPostnomAgent(String postnomAgent) {
        this.postnomAgent = postnomAgent;
    }

    public String getPrenomAgent() {
        return prenomAgent;
    }

    public void setPrenomAgent(String prenomAgent) {
        this.prenomAgent = prenomAgent;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
