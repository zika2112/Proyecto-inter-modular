package pruebas;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import clases.Enemigo;

class EnemigoTest {
    
    private Enemigo enemigo;
    
    @BeforeEach
    void setUp() {
        enemigo = new Enemigo();
    }
    
    @Test
    void testConstructorEnemigo() {
        // Verificar que el constructor crea un objeto Enemigo válido
        assertNotNull(enemigo, "El enemigo no debe ser null");
    }
    
    @Test
    void testIniciarEnemigoConNombre() {
        // Probar inicialización con nombre válido
        String nombreEsperado = "Lady Maria";
        enemigo.iniciarEnemigo(nombreEsperado);
        
        assertEquals(nombreEsperado, enemigo.getNombre(), 
            "El nombre del enemigo debe ser el asignado");
    }
    
    @Test
    void testIniciarEnemigoConNombreVacio() {
        // Probar inicialización con nombre vacío
        String nombreVacio = "";
        enemigo.iniciarEnemigo(nombreVacio);
        
        assertEquals(nombreVacio, enemigo.getNombre(), 
            "El enemigo debe aceptar nombres vacíos");
    }
    
    @Test
    void testIniciarEnemigoConNombreNull() {
        // Probar inicialización con nombre null
        enemigo.iniciarEnemigo(null);
        
        assertNull(enemigo.getNombre(), 
            "El enemigo debe aceptar nombre null");
    }
    
    @Test
    void testRangoVidaEnemigo() {
        // Probar múltiples inicializaciones para verificar el rango de vida (20-100)
        for (int i = 0; i < 50; i++) {
            enemigo = new Enemigo();
            enemigo.iniciarEnemigo("TestEnemigo");
            
            int vida = enemigo.getVida();
            assertTrue(vida >= 20 && vida <= 100, 
                "La vida debe estar entre 20 y 100, pero fue: " + vida);
        }
    }
    
    @Test
    void testRangoAtaqueEnemigo() {
        // Probar múltiples inicializaciones para verificar el rango de ataque (6-20)
        for (int i = 0; i < 50; i++) {
            enemigo = new Enemigo();
            enemigo.iniciarEnemigo("TestEnemigo");
            
            int ataque = enemigo.getAtaque();
            assertTrue(ataque >= 6 && ataque <= 20, 
                "El ataque debe estar entre 6 y 20, pero fue: " + ataque);
        }
    }
    
    @Test
    void testRangoDefensaEnemigo() {
        // Probar múltiples inicializaciones para verificar el rango de defensa (1-3)
        for (int i = 0; i < 50; i++) {
            enemigo = new Enemigo();
            enemigo.iniciarEnemigo("TestEnemigo");
            
            int defensa = enemigo.getDefensa();
            assertTrue(defensa >= 1 && defensa <= 3, 
                "La defensa debe estar entre 1 y 3, pero fue: " + defensa);
        }
    }
    
    @Test
    void testVidaInicialIgualAVidaActual() {
        // Verificar que la vida inicial se establece igual a la vida actual
        enemigo.iniciarEnemigo("TestEnemigo");
        
        assertEquals(enemigo.getVida(), enemigo.getVidaInicial(), 
            "La vida inicial debe ser igual a la vida actual tras la inicialización");
    }
    
    @Test
    void testMultiplesInicializacionesGeneranValoresDiferentes() {
        // Verificar que múltiples inicializaciones pueden generar valores diferentes
        enemigo.iniciarEnemigo("Enemigo1");
        int vida1 = enemigo.getVida();
        int ataque1 = enemigo.getAtaque();
        int defensa1 = enemigo.getDefensa();
        
        boolean hayDiferencias = false;
        
        // Probar hasta 20 inicializaciones para encontrar diferencias
        for (int i = 0; i < 20; i++) {
            enemigo = new Enemigo();
            enemigo.iniciarEnemigo("Enemigo2");
            
            if (enemigo.getVida() != vida1 || 
                enemigo.getAtaque() != ataque1 || 
                enemigo.getDefensa() != defensa1) {
                hayDiferencias = true;
                break;
            }
        }
        
        assertTrue(hayDiferencias, 
            "Las múltiples inicializaciones deberían generar al menos algunos valores diferentes");
    }
    
    @Test
    void testEstadoCompletoTrasInicializacion() {
        // Verificar que todos los atributos se establecen correctamente
        String nombre = "Malenia Goddes of Rot";
        enemigo.iniciarEnemigo(nombre);
        
        assertAll("Verificar estado completo del enemigo tras inicialización",
            () -> assertEquals(nombre, enemigo.getNombre(), "Nombre incorrecto"),
            () -> assertTrue(enemigo.getVida() > 0, "La vida debe ser positiva"),
            () -> assertTrue(enemigo.getVidaInicial() > 0, "La vida inicial debe ser positiva"),
            () -> assertTrue(enemigo.getAtaque() > 0, "El ataque debe ser positivo"),
            () -> assertTrue(enemigo.getDefensa() > 0, "La defensa debe ser positiva"),
            () -> assertEquals(enemigo.getVida(), enemigo.getVidaInicial(), 
                "Vida actual e inicial deben ser iguales")
        );
    }
    
    @Test
    void testReinicializacionEnemigo() {
        // Probar que se puede reinicializar el mismo objeto enemigo
        enemigo.iniciarEnemigo("Slave Knight Gael");
        String primerNombre = enemigo.getNombre();
        
        enemigo.iniciarEnemigo("Ludwig");
        String segundoNombre = enemigo.getNombre();
        
        assertNotEquals(primerNombre, segundoNombre, 
            "El nombre debe cambiar tras la reinicialización");
        
        // Verificar que las estadísticas siguen siendo válidas tras reinicialización
        assertTrue(enemigo.getVida() >= 20 && enemigo.getVida() <= 100, 
            "La vida tras reinicialización debe seguir en el rango válido");
        assertTrue(enemigo.getAtaque() >= 6 && enemigo.getAtaque() <= 20, 
            "El ataque tras reinicialización debe seguir en el rango válido");
        assertTrue(enemigo.getDefensa() >= 1 && enemigo.getDefensa() <= 3, 
            "La defensa tras reinicialización debe seguir en el rango válido");
    }
    
    @Test
    void testVariosNombresDelJuego() {
        // Probar con varios nombres específicos que usa el juego
        String[] nombres = {
            "Lady Maria", 
            "Malenia Goddes of Rot", 
            "Slave Knight Gael",
            "Sparda the devil knight", 
            "Maliketh the Black Blade", 
            "Ludwig",
            "Afilaor, Centinela del Esméril"
        };
        
        for (String nombre : nombres) {
            enemigo = new Enemigo();
            enemigo.iniciarEnemigo(nombre);
            
            assertEquals(nombre, enemigo.getNombre(), 
                "El enemigo debe aceptar correctamente el nombre: " + nombre);
            assertTrue(enemigo.getVida() > 0, "El enemigo debe tener vida válida");
            assertTrue(enemigo.getAtaque() > 0, "El enemigo debe tener ataque válido");
            assertTrue(enemigo.getDefensa() > 0, "El enemigo debe tener defensa válida");
        }
    }
}
