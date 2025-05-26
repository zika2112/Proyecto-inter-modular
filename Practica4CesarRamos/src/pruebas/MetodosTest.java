package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import clases.Juego;
import programa.Metodos;

class MetodosTest {
    
    private Metodos metodos;
    @TempDir
    Path tempDir;
    private File archivoTemporal;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    
    @BeforeEach
    void setUp() throws Exception {
        metodos = new Metodos();
        
        // Crear archivo temporal para pruebas
        archivoTemporal = tempDir.resolve("Mejor Puntuacion.txt").toFile();
        
        // Usar reflexión para cambiar el fichero por uno temporal
        Field ficheroField = Metodos.class.getDeclaredField("fichero");
        ficheroField.setAccessible(true);
        ficheroField.set(metodos, archivoTemporal);
        
        // Capturar salida de consola
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }
    
    @AfterEach
    void tearDown() {
        // Restaurar salida de consola
        System.setOut(originalOut);
    }
    
    @Test
    void testConstructorMetodos() {
        // Verificar que el constructor inicializa correctamente
        assertNotNull(metodos, "El objeto Metodos no debe ser null");
        
        // Verificar que se inicializan los objetos internos usando reflexión
        try {
            Field scannerField = Metodos.class.getDeclaredField("scanner");
            scannerField.setAccessible(true);
            assertNotNull(scannerField.get(metodos), "El Scanner debe estar inicializado");
            
            Field juegoField = Metodos.class.getDeclaredField("juego");
            juegoField.setAccessible(true);
            assertNotNull(juegoField.get(metodos), "El Juego debe estar inicializado");
        } catch (Exception e) {
            fail("Error accediendo a los campos privados: " + e.getMessage());
        }
    }
    
    @Test
    void testLeerRecordArchivoNoExiste() {
        // Asegurarse de que el archivo no existe
        if (archivoTemporal.exists()) {
            archivoTemporal.delete();
        }
        
        int record = metodos.leerRecord();
        
        assertEquals(0, record, "Debe devolver 0 cuando el archivo no existe");
        
        String output = outputStream.toString();
        assertTrue(output.contains("Archivo de récords no encontrado"), 
            "Debe mostrar mensaje de archivo no encontrado");
    }
    
    @Test
    void testLeerRecordArchivoVacio() throws IOException {
        // Crear archivo vacío
        archivoTemporal.createNewFile();
        
        int record = metodos.leerRecord();
        
        assertEquals(0, record, "Debe devolver 0 cuando el archivo está vacío");
    }
    
    @Test
    void testLeerRecordArchivoConDatos() throws IOException {
        // Crear archivo con datos de récord
        try (PrintWriter writer = new PrintWriter(archivoTemporal)) {
            writer.println("New Record");
            writer.println(". Nombre Jugador/a: TestPlayer");
            writer.println(". Rondas Ganadas:");
            writer.println("15");
        }
        
        int record = metodos.leerRecord();
        
        assertEquals(15, record, "Debe leer correctamente el récord del archivo");
    }
    
    @Test
    void testLeerRecordArchivoFormatoIncorrecto() throws IOException {
        // Crear archivo con formato incorrecto
        try (PrintWriter writer = new PrintWriter(archivoTemporal)) {
            writer.println("Datos incorrectos");
            writer.println("Sin formato esperado");
        }
        
        int record = metodos.leerRecord();
        
        assertEquals(0, record, "Debe devolver 0 cuando el formato es incorrecto");
    }
    
   
    
    @Test
    void testRegistroDeRecordNoSuperaRecord() throws Exception {
        // Preparar archivo con récord alto
        try (PrintWriter writer = new PrintWriter(archivoTemporal)) {
            writer.println("New Record");
            writer.println(". Nombre Jugador/a: ProPlayer");
            writer.println(". Rondas Ganadas:");
            writer.println("20");
        }
        
        // Configurar juego con ronda menor
        Field juegoField = Metodos.class.getDeclaredField("juego");
        juegoField.setAccessible(true);
        Juego juego = (Juego) juegoField.get(metodos);
        juego.setRonda(15); // Menor que 20
        
        metodos.registroDeRecord();
        
        String output = outputStream.toString();
        assertTrue(output.contains("No superaste el récord actual"), 
            "Debe mostrar mensaje de no superar récord");
        assertTrue(output.contains("20 rondas"), 
            "Debe mostrar el récord actual");
    }
    
   
    
    
    
    @Test
    void testLeerRecordSinLineaDespuesDeRondasGanadas() throws IOException {
        // Crear archivo donde falta la línea del número
        try (PrintWriter writer = new PrintWriter(archivoTemporal)) {
            writer.println("New Record");
            writer.println(". Nombre Jugador/a: TestPlayer");
            writer.println(". Rondas Ganadas:");
            // No hay línea con el número
        }
        
        int record = metodos.leerRecord();
        
        assertEquals(0, record, "Debe devolver 0 cuando falta el número de rondas");
    }
    
    @Test
    void testArchivoConMultiplesRegistros() throws IOException {
        // Crear archivo con múltiples entradas (solo debe leer la primera)
        try (PrintWriter writer = new PrintWriter(archivoTemporal)) {
            writer.println("New Record");
            writer.println(". Nombre Jugador/a: Player1");
            writer.println(". Rondas Ganadas:");
            writer.println("25");
            writer.println("Other data");
            writer.println(". Rondas Ganadas:");
            writer.println("30");
        }
        
        int record = metodos.leerRecord();
        
        assertEquals(25, record, "Debe leer solo el primer registro de rondas ganadas");
    }
    
    @Test
    void testInicializacionAtributos() throws Exception {
        // Verificar que los atributos se inicializan correctamente
        Field recordPartidaField = Metodos.class.getDeclaredField("recordPartida");
        recordPartidaField.setAccessible(true);
        int recordPartida = (int) recordPartidaField.get(metodos);
        
        Field recordActualField = Metodos.class.getDeclaredField("recordActual");
        recordActualField.setAccessible(true);
        int recordActual = (int) recordActualField.get(metodos);
        
        assertEquals(0, recordPartida, "recordPartida debe inicializarse en 0");
        assertEquals(0, recordActual, "recordActual debe inicializarse en 0");
    }
}
