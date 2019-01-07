package lt.sutemos.kodai.services;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CodeListTest {
    CodeListOld codeList;
    Code irasas;

    @Before
    public void setUp() throws Exception {
        codeList = new CodeListOld();
//        List<Code> irasai = new ArrayList<>();
        irasas = new Code(1,"adresas","kodas","info");
    }

    @Test
    public void setIrasai() {
        List<Code> irasai = new ArrayList<>();
        assertNotNull(codeList);
        CodeListOld cl = new CodeListOld();
        cl.setIrasai(irasai);
        assertNotEquals(codeList,cl);


    }

    @Test
    public void getIrasai() {
        codeList = new CodeListOld();
        codeList.add("adresas","kodas","info");
        assertNotNull(codeList.getIrasai());
    }


    @Test
    public void find() {
        codeList = new CodeListOld();
        codeList.add(irasas);
        codeList.add("bbb","ccc","DDD");
        List<Code> rezultatai =codeList.find(irasas.getAdresas());
        assertNotNull(rezultatai);
        assertEquals(1, rezultatai.size());
        assertEquals(0, codeList.find("belekoks tekstas").size());
        assertEquals(2,codeList.find(null).size());
    }

    @Test
    public void add() {
        codeList = new CodeListOld();
        codeList.add("aa","bb","cc");
        assertEquals(1,codeList.size());
        codeList.add(irasas);
        assertEquals(2, codeList.size());
    }

    @Test
    public void delete() {
        codeList = new CodeListOld();
        codeList.add(irasas);
        assertTrue(codeList.delete(
                codeList.find(
                        irasas.getAdresas()
                ).get(0).getId()));
        assertEquals(0,codeList.size());

    }

    @Test
    public void size() {
        codeList = new CodeListOld();
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
        codeList = new CodeListOld();
        List<Code> irasai = new ArrayList<>();
        irasai.add(irasas);
        irasai.add(new Code("adresas1","kodas1","info1"));


        codeList.add(irasas);
        codeList.add("adresas1","kodas1","info1");
        // ieskome raktazodzio "adresas1", grazina 1 rezultata, jo laukelyje "info" turi but "info1"
        assertEquals("info1", codeList.find("adresas1").get(0).getInfo());
        assertEquals(2,codeList.size());
        codeList.merge(irasai);
        assertEquals(2,codeList.size());
        irasai.add(new Code("adresas1","kodas1","info1"));
        irasai = new ArrayList<>();
        codeList.merge(irasai);
        assertEquals(2,codeList.size());
        irasai.add(new Code("adresas1","kodas1","info666"));
        codeList.merge(irasai);
        irasai = new ArrayList<>();
        irasai.add(new Code("visiskai naujas adresas1","kodas222","info333"));
        codeList.merge(irasai);
        assertEquals(3,codeList.size());
        assertEquals("info333", codeList.find("visiskai naujas adresas1").get(0).getInfo());




    }
}