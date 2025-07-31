package br.com.dio.model;

public enum Difficulty {
    EASY("Fácil", 30),
    MEDIUM("Médio", 45), 
    HARD("Difícil", 60);
    
    private final String label;
    private final int fixedCells;
    
    Difficulty(String label, int fixedCells) {
        this.label = label;
        this.fixedCells = fixedCells;
    }
    
    public String getLabel() {
        return label;
    }
    
    public int getFixedCells() {
        return fixedCells;
    }
}