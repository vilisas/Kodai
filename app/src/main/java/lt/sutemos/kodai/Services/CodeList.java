package lt.sutemos.kodai.Services;

import java.util.ArrayList;
import java.util.List;

import lt.sutemos.kodai.Models.Irasas;
import lt.sutemos.kodai.Models.KodaiViewModel;

public class CodeList {
    private List<Irasas> irasai;
    private int id = 0;

    public CodeList(){
        irasai = new ArrayList<>();
    }


    public void setIrasai(List<Irasas> irasai) {
        this.irasai = irasai;
    }

    public List<Irasas> getIrasai() {
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

    /**
     * Add address, code and info. Automatically generate ID
     * @param adresas
     * @param kodas
     * @param info
     */
    public void add(String adresas, String kodas, String info){
        irasai.add(new Irasas(id++, adresas,kodas, info));
    }

    /**
     * add entry and generate ID
     * @param irasas
     */
    public void add(Irasas irasas){
        this.add(irasas.getAdresas(),irasas.getKodas(),irasas.getInfo());
    }

    /**
     * adds list and automatically generates ID
     * @param sarasas
     */
    public void add(List<Irasas> sarasas) {
        for (Irasas rasas : sarasas) {
            this.add(rasas);
        }
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

    /**
     * merges list, if values "adresas" are equal, then overwrite them, else append
     * @param sarasas
     */
    public void merge(List<Irasas> sarasas){
        if (sarasas == null){
            return;
        }
        for (Irasas i :
                sarasas) {
            int position = irasai.indexOf(i);
            if (position >=0){
                int originalId = irasai.get(position).getId();
                i.setId(originalId);
                irasai.set(position,i);
            } else {
                irasai.add(i);
            }
        }

    }

}
