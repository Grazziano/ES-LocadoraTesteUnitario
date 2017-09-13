package local.entities;

import local.entities.Usuario;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Angelo
 */
public class FilmeTest {

    public FilmeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Verifica se o método equals da classe Usuário foi implementado
     * corretamente
     *
     * @Author Angelo Luz
     * @Date 12/09/2017
     */
    @Test
    public void deveConsiderarFilmesIguais() {
        Usuario user1 = new Usuario("Angelo");
        Usuario user2 = new Usuario("Angelo");
        assertTrue(user1.equals(user2));
    }
}
