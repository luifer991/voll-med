package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    
    @Autowired
    private PacienteRepository repository;
    
    @PostMapping
    @Transactional
    public void registra ( @RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente ) {
        repository.save(new Paciente(datosRegistroPaciente));
    }
    
    @GetMapping()
    public Page<DatosListadoPaciente> listar ( @PageableDefault( page = 0,size = 10, sort = { "nombre" } ) Pageable paginacion ) {
        return repository.findAll(paginacion).map(DatosListadoPaciente::new);
    }
    
    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DatosActualizacionPaciente datos) {
        var paciente = repository.getReferenceById(datos.id());
        paciente.atualizarInformacion(datos);
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public void remover(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.inactivar();
    }}
