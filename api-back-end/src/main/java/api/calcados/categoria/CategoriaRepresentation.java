package api.calcados.categoria;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public interface CategoriaRepresentation {
    @Data
    @Setter
    @Getter
    class CriarOuAtualizarCategoria{
        @NotNull(message = "A descrição da categoria não pode ser nula")
        @Size(min = 1, max = 120, message = "A descrição deve conter entre 1 e 120 caracteres")
        private String descricao;
    }

    @Data
    @Getter
    @Setter
    @Builder
    class DetalheCategoria{
        private int id;
        private String descricao;
        private Categoria.Status status;

        public static DetalheCategoria from(Categoria categoria){
            return DetalheCategoria.builder()
                    .descricao(categoria.getDescricao())
                    .id(categoria.getIdCategoria())
                    .status(categoria.getStatus())
                    .build();
        }
    }
    @Data
    @Getter
    @Setter
    @Builder
    class ListaCategoria {
        private int id;
        private String descricao;

        public static ListaCategoria from(Categoria categoria){
            return ListaCategoria.builder()
                    .id(categoria.getIdCategoria())
                    .descricao(categoria.getDescricao())
                    .build();
        }
        public static List<ListaCategoria> from(List<Categoria> categoria){
            return categoria.stream()
                    .map(ListaCategoria::from)
                    .collect(Collectors.toList());
        }
    }

}
