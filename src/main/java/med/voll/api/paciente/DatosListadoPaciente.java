package med.voll.api.paciente;

public record DatosListadoPaciente(
        String nombre,
        String email,
        String telefono
) {
    public DatosListadoPaciente ( Paciente paciente ) {
        this(paciente.getNombre(), paciente.getEmail(), paciente.getTelefono());
    }
}
