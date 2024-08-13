package Logica;

import java.util.HashMap;
import java.util.Map.Entry;

import Entidades.Entidad;
import GUI.Dominio;

public class Objetivo 
{
	protected int tamaño;
	protected HashMap<Integer,Integer> entidades;
	protected Dominio dominio;
	
	public Objetivo(Dominio dominio)
	{
		entidades = new HashMap<Integer,Integer>();
		this.dominio = dominio;
	}	
	
	public boolean isEmpty() 
	{
		return tamaño == 0;
	}
	
	public void agregarObjetivo(int color,int cantidad) {
		if(cantidad > 0) {
			entidades.put(color, cantidad);
			tamaño++;
		}
	}
	
	public void reducirCantidad(int color) 
	{
		if(entidades.get(color) != null) 
		{
			int cantidad = entidades.get(color);
			entidades.replace(color, --cantidad);
			if(cantidad < 1) 
			{
				entidades.remove(color);
				tamaño--;
			}
		}
	}
	
	public int cantidadObjetivos() {
		int cantidad = 0;
		for(int cantidades : entidades.values())
			cantidad += cantidades;
		return cantidad;
	}
	
	public String getObjetivos()	
	{
		String toReturn = "";
		for (Entry<Integer, Integer> entidad : entidades.entrySet())
		{
			toReturn+= "<html> &emsp;" + nombreObjetivo(entidad.getKey()) + " <img src='file:"+ imagenObjetivo(entidad.getKey())+"' width='25' height='25'/> " +" - " + entidad.getValue()  + "<br>";
		}
		return toReturn;
	}
	
	private String nombreObjetivo(int c) {
		String color = null;
		String nombre = dominio.nombreDelDominio();
		if(nombre == "Minecrush") 
		{
			switch(c) {
				case(1): color ="Esmeraldas ";
				break;
				case(2): color ="Hierros ";
				break;
				case(3): color ="Redstones ";
				break;
				case(4): color ="Oros ";
				break;
				case(5): color ="Lapislazulis ";
				break;
				case(6): color ="Diamantes ";
				break;
				case(7): color ="Obsidianas ";
				break;
				case(10): color ="Slimes ";
				break;
			}
		}
		if(nombre == "Mortalcrush")
		{
			System.out.println(c);
			switch(c) {
				case(1): color ="Verde ";
				break;
				case(2): color ="Blanco ";
				break;
				case(3): color ="Rojo";
				break;
				case(4): color ="Amarillo ";
				break;
				case(5): color ="Azul ";
				break;
				case(6): color ="Violeta";
				break;
				case(7): color ="Piedras";
				break;
				case(10): color ="Sangre";
				break;
			}
		}
		return color;
	}
	
	private String imagenObjetivo(int c)
	{
		String imagen = "src/";
		if(c > 0 && c <7)
			imagen += dominio.imagenesDeCaramelos() + c +".png";
		else
			if(c == 7)
				imagen += dominio.imagenesDeObsidiana()+ c +".png";
			else
				if(c == 10)
					imagen += dominio.imagenesDeSlime()+ c +".png";
		return imagen;
	}
}