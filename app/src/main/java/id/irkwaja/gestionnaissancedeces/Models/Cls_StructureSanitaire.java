package id.irkwaja.gestionnaissancedeces.Models;

public class Cls_StructureSanitaire {
    public Cls_StructureSanitaire(){}
    private Long idStruct;
    private String designationStruct;
    private String sigleStructure;
    private String contactStruct;
    private String nomRespo;
    private String postnomRespo;
    private String prenomRespo;
    private String qualiteTitreRespo;
    private String motDepasse;
    private Long matricule;

    public Long getIdStruct() {
        return idStruct;
    }

    public void setIdStruct(Long idStruct) {
        this.idStruct = idStruct;
    }

    public String getDesignationStruct() {
        return designationStruct;
    }

    public void setDesignationStruct(String designationStruct) {
        this.designationStruct = designationStruct;
    }

    public String getSigleStructure() {
        return sigleStructure;
    }

    public void setSigleStructure(String sigleStructure) {
        this.sigleStructure = sigleStructure;
    }

    public String getContactStruct() {
        return contactStruct;
    }

    public void setContactStruct(String contactStruct) {
        this.contactStruct = contactStruct;
    }

    public String getNomRespo() {
        return nomRespo;
    }

    public void setNomRespo(String nomRespo) {
        this.nomRespo = nomRespo;
    }

    public String getPostnomRespo() {
        return postnomRespo;
    }

    public void setPostnomRespo(String postnomRespo) {
        this.postnomRespo = postnomRespo;
    }

    public String getPrenomRespo() {
        return prenomRespo;
    }

    public void setPrenomRespo(String prenomRespo) {
        this.prenomRespo = prenomRespo;
    }

    public String getQualiteTitreRespo() {
        return qualiteTitreRespo;
    }

    public void setQualiteTitreRespo(String qualiteTitreRespo) {
        this.qualiteTitreRespo = qualiteTitreRespo;
    }

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
}
