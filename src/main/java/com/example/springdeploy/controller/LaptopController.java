package com.example.springdeploy.controller;

import com.example.springdeploy.entites.Laptop;
import com.example.springdeploy.repository.LaptopRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {

    private final Logger log = LoggerFactory.getLogger(LaptopController.class);
    private LaptopRepository laptopRepository;
   

    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

 
    @GetMapping("/api/laptops")
    @Operation(summary = "Recuperar todas las laptops")
    public List<Laptop> findAll(){
        // recuperar y devolver las laptops de base de datos
        return laptopRepository.findAll();
    }


    /**
     * Request
     * Response
     * @param id
     * @return
     */
    // buscar un solo laptop en base de datos segun su id
    @GetMapping("/api/laptops/{id}")
    @Operation(summary = "Buscar laptop por id")
    public ResponseEntity<Laptop> findOneById(@Parameter(description = "Clave primaria tipo long") @PathVariable Long id){

        Optional<Laptop> laptopOpt = laptopRepository.findById(id); // 3456546456435
        // opcion 1
        if(laptopOpt.isPresent())
            return ResponseEntity.ok(laptopOpt.get());
        else
            return ResponseEntity.notFound().build();

        // opcion 2
//        return laptopOpt.orElse(null);
        // return laptopOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }



    // crear una nueva laptop en base de datos
    @PostMapping("/api/laptops")
    @Operation(summary = "Crear una laptop, en caso de existir contruye el error")
    public ResponseEntity<Object> create(@RequestBody Laptop laptop){
        // guardar la nueva laptop recibida por parámetro en la base de datos

        if(laptop.getId() != null){ // quiere decir que existe el id y por tanto no es una creación
            log.warn("trying to create a book with id");
            System.out.println("trying to create a book with id");
            return ResponseEntity.badRequest().build();
        }
        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result); // el libro devuelto tiene una clave primaria
    }

    @PutMapping("/api/laptops")
    @Operation(summary = "Actualiza una laptop, en caso de no existir contruye el error")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop){
        if(laptop.getId() == null ){ // si no tiene id quiere decir que sí es una creación
            log.warn("Trying to update a non existent book");
            return ResponseEntity.badRequest().build();
        }
        if(!laptopRepository.existsById(laptop.getId())){
            log.warn("Trying to update a non existent book");
            return ResponseEntity.notFound().build();
        }

        // El proceso de actualización
        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result); // el libro devuelto tiene una clave primaria
    }

    @DeleteMapping("/api/laptops/{id}")
    @Operation(summary = "Eliminar laptop por id")
    public ResponseEntity<Laptop> delete(@PathVariable Long id){

        if(!laptopRepository.existsById(id)){
            log.warn("Trying to delete a non existent book");
            return ResponseEntity.notFound().build();
        }

        laptopRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/laptops")
    @Operation(summary = "Eliminar todas las laptops")
    public ResponseEntity<Laptop> deleteAll(){
        log.info("REST Request for delete all books");
        laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
