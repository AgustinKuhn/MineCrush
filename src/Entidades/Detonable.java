package Entidades;

/**
 * Define las operaciones esperables por sobre las entidades detonables.
 */
public interface Detonable {
	/**
	 * Detona la entidad que recibe el mensaje.
 	 * Notifica a la entidad gráfica del cambio de estado.
	 */
	public void detonar();
	
	public boolean detonada();
	
	public boolean matcheable();
	
	public boolean derribable();
}