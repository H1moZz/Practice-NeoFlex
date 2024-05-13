package ru.neoflex.practice.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.practice.Repository.CalculationsRepository;
import ru.neoflex.practice.exceptions.ResourceNotFoundException;
import ru.neoflex.practice.models.Calculations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OpenAPIDefinition (
        info = @Info(
                title = "Клиент серверное приложение калькулятора",
                version = "1.0",
                description = "Эта программа созданна для прохождения Производствнной практики в NeoFlex",
                contact = @Contact(
                        name = "Даниил",
                        email = "shabanovdan11l@mail.ru"
                )
        )
)
@RestController
public class CalcController {

    @Autowired
    private CalculationsRepository calculationsRepository;

    @GetMapping("/plus/{a}/{b}")
    public int Addition(@PathVariable int a, @PathVariable int b) {
        Calculations calculation = new Calculations();
        calculation.setNumber_1(a);
        calculation.setNumber_2(b);
        calculation.setSign('+');
        calculation.setResult(a + b);
        calculationsRepository.save(calculation);
        return calculation.getResult();
    }

    @GetMapping("/minus/{a}/{b}")
    public int Subtraction(@PathVariable int a, @PathVariable int b) {
        Calculations calculation = new Calculations();
        calculation.setNumber_1(a);
        calculation.setNumber_2(b);
        calculation.setSign('-');
        calculation.setResult(a - b);
        calculationsRepository.save(calculation);
        return calculation.getResult();
    }

    @GetMapping("/calculations")
    public List<Calculations> get_all_calculations() {
        return calculationsRepository.findAll();
    }

    @GetMapping("/calculations/{id}")
    public ResponseEntity<Calculations> getCalculation_by_id(@PathVariable(value = "id") int id)
            throws ResourceNotFoundException {
        Calculations calculation;
        calculation = calculationsRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Expression not found with this ID :" + id));
        return ResponseEntity.ok().body(calculation);
    }

    @PutMapping("/calculation/{id}")
    public ResponseEntity<Calculations> update_Calculation(@PathVariable(value = "id") int id,@RequestBody Calculations CalcDetails) throws ResourceNotFoundException {
        Calculations calculation = calculationsRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Expression not found with this ID :\" + id)"));

        calculation.setNumber_1(CalcDetails.getNumber_1());
        calculation.setNumber_2(CalcDetails.getNumber_2());
        if (calculation.getSign()== '+')
            calculation.setResult(CalcDetails.getNumber_1() + CalcDetails.getNumber_2());

        else if (calculation.getSign()== '-')
            calculation.setResult(CalcDetails.getNumber_1() - CalcDetails.getNumber_2());

        calculationsRepository.delete(id);
        final Calculations updatedCalculation = calculationsRepository.save(calculation);
        return ResponseEntity.ok(updatedCalculation);
    }
    @DeleteMapping("/calculation/{id}")
    public Map< String, Boolean > deleteExpression(@PathVariable(value = "id") int id)
            throws ResourceNotFoundException {
        Calculations calculation = calculationsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expression not found for this id :" + id));
        calculationsRepository.delete(calculation.getId());
        Map <String, Boolean> response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}


