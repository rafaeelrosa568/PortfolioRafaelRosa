package Model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString

public class Usuario {
    private int idUsuario;
    private String nome;
    private String login;
    private String senha;
    private String email;
    private int status;
    private Perfil perfil;
}
