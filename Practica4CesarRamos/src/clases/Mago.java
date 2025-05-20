package clases;

public class Mago extends Personaje implements Jugable {

	private final static int ATAQUE_MAGIA = 20;
	private final static int ATAQUE_SIN_MAGIA = 5;
	private final static int DEFENSA = 5;
	private final static int MAGIA = 10;
	private int magia;

	public Mago(String nombre, int vida) {
		super(nombre, vida, ATAQUE_MAGIA, DEFENSA);
		this.magia = MAGIA;
	}

	@Override
	public void atacar(Personaje otro) {
		super.atacar(otro);
		if (magia > 0) {
			magia--;
		}
		if (magia == 0) {
			this.ataque = ATAQUE_SIN_MAGIA;
		}

	}

	@Override
	public void resetear() {
		super.resetear();
		this.magia = MAGIA;
		this.getAtaque = ATAQUE_MAGIA;
	}

	@Override
	public String toString() {
		return super.toString() + "; Magia : " + magia;
	}
}
