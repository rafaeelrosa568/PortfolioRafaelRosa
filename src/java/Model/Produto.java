package Model;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Produto {
    
    private int idProduto;
    private String nome;
    private String descricao;
    private int status;
    private Date dataCadastro;

    

    

    
}
