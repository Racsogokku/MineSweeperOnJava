import juego.FinJuego;

public class MainJuego {
    public static void main (String[] args) {
        juego.Juego juego = new juego.Juego(4, 4, 1);
        try {
            juego.jugar();
        } catch (FinJuego e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        } finally {
            System.out.println("Fin del juego.");
        }
    }
}
