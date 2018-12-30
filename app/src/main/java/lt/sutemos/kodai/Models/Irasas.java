package lt.sutemos.kodai.Models;

public class Irasas {
    int id;
    private String adresas;
    private String kodas;
    private String info;

    public Irasas(String adresas, String kodas, String info) {
        this.id = -1;
        this.adresas = adresas;
        this.kodas = kodas;
        this.info = info;
    }


    public Irasas(int id, String adresas, String kodas, String info) {
        this(adresas, kodas, info);
        this.id = id;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
