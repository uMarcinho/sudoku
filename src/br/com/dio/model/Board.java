package br.com.dio.model;

import static br.com.dio.model.GameStatusEnum.*;
import java.util.Collection;
import java.util.List;

public class Board {
    private final List<List<Space>> spaces;
    private final static int BOARD_LIMIT = 9;

    public Board(final List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getStatus() {
        if (spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && s.getActual() != null)) {
            return NON_STARTED;
        }
        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> s.getActual() == null) ? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors() {
        if (getStatus() == NON_STARTED) {
            return false;
        }
        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(s -> s.getActual() != null && !s.getActual().equals(s.getExpected()));
    }

    public boolean isValidMove(int col, int row, int value) {
        // Verifica linha
        for (int i = 0; i < BOARD_LIMIT; i++) {
            if (spaces.get(col).get(i).getActual() != null && 
                spaces.get(col).get(i).getActual() == value && i != row) {
                return false;
            }
        }
        
        // Verifica coluna
        for (int i = 0; i < BOARD_LIMIT; i++) {
            if (spaces.get(i).get(row).getActual() != null && 
                spaces.get(i).get(row).getActual() == value && i != col) {
                return false;
            }
        }
        
        // Verifica quadrante 3x3
        int quadrantCol = col / 3 * 3;
        int quadrantRow = row / 3 * 3;
        
        for (int i = quadrantCol; i < quadrantCol + 3; i++) {
            for (int j = quadrantRow; j < quadrantRow + 3; j++) {
                if (spaces.get(i).get(j).getActual() != null && 
                    spaces.get(i).get(j).getActual() == value && 
                    !(i == col && j == row)) {
                    return false;
                }
            }
        }
        
        return true;
    }

    public boolean changeValue(final int col, final int row, final int value) {
        var space = spaces.get(col).get(row);
        if (space.isFixed()) {
            return false;
        }
        
        if (!isValidMove(col, row, value)) {
            return false;
        }

        space.setActual(value);
        return true;
    }

    public boolean clearValue(final int col, final int row) {
        var space = spaces.get(col).get(row);
        if (space.isFixed()) {
            return false;
        }

        space.clearSpace();
        return true;
    }

    public void reset() {
        spaces.forEach(c -> c.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished() {
        return !hasErrors() && getStatus().equals(COMPLETE);
    }
}