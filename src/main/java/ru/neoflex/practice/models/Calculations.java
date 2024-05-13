package ru.neoflex.practice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Setter
@Entity
@Table(name  = "calculations")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"id","sign","result","file_PATH"},allowSetters = true, allowGetters = true)
public class Calculations {

    @Id
    private Integer id;
    private Integer number_1;
    private Character sign;
    private Integer number_2;
    private Integer result;

    private String FILE_PATH = "calculations.txt";

    public Calculations(Integer number_1, Character op, Integer number_2, Integer result) {
        this.number_1 = number_1;
        this.sign = op;
        this.number_2 = number_2;
        this.result = result;
    }

    public Calculations(Integer id,Integer number_1, Character op, Integer number_2, Integer result) {
        this.id = id;
        this.number_1 = number_1;
        this.sign = op;
        this.number_2 = number_2;
        this.result = result;
    }

    public Calculations() {
        setId();
        this.id += 1;
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

    public void setId () {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))){
            String line;
            this.id = 0;
            int id_line = 1;
            int currentLineNumber = 0;
            while ((line = bufferedReader.readLine()) != null) {
                currentLineNumber++;
                if (currentLineNumber == id_line) {
                    if (Integer.parseInt(line) > this.id)
                        this.id = Integer.parseInt(line);
                    id_line += 5;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Id
    public Integer getId() {
        return id;
    }
}
