package api.calcados.categoria;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "categorias")
public class Categoria {
    @Id
    @Column(name = "id_categoria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCategoria;

    @Column(name = "descricao")
    @NotNull(message = "A descrição da categoria não pode ser nula")
    @Size(min = 1, max = 120, message = "A descrição deve conter entre 1 e 120 caracteres")
    private String descricao;

    @Column(name = "status")
    @NotNull(message = "O Status não pode ser nulo")
    private Status status;

    public enum Status{
        INATIVO,
        ATIVO
    }
}
