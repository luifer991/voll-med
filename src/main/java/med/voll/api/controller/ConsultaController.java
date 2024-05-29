package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultasService;
import med.voll.api.domain.consulta.DatosAgemdarConsulta;
//import med.voll.api.domain.consulta.DatosCancelarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
    
    @Autowired
    private AgendaDeConsultasService service;
    
    @PostMapping
    @Transactional
    public ResponseEntity agendar ( @RequestBody @Valid DatosAgemdarConsulta datos ) {
        var response = service.agendar(datos);
        return ResponseEntity.ok(response);
    }
    
//    @DeleteMapping
//    @Transactional
//    public ResponseEntity cancelar ( @RequestBody @Valid DatosCancelarConsulta datos ) {
//        service.cancelar(datos);
//        return ResponseEntity.noContent().build();
//    }
}
