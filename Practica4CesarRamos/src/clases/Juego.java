package clases;

import java.util.ArrayList;
import java.util.Random;

public class Juego {
	private final static String[] nombresEnemigos = { "Fenrrir, el lobo", "Derek, el enano", "Custreco, el goblin",
			"Sparda, el caballero demonio", "Maliketh, la hoja negra", "Odón, de la Hermandad de la Sal",
			"Afilaor, Centinela del Esméril" };

	private ArrayList<Enemigo> enemigos;
	private Personaje jugador;
	private int nRondas;
	private int ronda;

	public Juego() {
		enemigos = new ArrayList<>();
	}

	public static String getNombreAleatorio() {
		Random random = new Random();
		int indice = random.nextInt(nombresEnemigos.length);
		return nombresEnemigos[indice];
	}

	public void iniciarJuego() {
		enemigos.clear();

		for (int i = 0; i < nRondas; i++) {
			Enemigo enemigo = new Enemigo();
			enemigo.iniciarEnemigo(getNombreAleatorio());
			enemigos.add(enemigo);
		}

		ronda = 0;
	}

	public Enemigo getSiguiente() {
		return enemigos.get(0);
	}

	public boolean terminarRonda() {
		if (enemigos.get(0).muerto()) {
			enemigos.remove(0);
			ronda++;
			return true;
		}
		return false;
	}

	public void nuevoGuerrero(String nombre) {
		Random random = new Random();
		int vida = random.nextInt(101) + 100;
		jugador = new Guerrero(nombre, vida);
	}

	public void nuevoMago(String nombre) {
		Random random = new Random();
		int vida = random.nextInt(101) + 50;
		jugador = new Mago(nombre, vida);
	}

	public boolean finalJuego() {
		return enemigos.isEmpty();
	}

	public Personaje getJugador() {
		return jugador;
	}

	public void setJugador(Personaje jugador) {
		this.jugador = jugador;
	}

	public int getNRondas() {
		return nRondas;
	}

	public void setNRondas(int nRondas) {
		this.nRondas = nRondas;
	}

	public int getRonda() {
		return ronda;
	}

	public void setRonda(int ronda) {
		this.ronda = ronda;
	}
}