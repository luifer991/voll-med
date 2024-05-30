package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase ( replace = AutoConfigureTestDatabase.Replace.NONE )
@ActiveProfiles ( "test" )
class MedicoRepositoryTest {
    
    @Autowired
    private MedicoRepository medicoRepository;
    
    @Autowired
    private TestEntityManager em;
    
    @Test
    @DisplayName ( "deberia retornar null cuando el medico se encuentre en consulta con otro paciente en ese horario" )
    void seleccionarMedicoConEspecialidadEnFechaEscenario1 () {
        // GIVEN
        var proximoLunes10H = LocalDate.now()
                .with( TemporalAdjusters.next( DayOfWeek.MONDAY ) )
                .atTime( 10, 0 );
        
        var medico = registrarMedico( "Jose Perez", "jose.perez@voll.med", "125469", Especialidad.CARDIOLOGIA );
        var paciente = registrarPaciente( "Juan Rojas", "juanrojas@gmail.com", "123654" );
        registrarConsulta( medico, paciente, proximoLunes10H );
        // WHEN
        var medicoLibre = medicoRepository
                .seleccionarMedicoConEspecialidadEnFecha( Especialidad.CARDIOLOGIA, proximoLunes10H );
        // THEN
        assertNull( medicoLibre );
    }
    
    @Test
    @DisplayName ( "deberia retornar null cuando un- medico cuando realice la consulta en la base de datos para ese horario" )
    void seleccionarMedicoConEspecialidadEnFechaEscenario2 () {
        
        var proximoLunes10H = LocalDate.now()
                .with( TemporalAdjusters.next( DayOfWeek.MONDAY ) )
                .atTime( 10, 0 );
        
        var medico = registrarMedico( "Jose Perez", "jose.perez@voll.med", "125469", Especialidad.CARDIOLOGIA );
        
        var medicoLibre = medicoRepository
                .seleccionarMedicoConEspecialidadEnFecha( Especialidad.CARDIOLOGIA, proximoLunes10H );
        
        assertEquals( medico, medicoLibre );
    }
    
    private void registrarConsulta ( Medico medico, Paciente paciente, LocalDateTime fecha ) {
        em.persist( new Consulta( null, medico, paciente, fecha ) );
    }
    
    private Medico registrarMedico ( String nombre, String email, String documento, Especialidad especialidad ) {
        var medico = new Medico( datosMedico( nombre, email, documento, especialidad ) );
        em.persist( medico );
        return medico;
    }
    
    private Paciente registrarPaciente ( String nombre, String email, String documento ) {
        var paciente = new Paciente( datosPaciente( nombre, email, documento ) );
        em.persist( paciente );
        return paciente;
    }
    
    private DatosRegistroMedico datosMedico ( String nombre, String email, String documento, Especialidad especialidad ) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "1236548",
                documento,
                especialidad,
                datosDireccion()
        );
    }
    
    private DatosRegistroPaciente datosPaciente ( String nombre, String email, String documento ) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "1236548",
                documento,
                datosDireccion()
        );
    }
    
    private DatosDireccion datosDireccion () {
        return new DatosDireccion(
                "2",
                "distrito 3",
                "Bogota",
                "2",
                "b"
        );
    }
}