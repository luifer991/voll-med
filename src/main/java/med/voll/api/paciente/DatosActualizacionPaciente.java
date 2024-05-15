package med.voll.api.paciente;

import med.voll.api.direccion.DatosDireccion;

public record DatosActualizacionPaciente(
        Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion
) {
}
