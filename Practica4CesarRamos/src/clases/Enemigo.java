package clases;

import java.util.Random;

public class Enemigo extends Personaje {

	public Enemigo() {

	}

	public void iniciarEnemigo(String nombre) {
		
		Random random = new Random();
		this.nombre = nombre;
		this.vida = random.nextInt(81) + 20;
		this.vidaInicial = this.vida;
		this.ataque = random.nextInt(15) + 6;
		this.defensa = random.nextInt(3) + 1;
	}
}
