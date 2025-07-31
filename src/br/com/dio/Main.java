package br.com.dio;

import br.com.dio.model.*;
import br.com.dio.util.BoardGenerator;
import static br.com.dio.util.BoardTemplate.BOARD_TEMPLATE;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Board board;
    private static GameStats gameStats;
    private static final int BOARD_LIMIT = 9;

    public static void main(String[] args) {
        showMainMenu();
    }

    private static void showMainMenu() {
        int option;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Iniciar novo jogo");
            System.out.println("2 - Sair");
            System.out.print("Escolha uma opção: ");
            
            option = scanner.nextInt();
            
            switch (option) {
                case 1 -> startNewGame();
                case 2 -> System.exit(0);
                default -> System.out.println("Opção inválida!");
            }
        } while (option != 2);
    }

    private static void startNewGame() {
        System.out.println("\nSelecione a dificuldade:");
        System.out.println("1 - Fácil");
        System.out.println("2 - Médio");
        System.out.println("3 - Difícil");
        System.out.print("Escolha: ");
        
        int difficultyChoice = scanner.nextInt();
        Difficulty difficulty = Difficulty.values()[difficultyChoice-1];
        
        board = new Board(BoardGenerator.generateBoard(difficulty));
        gameStats = new GameStats();
        gameStats.startTimer();
        System.out.println("\nNovo jogo iniciado!");
        showGameMenu();
    }

    private static void showGameMenu() {
        int option;
        do {
            System.out.println("\n=== MENU DO JOGO ===");
            System.out.println("1 - Colocar número");
            System.out.println("2 - Remover número");
            System.out.println("3 - Visualizar tabuleiro");
            System.out.println("4 - Verificar status");
            System.out.println("5 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            
            option = scanner.nextInt();
            
            switch (option) {
                case 1 -> inputNumber();
                case 2 -> removeNumber();
                case 3 -> showCurrentGame();
                case 4 -> showGameStatus();
                case 5 -> { return; }
                default -> System.out.println("Opção inválida!");
            }
        } while (option != 5);
    }

    private static void inputNumber() {
        System.out.println("Informe a coluna (0-8):");
        int col = scanner.nextInt();
        System.out.println("Informe a linha (0-8):");
        int row = scanner.nextInt();
        System.out.println("Informe o valor (1-9):");
        int value = scanner.nextInt();
        
        if (board.changeValue(col, row, value)) {
            System.out.println("Valor inserido com sucesso!");
        } else {
            System.out.println("Movimento inválido ou posição fixa!");
        }
    }

    private static void removeNumber() {
        System.out.println("Informe a coluna (0-8):");
        int col = scanner.nextInt();
        System.out.println("Informe a linha (0-8):");
        int row = scanner.nextInt();
        
        if (board.clearValue(col, row)) {
            System.out.println("Valor removido com sucesso!");
        } else {
            System.out.println("Não é possível remover valores fixos!");
        }
    }

    private static void showCurrentGame() {
        Object[] args = new Object[81];
        int index = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (int j = 0; j < BOARD_LIMIT; j++) {
                Space space = board.getSpaces().get(i).get(j);
                args[index++] = space.getActual() != null ? space.getActual() : " ";
            }
        }
        System.out.printf(BOARD_TEMPLATE + "%n", args);
    }

    private static void showGameStatus() {
        System.out.println("Status: " + board.getStatus().getLabel());
        System.out.println("Erros: " + (board.hasErrors() ? "Sim" : "Não"));
    }

    private static int runUntilGetValidNumber(int min, int max) {
        int num;
        do {
            num = scanner.nextInt();
            if (num < min || num > max) {
                System.out.printf("Digite um número entre %d e %d: ", min, max);
            }
        } while (num < min || num > max);
        return num;
    }
}