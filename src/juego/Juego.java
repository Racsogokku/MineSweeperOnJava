package juego;

import java.util.Random;
import java.util.Scanner;
import java.util.StringJoiner;

public class Juego {
    private Celda[][] mapa;
    private int fils, cols, numBombas;
    private boolean haPerdido;

    public Juego (int fils, int cols, int numBombas) {
        this.fils = fils;
        this.cols = cols;
        haPerdido=false;
        prepararMapa(fils, cols, numBombas);
    }

    public void prepararMapa (int fils, int cols, int numBombas) {
        if (numBombas >= fils * cols) {
            throw new IllegalArgumentException("Demasiadas bombas para el tamaño del tablero");
        }
        mapa = new Celda[fils][cols];
        inicializarMapa();
        Random aleatorio = new Random();
        int randFil, randCol;
        while (this.numBombas < numBombas) {
            randFil = aleatorio.nextInt(fils);
            randCol = aleatorio.nextInt(cols);
            if (! mapa[randFil][randCol].tieneBomba()) {
                this.numBombas++;
                mapa[randFil][randCol].ponerBomba();
            }
        }
        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                mapa[i][j].setNumBombasCerca(calcBombasCerca(i,j));
            }
        }


    }
    private int minNumBombas(){
        int minimo=mapa[0][0].getNumBombasCerca();
        for(int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                if (mapa[i][j].getNumBombasCerca() < minimo) {
                    minimo = mapa[i][j].getNumBombasCerca();
                }
            }
        }
        return minimo;
    }
    public void jugar(){
        int filAJugar, colAJugar;
        Random random=new Random();
        int minimoBombas = minNumBombas();
        int intentos=0;
        // Elegir una celda aleatoria que no tenga bomba para empezar
        do {
            filAJugar = random.nextInt(fils);
            colAJugar= random.nextInt(cols);
            intentos++;
        }while(((mapa[filAJugar][colAJugar].tieneBomba()) || (mapa[filAJugar][colAJugar].getNumBombasCerca() > minimoBombas))&&(intentos<100));
        if(intentos==100){
            for (int i = 0; i < fils; i++) {
                for( int j = 0; j < cols; j++) {
                    if (mapa[i][j].getNumBombasCerca() == minimoBombas && !mapa[i][j].tieneBomba()) {
                        filAJugar = i;
                        colAJugar = j;

                    }
                }
            }
        }
        clicarCasilla(filAJugar, colAJugar);
        // Iniciar el juego
        try(Scanner scanner=new Scanner(System.in)) {
            while (! haPerdido) {
                mostrarMapa();
                if (numDescubiertas() == (fils * cols - numBombas)) {
                    throw new FinJuego("Has descubierto todas las celdas sin bombas. ¡Felicidades!");
                }
                System.out.println("Introduce fila y columna a descubrir (separadas por espacio): ");
                do {
                    filAJugar = scanner.nextInt();
                    colAJugar = scanner.nextInt();
                } while((!posValida(filAJugar, colAJugar)) || (mapa[filAJugar][colAJugar].isDescubierta()));
                clicarCasilla(filAJugar, colAJugar);
                if(haPerdido) {
                    throw new FinJuego("Has hecho clic en una bomba. Fin del juego.");
                }
            }
        }
    }
    private void clicarCasilla(int fils, int col){
        mapa[fils][col].setDescubierta();
        if(mapa[fils][col].tieneBomba()) {
            haPerdido = true;
        } else {
            if (mapa[fils][col].getNumBombasCerca() == 0) {
                // Si no hay bombas cerca, descubrimos las celdas adyacentes
                for (int i = fils - 1; i <= fils + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (posValida(i, j) && !mapa[i][j].isDescubierta()) {
                            clicarCasilla(i, j);
                        }
                    }
                }
            }
        }
    }

    private int numDescubiertas() {
        int contador = 0;
        for (Celda[] fila : mapa) {
            for (Celda celda : fila) {
                if (celda.isDescubierta()) {
                    contador++;
                }
            }
        }
        return contador;
    }
    private void inicializarMapa () {
        for (int i = 0; i < fils; i++) {
            for (int j = 0; j < cols; j++) {
                mapa[i][j] = new Celda();
            }
        }
    }

    private int calcBombasCerca (int fil, int col) {
        int contador = 0;
        for (int i = fil - 1; i <= fil + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if ((! ((fil == i) && (col == j))) && (posValida(i, j))) {
                    contador += (mapa[i][j].tieneBomba() ? 1 : 0);
                }
            }
        }
        return contador;
    }

    private boolean posValida (int fil, int col) {
        return fil >= 0 && col >= 0 && fil < fils && col < cols;
    }


    private void mostrarMapa () {
        StringBuilder mapaVisual = new StringBuilder();
        int numFila=0;
        int maxNumNumeros = numNumeros(cols-1);
        int maxNumNumerosFil = numNumeros(fils-1);
        mapaVisual.append("  ").append(" ".repeat(2));
        for(int i = 0; i < cols; i++) {
            mapaVisual.append(i).append(" ".repeat(maxNumNumeros-numNumeros(i)+2));
        }
        mapaVisual.append("\n");
        for (Celda[] fila : mapa) {
            mapaVisual.append(numFila).append(" ".repeat(3-numNumeros(numFila)));
            StringJoiner stringJoiner=new StringJoiner(" ".repeat(maxNumNumeros+1),"|","|");
            for (Celda celda : fila) {
                if (celda.isDescubierta()) {
                    if (celda.tieneBomba()) {
                        haPerdido = true;
                        stringJoiner.add("X");
                    } else {
                        stringJoiner.add(Integer.toString(celda.getNumBombasCerca()));
                    }
                } else {
                    stringJoiner.add("■");
                }
            }
            mapaVisual.append(stringJoiner.toString()).append("\n");
            numFila++;
        }
        System.out.println(mapaVisual);
    }
    private int numNumeros(int num){
        int contador = 0;
        if(num!=0) {
            while (num > 0) {
                num /= 10;
                contador++;
            }
        }else contador =1;
        return contador;
    }
}
