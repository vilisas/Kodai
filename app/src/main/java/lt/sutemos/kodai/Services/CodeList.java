package lt.sutemos.kodai.Services;

import java.util.ArrayList;
import java.util.List;

import lt.sutemos.kodai.Model.Irasas;

public class CodeList {
    private List<Irasas> irasai;

    public CodeList(){
        irasai = new ArrayList<>();
        this.load();
    }

    /*
     * generates dummy codes for now
     */
    public void load(){

        irasai = new ArrayList<>();
        for (int i = 1; i<=25; i++){
            Irasas irasas = new Irasas(
                    "Bijūno " + i,
                    i + " kart paperst"
            );
            irasai.add(irasas);
        }
    }

    public List<Irasas> get(){
        return irasai;
    }

    public List<Irasas> find(String keyword){
        List<Irasas> returnList = new ArrayList<>();

        keyword = keyword.toLowerCase();
        if (keyword== null) {
            return irasai;
        }
        if (keyword.isEmpty()) {
            return irasai;
        }

        // return if something matches

        for (int i = 0; i < irasai.size(); i++){
            Irasas irasas = irasai.get(i);
            if (irasas.getAdresas().toLowerCase().contains(keyword) || irasas.getKodas().toLowerCase().contains(keyword)){
                returnList.add(irasas);
            }
        }
        return returnList;

    }

    public int size(){
        return irasai.size();
    }

}
