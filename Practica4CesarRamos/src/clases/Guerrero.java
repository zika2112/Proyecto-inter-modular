package clases;

public class Guerrero extends Personaje implements Jugable{
	private final static int ATAQUE = 20;
	private final static int POCIONES = 2;
	private final static int DEFENSA = 10;
	private int pociones;

	public Guerrero(String nombre, int vida) {
		super(nombre, vida, ATAQUE, DEFENSA);
		this.pociones = POCIONES;
	}


	@Override
	public void resetear() {
		super.resetear();
		this.pociones = POCIONES;
	}
	@Override
	public void curar() {
		if (pociones > 0) {
			this.vida = this.vidaInicial;
			pociones--;
		}
	}
	@Override
	public String toString() {
		return super.toString() + "; Pociones : " + pociones;
	}


	public void recibirDanio(int i) {
		
		
	}
}
