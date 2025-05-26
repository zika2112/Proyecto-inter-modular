package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import clases.Mago;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;



public class MagoTest {

    static Mago mago;

    @BeforeAll
    static void initAll() {
        System.out.println("Inicializando instancia de Mago");
        mago = new Mago("Merlín", 100);
    }

    @BeforeEach
    void init() {
        System.out.println("Reseteando estado del Mago");
        mago.resetear();
    }

    @Test
    void testToStringContieneNombreYMagia() {
        String descripcion = mago.toString();
        assertTrue(descripcion.contains("Merlín"), "toString debe contener el nombre");
        assertTrue(descripcion.contains("Magia : 10"), "Debe comenzar con 10 puntos de magia");
    }

    @Test
    void testCurarReduceMagia() {
        String antes = mago.toString();
        mago.curar();
        String despues = mago.toString();

        assertTrue(antes.contains("Magia : 10"), "Antes de curar debe tener 10 de magia");
        assertTrue(despues.contains("Magia : 9"), "Después de curar debe tener 9 de magia");
    }

    @Test
    void testCurarAgotaMagia() {
        for (int i = 0; i < 10; i++) {
            mago.curar();
        }
        String descripcion = mago.toString();
        assertTrue(descripcion.contains("Magia : 0"), "Después de curar 10 veces, la magia debe ser 0");
    }

    @Test
    void testResetearDespuesDeMagiaAgotada() {
        for (int i = 0; i < 10; i++) {
            mago.curar(); // Agotar magia
        }

        String sinMagia = mago.toString();
        assertTrue(sinMagia.contains("Magia : 0"), "Debe tener 0 de magia antes de resetear");

        mago.resetear();

        String restaurado = mago.toString();
        assertTrue(restaurado.contains("Magia : 10"), "Resetear debe restaurar la magia a 10");
    }
}