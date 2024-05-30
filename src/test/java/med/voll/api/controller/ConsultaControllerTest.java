package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultasService;
import med.voll.api.domain.consulta.DatosAgemdarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {
    
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester <DatosAgemdarConsulta> agemdarConsultaJacksonTester;
    @Autowired
    private JacksonTester <DatosDetalleConsulta> datosDetalleConsultaJacksonTester;
    @MockBean
    private AgendaDeConsultasService agendaDeConsultasService;
    
    @Test
    @DisplayName ( "Deberia retornar estado http 400 cuando los datos sean invalidos" )
    @WithMockUser
    void agendarEscenario1 () throws Exception {
        // GIVEN  // WHEN
        var response = mvc.perform( post( "/consultas" ) ).andReturn().getResponse();
        //THEN
        assertThat( response.getStatus() ).isEqualTo( HttpStatus.BAD_REQUEST.value() );
    }
    
    @Test
    @DisplayName ( "Deberia retornar estado http 200 cuando los datos sean validos" )
    @WithMockUser
    void agendarEscenario2 () throws Exception {
        // GIVEN
        var fecha = LocalDateTime.now().plusHours( 1 );
        var especialidad = Especialidad.CARDIOLOGIA;
        var datos = new DatosDetalleConsulta( null, 2l, 5l, fecha );
        // WHEN
        when( agendaDeConsultasService.agendar( any() ) )
                .thenReturn( datos );
        
        var response = mvc.perform( post( "/consultas" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( agemdarConsultaJacksonTester
                                .write( new DatosAgemdarConsulta( 2l, 5l, fecha, especialidad ) ).getJson() ) )
                .andReturn().getResponse();
        //THEN
        assertThat( response.getStatus() ).isEqualTo( HttpStatus.OK.value() );
        
        var jsonEsperado = datosDetalleConsultaJacksonTester
                .write( datos ).getJson();
        
        assertThat( response.getContentAsString() ).isEqualTo( jsonEsperado );
    }
}