package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgemdarConsulta;
import med.voll.api.domain.medico.MedicoRepository;

public class MedicoActivo {
    private MedicoRepository repository;
    
    public void validar( DatosAgemdarConsulta datos ) {
        if ( datos.idMedico() == null ) {
            return;
        }
        
        var medicoActivo = repository.findActivoById(datos.idPaciente());
        
        if ( !medicoActivo ) {
            throw new ValidationException("No se puede permitir agendar citas con pacientes incactivosm");
        }
    }
}
