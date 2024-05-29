package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgemdarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;

import java.time.DayOfWeek;

public class HorarioDeFuncionamientoClinica {
    public void validar ( DatosAgemdarConsulta datos ) {
        var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());
        var antesDeApertura = datos.fecha().getHour() < 7;
        var despuesDeCierre = datos.fecha().getHour() > 19;
        
        if ( domingo || antesDeApertura || despuesDeCierre )
            throw new ValidationException("El horario de atención de la clínica es de lunes a sábado de 7:00 a 19:00 horas");
    }
}
