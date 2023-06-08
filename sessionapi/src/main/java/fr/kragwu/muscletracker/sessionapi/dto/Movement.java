package fr.kragwu.muscletracker.sessionapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Movement {
    String name;
    Integer repetition;
    Integer weight;
    String idSession;

    public Movement(String name, Integer repetition, Integer weight, String idSession) {
        super();
        this.name = name;
        this.weight = weight;
        this.repetition = repetition;
        this.idSession = idSession;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public void setRepetition(Integer repetition) {
        this.repetition = repetition;
    }
    public Integer getRepetition() {
        return this.repetition;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public Integer getWeight() {
        return this.weight;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }
    public String getIdSession() {
        return this.idSession;
    }

    @Override
    public String toString() {
        return "Movement(name = "+ this.name + 
            ",weight = "+ this.weight + 
            ",repetition = "+ this.repetition + 
            ",idSession = "+ this.idSession + ")";
    }
}
