package Model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString

public class Menu {
    
    private int idMenu;
    private String nome;
    private String link;
    private String icone;
    private int exibir;
    private int status;
    
}
