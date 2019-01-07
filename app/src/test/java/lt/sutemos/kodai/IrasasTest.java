package lt.sutemos.kodai;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class IrasasTest {
    @Test
    public void iraso_testas(){
        Code irasas = new Code(1,"adresas", "kodas","info");
        Code irasas2 = new Code(2,"adresas", "kodas","info");
        Code irasas3 = new Code(3,"adresas1", "kodas2","info3");
        assertNotNull(irasas);
        assertTrue(irasas.equals(new Code("adresas", "kodas", "info")));
        assertTrue(irasas.equals(irasas2));
        assertTrue(!irasas.equals(irasas3));
        assertTrue(irasas.hashCode() == irasas2.hashCode());
        assertTrue(irasas.hashCode() != irasas3.hashCode());

    }

}
