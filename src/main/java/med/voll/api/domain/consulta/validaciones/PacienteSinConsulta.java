package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgemdarConsulta;

import java.time.LocalDateTime;

public class PacienteSinConsulta {
    
    private ConsultaRepository repository;
    
    public void validar ( DatosAgemdarConsulta datos ) {
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);
        
        var pacienteConConsulta = repository.existsByPacienteIdAndDataBetween(datos.idPaciente(), primerHorario, ultimoHorario);
        
        if ( pacienteConConsulta ) {
            throw new ValidationException("El paciente ya tiene una consulta para ese día");
        }
    }
}
