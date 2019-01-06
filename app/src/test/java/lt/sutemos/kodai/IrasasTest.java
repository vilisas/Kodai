package lt.sutemos.kodai;

import org.junit.Test;

import lt.sutemos.kodai.Models.Irasas;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class IrasasTest {
    @Test
    public void iraso_testas(){
        Irasas irasas = new lt.sutemos.kodai.Models.Irasas(1,"adresas", "kodas","info");
        Irasas irasas2 = new lt.sutemos.kodai.Models.Irasas(2,"adresas", "kodas","info");
        Irasas irasas3 = new lt.sutemos.kodai.Models.Irasas(3,"adresas1", "kodas2","info3");
        assertNotNull(irasas);
        assertTrue(irasas.equals(new lt.sutemos.kodai.Models.Irasas("adresas", "kodas", "info")));
        assertTrue(irasas.equals(irasas2));
        assertTrue(!irasas.equals(irasas3));
        assertTrue(irasas.hashCode() == irasas2.hashCode());
        assertTrue(irasas.hashCode() != irasas3.hashCode());

    }

}
