package api.calcados.calcado;

import api.calcados.categoria.Categoria;
import api.calcados.categoria.CategoriaRepresentation;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface CalcadoRepresentation {
    @Data
    @Getter
    @Setter
    class CriarOuAtualizarCalcado{
        @NotNull(message = "A descrição do calçado não pode ser nula")
        @Size(min = 1, max = 120, message = "A descrição do calçado deve conter entre 1 e 120 caracteres")
        private String descricao;

        @NotNull(message = "O preço do calçado não pode ser nulo!")
        private double preco;

        @NotNull(message = "A quantidade em estoque não pode ser nula")
        private int qtdeEstoque;

        @NotNull(message = "A cor do calçado não pode ser nulo!")
        @Size(min = 1, max = 40, message = "A cor do calçado deve ter entre 1 e 40 caracteres")
        private String cor;

        @NotNull(message = "A marca do calçado não pode ser nulo!")
        @Size(min = 1, max = 30, message = "A marca do calçado deve conter entre 1 e 30 caracteres")
        private String marca;

        @NotNull(message = "A categoria do calçado não pode ser nulo!")
        private int categoria;
    }
    //
    @Data
    @Setter
    @Getter
    @Builder
    class DetalheCalcado{
        private int idCalcado;
        private String descricao;
        private double preco;
        private LocalDateTime dataCadastro;
        private int qtdeEstoque;
        private String cor;
        private String marca;
        private CategoriaRepresentation.ListaCategoria categoria;

        public static DetalheCalcado from(Calcado calcado){
            return DetalheCalcado.builder()
                    .idCalcado(calcado.getIdCalcado())
                    .descricao(calcado.getDescricao())
                    .preco(calcado.getPreco())
                    .dataCadastro(calcado.getDataCadastro())
                    .qtdeEstoque(calcado.getQtdeEstoque())
                    .cor(calcado.getCor())
                    .marca(calcado.getMarca())
                    .categoria(CategoriaRepresentation.ListaCategoria.from(calcado.getCategoria()))
                    .build();
        }
    }
    @Data
    @Getter
    @Setter
    @Builder
    class ListaCalcado{
        private int idCalcado;
        private String descricao;
        private double preco;
        private String marca;

        public static ListaCalcado from(Calcado calcado){
            return ListaCalcado.builder()
                    .idCalcado(calcado.getIdCalcado())
                    .descricao(calcado.getDescricao())
                    .preco(calcado.getPreco())
                    .marca(calcado.getMarca())
                    .build();
        }
        public static List<ListaCalcado> from(List<Calcado> calcado){
            return calcado.stream()
                    .map(ListaCalcado::from)
                    .collect(Collectors.toList());
        }
    }

}