package Logica;

public class Puntuacion 
{
	protected int puntaje;
	
	public Puntuacion()
	{
		puntaje = 0;
	}
	
	public int getPuntaje()
	{
		return puntaje;
	}
	
	public void sumarPuntos(int puntaje)
	{
		this.puntaje += puntaje;
	}
}