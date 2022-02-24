package api.calcados.calcado;

import api.calcados.categoria.Categoria;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity(name = "calcados")
public class Calcado {
    @Id
    @Column(name = "id_calcado")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCalcado;

    @Column(name = "descricao")
    @NotNull(message = "A descrição do calçado não pode ser nula")
    @Size(min = 1, max = 120, message = "A descrição do calçado deve conter entre 1 e 120 caracteres")
    private String descricao;

    @Column(name = "vl_calcado")
    @NotNull(message = "O preço do calçado não pode ser nulo!")
    private double preco;

    @Column(name = "dh_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "qtd_estoque")
    @NotNull(message = "A quantidade em estoque não pode ser nula")
    private int qtdeEstoque;

    @Column(name = "cor")
    @NotNull(message = "A cor do calçado não pode ser nulo!")
    @Size(min = 1, max = 40, message = "A cor do calçado deve ter entre 1 e 40 caracteres")
    private String cor;

    @Column(name = "marca")
    @NotNull(message = "A marca do calçado não pode ser nulo!")
    @Size(min = 1, max = 30, message = "A marca do calçado deve conter entre 1 e 30 caracteres")
    private String marca;

    @JoinColumn(name = "id_categoria")
    @NotNull(message = "A categoria do calçado não pode ser nulo!")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Categoria.class)
    private Categoria categoria;
}

