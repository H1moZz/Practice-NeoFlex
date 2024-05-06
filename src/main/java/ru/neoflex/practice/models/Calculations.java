package ru.neoflex.practice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Setter;

@Setter
@Entity
@Table(name  = "calculations")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"id","sign","result"},allowSetters = true, allowGetters = true)
public class Calculations {

    @Id
    private Integer id;
    private Integer number_1;
    private Character sign;
    private Integer number_2;
    private Integer result;


    public Calculations(Integer number_1, Integer number_2) {
        this.number_1 = number_1;
        this.number_2 = number_2;
    }

    public Calculations() {
        
    }

    @Column(name = "number_1", nullable = false)
     public Integer getNumber_1() {
        return number_1;
    }

    @Column (name = "number_2", nullable = false)
    public Integer getNumber_2() {
        return number_2;
    }

    @Column (name = "result", nullable = false)
    public Integer getResult() {
        return result;
    }
    @Column (name = "sign", nullable = false)
    public Character getSign() {
        return this.sign;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }
}
