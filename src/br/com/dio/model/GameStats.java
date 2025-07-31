package br.com.dio.model;

import java.time.Duration;
import java.time.Instant;

public class GameStats {
    private Instant startTime;
    private int hintsUsed;
    private int mistakesMade;
    
    public void startTimer() {
        startTime = Instant.now();
    }
    
    public Duration getElapsedTime() {
        return Duration.between(startTime, Instant.now());
    }
    
    public void incrementHintsUsed() {
        hintsUsed++;
    }
    
    public void incrementMistakes() {
        mistakesMade++;
    }
    
    public int getHintsUsed() {
        return hintsUsed;
    }
    
    public int getMistakesMade() {
        return mistakesMade;
    }
    
    public int calculateScore() {
        Duration elapsed = getElapsedTime();
        long minutes = elapsed.toMinutes();
        return (int) (1000 - (minutes * 10) - (hintsUsed * 50) - (mistakesMade * 20));
    }
}