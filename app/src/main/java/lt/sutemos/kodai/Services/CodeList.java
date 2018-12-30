package lt.sutemos.kodai.Services;

import java.util.ArrayList;
import java.util.List;

import lt.sutemos.kodai.Models.Irasas;

public class CodeList {
    private List<Irasas> irasai;
    private int id = 0;

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
            this.add("Bijūno " + i, i + " kart paperst", "");
        }
    }

    public List<Irasas> get(){
        return irasai;
    }

    public List<Irasas> find(String keyword){
        List<Irasas> returnList = new ArrayList<>();

        if (keyword== null) {
            return irasai;
        }
        if (keyword.isEmpty()) {
            return irasai;
        }

        keyword = keyword.toLowerCase();
        // return if something matches

        for (int i = 0; i < irasai.size(); i++){
            Irasas irasas = irasai.get(i);
            if (irasas.getAdresas().toLowerCase().contains(keyword) || irasas.getKodas().toLowerCase().contains(keyword)){
                returnList.add(irasas);
            }
        }
        return returnList;

    }

    public void add(String adresas, String kodas, String info){
        irasai.add(new Irasas(id++, adresas,kodas, info));
    }

    private int getPositionById(int id){
        for (int i = 0; i < irasai.size(); i++){
            Irasas irasas = irasai.get(i);
            if (irasas.getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public boolean delete(int id){
        int position = getPositionById(id);
        if (position <0) {
            return false;
        } else{
            return (irasai.remove(position) != null);
        }
    }

    public int size(){
        return irasai.size();
    }

    public boolean update(Irasas irasas){
        int position = getPositionById(irasas.getId());
        if (position <0) {
            return false;
        } else{
            irasai.set(position, irasas);
        }

        return true;
    }

}
