package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/medicos")
public class MedicoController {
    
    @Autowired
    private MedicoRepository medicoRepository;
    
    @PostMapping
    public void registrarMedicos ( @RequestBody @Valid DatosRegistroMedico datosRegistroMedico ) {
        medicoRepository.save(new Medico(datosRegistroMedico));
    }
    
    @GetMapping()
    public Page<DatosListadoMedico> listadoMedicos ( @PageableDefault(page = 0, size = 10, sort = { "nombre" }) Pageable paginacion ) {
//        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);
    }
    
    @PutMapping
    @Transactional
    public void actualizarMedico ( @RequestBody @Valid DatosActualizarMedico datosActualizarMedico ) {
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
    }
    
   // DELETE logico
    @DeleteMapping("/{id}")
    @Transactional
    public void wliminarMedico ( @PathVariable Long  id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
    }
    
    // DELETE en base de datos
//    public void wliminarMedico ( @PathVariable Long  id) {
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }
}
