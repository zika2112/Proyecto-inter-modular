package programa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import clases.Enemigo;
import clases.Juego;
import clases.Jugable;

public class Metodos {
	private Scanner scanner;
	private Juego juego;
	File fichero = new File("Mejor Puntuacion.txt");
	int recordPartida = 0;
	int recordActual = 0;

	public Metodos() {
		scanner = new Scanner(System.in);
		juego = new Juego();
	}

	public void iniciarJuego() {
		boolean jugarOtraVez = true;

		while (jugarOtraVez) {
			iniciarPartida();

			while (!juego.getJugador().muerto() && !juego.finalJuego()) {
				mostrarEstadoJuego();
				realizarTurno();

				if (juego.terminarRonda()) {
					System.out.println("Random soul obtenida");
				}
			}

			finalizarPartida();
			jugarOtraVez = preguntarJugarOtraVez();
		}
	}
	
	public void registroDeRecord() {
		int recordAnterior = leerRecord();
		int recordNuevo = juego.getRonda();
		if (recordNuevo >= recordAnterior) {
			try (PrintWriter escritor = new PrintWriter(fichero)) {
				escritor.println("New Record");
				escritor.println(". Nombre Jugador/a: " + juego.getJugador().getNombre());
				escritor.println(". Rondas Ganadas:");
				escritor.println(recordNuevo);
				System.out.println("NUEVO RECORD!! Sal a tocar cesped jugador:" + juego.getJugador().getNombre()
						+ " Las rondas del nuevo record son: " + juego.getRonda());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No superaste el récord actual de " + recordAnterior + " rondas.");
		}
	}

	public int leerRecord() {
		int record = 0;

		if (!fichero.exists()) {
			System.out.println("Archivo de récords no encontrado. Comenzando con récord inicial de 0.");
			return record;
		}

		try (Scanner lectorRondas = new Scanner(fichero)) {
			while (lectorRondas.hasNextLine()) {
				String linea = lectorRondas.nextLine();
				if (linea.contains("Rondas Ganadas:")) {
					if (lectorRondas.hasNextLine()) {
						String numeroTexto = lectorRondas.nextLine().trim();
						record = Integer.parseInt(numeroTexto);
					}
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return record;
	}

	private void iniciarPartida() {
		System.out.println("Bienvenido al RandomSouls:");
		System.out.print("¿Cuántas rondas quieres jugar? ");
		int rondas = scanner.nextInt();
		scanner.nextLine();

		juego.setNRondas(rondas);

		System.out.print("Introduce tu nombre: ");
		String nombre = scanner.nextLine();

		System.out.println("Elige tu clase:");
		System.out.println("1. Mago");
		System.out.println("2. Guerrero");
		System.out.print("Elige (1, 2): ");
		int opcion = scanner.nextInt();
		scanner.nextLine();

		if (opcion == 1) {
			juego.nuevoMago(nombre);
		} else {
			juego.nuevoGuerrero(nombre);
		}

		juego.iniciarJuego();
	}

	private void mostrarEstadoJuego() {

		System.out.println("Ronda: " + (juego.getRonda() + 1) + "/" + juego.getNRondas());
		System.out.println("Estás luchando contra: " + juego.getSiguiente());
		System.out.println("Eres: " + juego.getJugador());
	}

	private void realizarTurno() {
		System.out.println("Acciones:");
		System.out.println("1. Atacar");
		System.out.println("2. Curar");
		System.out.print("Elige: ");
		int opcion = scanner.nextInt();
		scanner.nextLine();

		Enemigo enemigo = juego.getSiguiente();

		if (opcion == 1) {
			System.out.println(juego.getJugador().getNombre() + " ataca a " + enemigo.getNombre());
			juego.getJugador().atacar(enemigo);
		} else {
			System.out.println(juego.getJugador().getNombre() + " se cura");
			((Jugable) juego.getJugador()).curar();

		}

		if (!enemigo.muerto()) {
			System.out.println(enemigo.getNombre() + " ataca a " + juego.getJugador().getNombre());
			juego.getSiguiente().atacar(juego.getJugador());
		}

	}

	private void finalizarPartida() {
		if (juego.getJugador().muerto()) {
			System.out.println("Has perdido ,consejo del juego: Get good");
			registroDeRecord();
			
		} else {
			System.out.println("Felicidades has eliminado a todos los enemigos");
			registroDeRecord();
			
		}
	}

	private boolean preguntarJugarOtraVez() {
		String respuesta;

		while (true) {
			System.out.print("¿Volver a jugar? (s/n): ");
			respuesta = scanner.nextLine().toLowerCase().trim();

			if (respuesta.equals("s")) {
				return true;
			} else if (respuesta.equals("n")) {
				System.out.println("Gracias por jugar");
				return false;
			} else {
				System.out.println("Por favor, introduce 's' para sí o 'n' para no.");
			}
		}

	}
}
