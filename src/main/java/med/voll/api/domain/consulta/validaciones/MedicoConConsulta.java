package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgemdarConsulta;

public class MedicoConConsulta {
    
    private ConsultaRepository repository;
    
    public void validar( DatosAgemdarConsulta datos ) {
        if ( datos.idMedico() == null )
            return;
        
        var medicoConConsulta = repository.existsByMedicpIdAndDataBetween(datos.idMedico(), datos.fecha());
        
        if ( medicoConConsulta )
            throw new ValidationException("Este médico ya tiene una consulta en ese horario");
    }
}
