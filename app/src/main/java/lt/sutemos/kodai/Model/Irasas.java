package lt.sutemos.kodai.Model;

public class Irasas {
    private String adresas;
    private String kodas;


    public Irasas(String adresas, String kodas) {
        this.adresas = adresas;
        this.kodas = kodas;
    }

    public String getAdresas() {
        return adresas;
    }

    public void setAdresas(String adresas) {
        this.adresas = adresas;
    }

    public String getKodas() {
        return kodas;
    }

    public void setKodas(String kodas) {
        this.kodas = kodas;
    }
}
