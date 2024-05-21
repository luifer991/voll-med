package med.voll.api.domain.paciente;

import med.voll.api.domain.direccion.DatosDireccion;

public record DatosActualizacionPaciente(
        Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion
) {
}
