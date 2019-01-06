package lt.sutemos.kodai.Services;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import lt.sutemos.kodai.Models.Irasas;

import static org.junit.Assert.*;

public class CodeListTest {
    CodeList codeList;
    Irasas irasas;

    @Before
    public void setUp() throws Exception {
        codeList = new CodeList();
//        List<Irasas> irasai = new ArrayList<>();
        irasas = new Irasas(1,"adresas","kodas","info");
    }

    @Test
    public void setIrasai() {
        List<Irasas> irasai = new ArrayList<>();
        assertNotNull(codeList);
        CodeList cl = new CodeList();
        cl.setIrasai(irasai);
        assertNotEquals(codeList,cl);


    }

    @Test
    public void getIrasai() {
        codeList = new CodeList();
        codeList.add("adresas","kodas","info");
        assertNotNull(codeList.getIrasai());
    }


    @Test
    public void find() {
        codeList = new CodeList();
        codeList.add(irasas);
        codeList.add("bbb","ccc","DDD");
        List<Irasas> rezultatai =codeList.find(irasas.getAdresas());
        assertNotNull(rezultatai);
        assertEquals(1, rezultatai.size());
        assertEquals(0, codeList.find("belekoks tekstas").size());
        assertEquals(2,codeList.find(null).size());
    }

    @Test
    public void add() {
        codeList = new CodeList();
        codeList.add("aa","bb","cc");
        assertEquals(1,codeList.size());
        codeList.add(irasas);
        assertEquals(2, codeList.size());
    }

    @Test
    public void delete() {
        codeList = new CodeList();
        codeList.add(irasas);
        assertTrue(codeList.delete(
                codeList.find(
                        irasas.getAdresas()
                ).get(0).getId()));
        assertEquals(0,codeList.size());

    }

    @Test
    public void size() {
        codeList = new CodeList();
        assertEquals(0,codeList.size());
        codeList.add(irasas);
        codeList.add(irasas);
        assertEquals(2,codeList.size());
        codeList.delete(0);
        assertEquals(1,codeList.size());

    }

    @Test
    public void update() {
    }

    @Test
    /*
        prie esamo saraso prideda kita, jeigu laukelis "adresas" sutampa, tai rezultatus uzraso
        ant virsaus.
     */
    public void merge(){
        codeList = new CodeList();
        List<Irasas> irasai = new ArrayList<>();
        irasai.add(irasas);
        irasai.add(new Irasas("adresas1","kodas1","info1"));


        codeList.add(irasas);
        codeList.add("adresas1","kodas1","info1");
        // ieskome raktazodzio "adresas1", grazina 1 rezultata, jo laukelyje "info" turi but "info1"
        assertEquals("info1", codeList.find("adresas1").get(0).getInfo());
        assertEquals(2,codeList.size());
        codeList.merge(irasai);
        assertEquals(2,codeList.size());
        irasai.add(new Irasas("adresas1","kodas1","info1"));
        irasai = new ArrayList<>();
        codeList.merge(irasai);
        assertEquals(2,codeList.size());
        irasai.add(new Irasas("adresas1","kodas1","info666"));
        codeList.merge(irasai);
        irasai = new ArrayList<>();
        irasai.add(new Irasas("visiskai naujas adresas1","kodas222","info333"));
        codeList.merge(irasai);
        assertEquals(3,codeList.size());
        assertEquals("info333", codeList.find("visiskai naujas adresas1").get(0).getInfo());




    }
}