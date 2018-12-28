package lt.sutemos.kodai.Models;

public class Irasas {
    int id;
    private String adresas;
    private String kodas;


    public Irasas(String adresas, String kodas) {
        this.adresas = adresas;
        this.kodas = kodas;
    }

    public Irasas(int id, String adresas, String kodas) {
        this.id = id;
        this.adresas = adresas;
        this.kodas = kodas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
