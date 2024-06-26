package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
    
    @Autowired
    private MedicoRepository medicoRepository;
    
    @PostMapping
    public ResponseEntity<DatosRepuestaMedico> registrarMedicos ( @RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                                                  UriComponentsBuilder uriComponentsBuilder ) {
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRepuestaMedico datosRepuestaMedico = new DatosRepuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        
        URI url = uriComponentsBuilder.path("/medicos/{id}")
                .buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRepuestaMedico);
    }
    
    @GetMapping()
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos ( @PageableDefault(page = 0, size = 10, sort = { "nombre" })
                                                                         Pageable paginacion ) {
//        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }
    
    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico ( @RequestBody @Valid DatosActualizarMedico datosActualizarMedico ) {
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRepuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(),medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())));
    }
    
   // DELETE logico
    @DeleteMapping("/{id}")
    @Transactional
//    @Secured("ROLE_ADMIN") para que un admin pueda eliminar medicos, limitar accesos
    public ResponseEntity eliminarMedico ( @PathVariable Long  id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DatosRepuestaMedico> retornaDatosMedico ( @PathVariable Long  id) {
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRepuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedico);
    }
    
    // DELETE en base de datos
//    public void wliminarMedico ( @PathVariable Long  id) {
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }
}
