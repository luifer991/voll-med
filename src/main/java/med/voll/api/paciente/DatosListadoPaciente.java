package med.voll.api.paciente;

public record DatosListadoPaciente(
        Long id,
        String nombre,
        String email,
        String telefono
) {
    public DatosListadoPaciente ( Paciente paciente ) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getTelefono());
    }
}
