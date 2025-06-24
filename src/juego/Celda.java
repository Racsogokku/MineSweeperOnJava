package juego;

public class Celda {
    private boolean descubierta;
    private boolean tieneBomba;
    private int numBombasCerca;
    public Celda(){
        descubierta=false;
        numBombasCerca=0;
    }
    public boolean isDescubierta(){
        return descubierta;
    }
    public int getNumBombasCerca () {
        return numBombasCerca;
    }
    public boolean tieneBomba(){
        return tieneBomba;
    }
    public void setNumBombasCerca(int numBombasCerca){
        this.numBombasCerca=numBombasCerca;
    }
    public void ponerBomba(){
        tieneBomba=true;
    }
    public void setDescubierta(){
        descubierta=true;
    }
}
