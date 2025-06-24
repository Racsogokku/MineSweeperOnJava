import juego.FinJuego;

import java.util.Scanner;

public class MainJuego {
    public static void main (String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Welcome to Minesweeper!");
        System.out.println("Please enter the number of rows, columns, and bombs (e.g., 4 4 1): ");
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int bombs = scanner.nextInt();
        juego.Juego juego = new juego.Juego(rows, cols, bombs);
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
