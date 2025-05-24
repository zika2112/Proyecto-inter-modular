package pruebas;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clases.Guerrero;

import static org.junit.jupiter.api.Assertions.*;

public class GuerreroTest {

    static Guerrero guerrero;

    @BeforeAll
    static void setupAll() {
        System.out.println("Inicializando pruebas...");
        guerrero = new Guerrero("Conan", 100);
    }

    @BeforeEach
    void setup() {
        System.out.println("Reseteando estado del Guerrero...");
        guerrero.resetear();
    }

    @Test
    void testToStringContieneNombreYPociones() {
        String descripcion = guerrero.toString();
        assertTrue(descripcion.contains("Conan"), "toString debe contener el nombre");
        assertTrue(descripcion.contains("Pociones : 2"), "Debe iniciar con 2 pociones");
    }

    @Test
    void testCurarReducePociones() {
        String antes = guerrero.toString();
        guerrero.curar();
        String despues = guerrero.toString();

        assertTrue(antes.contains("Pociones : 2"), "Antes de curar debe tener 2 pociones");
        assertTrue(despues.contains("Pociones : 1"), "Después de curar debe tener 1 poción");
    }

    @Test
    void testCurarAgotaPociones() {
        guerrero.curar(); 
        String descripcion = guerrero.toString();
        assertTrue(descripcion.contains("Pociones : 0"), "Debe quedarse sin pociones después de curar dos veces");
    }

    @Test
    void testResetearRestauraPociones() {
        guerrero.curar(); 
        guerrero.resetear(); 

        String descripcion = guerrero.toString();
        assertTrue(descripcion.contains("Pociones : 2"), "Resetear debe restaurar las pociones a 2");
    }
}
