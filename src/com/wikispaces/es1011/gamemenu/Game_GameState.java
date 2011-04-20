package com.wikispaces.es1011.gamemenu;

public class Game_GameState {

    private static State state;
    private int lives;
    private int score;

    public enum State {

        LOSE, READY, RUNNING, WIN, PAUSE;
    }

    public Game_GameState() {
        this.lives = 3;
        this.score = 0;
    }

    public void setScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    public static void setState(State state) {
        Game_GameState.state = state;
    }

    public static State getState() {
        return Game_GameState.state;
    }

    public int getLives() {
        return this.lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
