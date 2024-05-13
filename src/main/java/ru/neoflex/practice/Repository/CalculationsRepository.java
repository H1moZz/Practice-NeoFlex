package ru.neoflex.practice.Repository;

import org.springframework.stereotype.Repository;
import ru.neoflex.practice.models.Calculations;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CalculationsRepository {

    // File path to store calculations data
    private final String FILE_PATH = "calculations.txt";

    // Method to save a calculation to the file
    public Calculations save(Calculations calculation) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Write calculation details to the file
            bufferedWriter.write(calculation.getId().toString());
            bufferedWriter.newLine();
            bufferedWriter.write(calculation.getNumber_1().toString());
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(calculation.getSign()));
            bufferedWriter.newLine();
            bufferedWriter.write(calculation.getNumber_2().toString());
            bufferedWriter.newLine();
            bufferedWriter.write(calculation.getResult().toString());
            bufferedWriter.newLine();
        } catch (IOException e) {}
        return calculation;
    }

    // Method to retrieve all calculations from the file
    public List<Calculations> findAll() {
        List<Calculations> calculationsList = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            int i = 0;
            int id = 0;
            int number1=0;
            char sign = ' ';
            int number2=0;
            int result=0;
            String line;
            // Read each line from the file
            while((line = bufferedReader.readLine()) != null ){
                // Parse data based on the line number
                switch(i){
                    case 0:
                        id = Integer.parseInt(line);
                        break;
                    case 1:
                        number1 = Integer.parseInt(line);
                        break;
                    case 2:
                        sign = line.charAt(0);
                        break;
                    case 3:
                        number2 = Integer.parseInt(line);
                        break;
                    case 4:
                        result = Integer.parseInt(line);
                        // Add the calculation to the list
                        calculationsList.add(new Calculations(id, number1, sign, number2, result));
                        i = -1; // Reset the counter to start a new iteration
                        break;
                }
                i++;
            }
        } catch (IOException e) {}
        return calculationsList;
    }

    // Method to find a calculation by its ID
    public Optional<Calculations> findById(int id) {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            int i = -1;
            int id_of_ex = 0;
            int number1=0;
            char sign = ' ';
            int number2=0;
            int result=0;
            int id_line = 1;
            int currentLineNumber = 0;
            // Read each line from the file
            while ((line = bufferedReader.readLine()) != null) {
                currentLineNumber++;
                // Check if the current line number matches the ID line
                if (currentLineNumber == id_line) {
                    // Check if the ID matches the target ID
                    if (id == Integer.parseInt(line)){
                        i = 5;
                    }
                    id_line += 5; // Move to the next ID line
                }
                // Parse data based on the state of the iteration
                if(i==5)
                {
                    id_of_ex = Integer.parseInt(line);
                }
                if(i==4){
                    number1 = Integer.parseInt(line);
                }
                if(i==3){
                    sign = line.charAt(0);
                }
                if(i==2){
                    number2 = Integer.parseInt(line);
                }
                if(i==1){
                    result = Integer.parseInt(line);
                }
                if (i==5 || i ==4 || i==3 || i==2 || i==1){
                    i--;
                }
                if (i == 0)
                    return Optional.of(new Calculations(id_of_ex, number1, sign, number2, result));
            }
        } catch (IOException e) {}
        return Optional.empty();
    }

    // Method to delete a calculation by its ID
    public void delete(Integer id) {
        boolean deleted = false;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH + ".tmp"))) {
            String line;
            int i = -1;
            int id_line = 1;
            int currentLineNumber = 0;
            while ((line = bufferedReader.readLine()) != null) {
                currentLineNumber++;
                // Check if the current line number matches the ID line
                if (currentLineNumber == id_line) {
                    // Check if the ID matches the target ID
                    if (id == Integer.parseInt(line)){
                        i = 5;
                    }
                    id_line += 5; // Move to the next ID line
                }
                // Write lines to a temporary file, excluding the line with the target ID
                if (i == -1) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
                // Update the state of the iteration
                if (i==5 || i ==4 || i==3 || i==2 || i==1){
                    i--;
                    if (i == 0){
                        deleted = true;
                        i = -1;
                    }
                }
            }
        } catch (IOException e) {}
        // Rename the temporary file to replace the original file
        File originalFile = new File(FILE_PATH);
        File tempFile = new File(FILE_PATH + ".tmp");
        if (deleted) {
            originalFile.delete();
            tempFile.renameTo(originalFile);
        }
    }
}
