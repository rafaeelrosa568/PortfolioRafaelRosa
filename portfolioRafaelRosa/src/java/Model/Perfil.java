package Model;

import java.util.Date;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString

public class Perfil {

    private int idPerfil;
    private String nome;
    private Date dataCadastro;
    private int status;
    //status aqui vai receber 0 ou 1. 0=desativado 1=ativado
}
