package clases;

public abstract class  Personaje {
	protected int vida;
	protected int vidaInicial;
	protected int ataque;
	protected int defensa;
	protected String nombre;

	public Personaje() {

	}

	public Personaje(String nombre, int vida, int ataque, int defensa) {
		this.nombre = nombre;
		this.vida = vida;
		this.vidaInicial = vida;
		this.ataque = ataque;
		this.defensa = defensa;
	}

	public void atacar(Personaje otro) {
		int daño = (this.ataque - otro.defensa);

		if (daño < 0) {
			daño = 0;
		}
		otro.vida -= daño;

		if (otro.vida < 0) {
			otro.vida = 0;
		}
	}

	public void resetear() {
		this.vida = this.vidaInicial;
	}

	public boolean muerto() {
		return vida <= 0;
	}

	@Override
	public String toString() {
		return nombre + "=> Vida: " + vida + "/" + vidaInicial;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getVidaInicial() {
		return vidaInicial;
	}

	public void setVidaInicial(int vidaInicial) {
		this.vidaInicial = vidaInicial;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public int getDefensa() {
		return defensa;
	}

	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	

}