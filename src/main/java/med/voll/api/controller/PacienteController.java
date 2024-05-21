package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    
    @Autowired
    private PacienteRepository repository;
    
    @PostMapping
    @Transactional
    public ResponseEntity registrar( @RequestBody @Valid DatosRegistroPaciente datos, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente(datos);
        repository.save(paciente);
        
        URI url = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosDetalladoPaciente(paciente));
    }
    
    @GetMapping
    public ResponseEntity<Page<DatosListadoPaciente>> listar( @PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        return ResponseEntity.ok(repository.findAll(paginacion).map(DatosListadoPaciente::new));
    }
    
    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizacionPaciente datos) {
        var paciente = repository.getReferenceById(datos.id());
        paciente.atualizarInformacion(datos);
        
        return ResponseEntity.ok(new DatosDetalladoPaciente(paciente));
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.inactivar();
        
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalladoPaciente(paciente));
    }
}
