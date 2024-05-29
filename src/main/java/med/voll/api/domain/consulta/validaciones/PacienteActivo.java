package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgemdarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PacienteActivo {
    

    private PacienteRepository repository;
    
    public void validar( DatosAgemdarConsulta datos ) {
        if ( datos.idPaciente() == null ) {
            return;
        }
        
        var pacienteActivo = repository.findActivoById(datos.idPaciente());
        
        if ( ! pacienteActivo ) {
            throw new ValidationException("No se puede permitir agendar citas con pacientes incactivosm");
        }
    }
}
