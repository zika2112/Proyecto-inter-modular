package pruebas;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clases.Guerrero;

import static org.junit.jupiter.api.Assertions.*;

public class GuerreroTest {

    static Guerrero guerrero;

    @BeforeAll
    static void initAll() {
        System.out.println("Ejecutando @BeforeAll: Creando instancia de Guerrero");
        guerrero = new Guerrero("Conan", 100);
    }

    @BeforeEach
    void init() {
        System.out.println("Ejecutando @BeforeEach: Reseteando Guerrero");
        guerrero.resetear();
    }

    @Test
    void testConstructorInicializaCorrectamente() {
        assertEquals("Conan", guerrero.getNombre());
        assertEquals(100, guerrero.getVida());
        assertTrue(guerrero.toString().contains("Pociones : 2"));
    }

    @Test
    void testCurarRestauraVidaYReducePociones() {
        guerrero.recibirDanio(50);
        assertEquals(50, guerrero.getVida());

        guerrero.curar();

        assertEquals(100, guerrero.getVida(), "La vida debe restaurarse a 100");
        assertTrue(guerrero.toString().contains("Pociones : 1"), "Debe haber 1 poción restante");
    }

    @Test
    void testCurarSinPocionesNoCura() {
        // Usamos las dos pociones
        guerrero.recibirDanio(30);
        guerrero.curar();
        guerrero.recibirDanio(40);
        guerrero.curar();

        // Intento de curar sin pociones
        guerrero.recibirDanio(50);
        guerrero.curar();

        assertEquals(10, guerrero.getVida(), "La vida no debe cambiar si no hay pociones");
        assertTrue(guerrero.toString().contains("Pociones : 0"), "No debe quedar ninguna poción");
    }

    @Test
    void testResetearRestauraPociones() {
        guerrero.recibirDanio(50);
        guerrero.curar(); // ahora tiene 1 poción

        guerrero.resetear();

        assertTrue(guerrero.toString().contains("Pociones : 2"), "Debe tener 2 pociones tras resetear");
        assertEquals(100, guerrero.getVida(), "La vida debe estar restaurada tras resetear");
    }
}

