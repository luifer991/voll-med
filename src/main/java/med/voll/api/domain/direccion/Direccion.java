package med.voll.api.domain.direccion;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    
    private String calle;
    private String distrito;
    private String numero;
    private String ciudad;
    private String complemento;
    
    public Direccion ( DatosDireccion direccion ) {
        this.calle = direccion.calle();
        this.numero = direccion.numero();
        this.distrito = direccion.distrito();
        this.ciudad =  direccion.ciudad();
        this.complemento = direccion.complemento();
    }
    
    public Direccion actualizarDatos ( DatosDireccion direccion ) {
        this.calle = direccion.calle();
        this.numero = direccion.numero();
        this.distrito = direccion.distrito();
        this.ciudad =  direccion.ciudad();
        this.complemento = direccion.complemento();
        return this;
    }
    
    public Direccion atualizarInformacion ( DatosDireccion direccion ) {
        this.calle = direccion.calle();
        this.numero = direccion.numero();
        this.distrito = direccion.distrito();
        this.ciudad =  direccion.ciudad();
        this.complemento = direccion.complemento();
        return this;
    }
}
