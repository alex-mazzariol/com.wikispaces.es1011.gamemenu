package com.wikispaces.es1011.gamemenu;

public class game_GameState {

    private State state;
    public int lives;
    private int score;

    public enum State {

        LOSE, READY, RUNNING, WIN, PAUSE;
    }

    public game_GameState() {
        this.lives = 3;
    }

    public void setScore(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public int getLives() {
        return this.lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
