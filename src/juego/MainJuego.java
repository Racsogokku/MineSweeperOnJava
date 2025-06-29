package juego;

import java.util.Scanner;

public class MainJuego {
    public static void main (String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Welcome to Minesweeper!");
        Juego juego = null;
        int fils, cols, numBombas;
        do {
            System.out.print("Please enter the number of rows, columns, and bombs (e.g., 4 4 1): ");
            fils = scanner.nextInt();
            cols= scanner.nextInt();
            numBombas = scanner.nextInt();
        } while (fils <= 0 || cols <= 0 || numBombas <= 0 || numBombas >= fils * cols);
        juego = new Juego(fils, cols, numBombas);
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
