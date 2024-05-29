package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultasService;
import med.voll.api.domain.consulta.DatosAgemdarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    
    @Autowired
    private AgendaDeConsultasService service;
    
    @PostMapping
    @Transactional
    public ResponseEntity agendar ( @RequestBody @Valid DatosAgemdarConsulta datos ) {
        var response = service.agendar(datos);
        return ResponseEntity.ok(response);
    }
}
