package GUI;

public class Dominio {

    private String name;

    //responsabilidad insertar n valido
    public Dominio(String n){
        name = n;
    }

    public String nombreDelDominio(){
      return name;
    }

	  public String fondoDelSelectorDeNivel(){
		  return "/imagenes/"+ name +"/fondos/selector.png";
    }


    public String imagenesDeCaramelos(){
    	return "/imagenes/"+name+"/caramelos/";
    }
    
    public String imagenesDeCohete_Horizontal()	
    {
    	return "/imagenes/"+name+"/potenciadores/Horizontal/";
    }
    
    public String imagenesDeCohete_Vertical()	
    {
    	return "/imagenes/"+name+"/potenciadores/Vertical/";
    }
    
    public String imagenesDeEnvuelto()
    {
    	return "/imagenes/"+name+"/potenciadores/Envuelto/";
    }
    public String imagenesDeObsidiana()
    {
    	return "/imagenes/"+name+"/especiales/obsidiana/";
    }
    
    public String imagenesDeCreeper()
    {
    	return "/imagenes/"+name+"/especiales/creeper/";
    }
    
    public String imagenesDeTdp1()
    {
    	return "/imagenes/"+name+"/potenciadores/tdp1/";
    }
    public String imagenesDeSlime()
    {
    	return "/imagenes/"+name+"/especiales/slime/";
    }
    public String fondoDeNivel(int nivel){
    	 return "/imagenes/"+ name +"/fondos/nivel-"+nivel+".jpg";
    }

    public String fondoDelRanking(){
    	 return "/imagenes/"+ name +"/fondos/ranking.png";
	}
}
