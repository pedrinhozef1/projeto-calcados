package api.calcados.utilitarios;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
@Builder
public class Paginacao {
    private int tamanho;
    private int paginaSelecionada;
    private Boolean proximaPagina;
    private Long qtdeTotalRegistro;
    private List<?> conteudoPagina;
}
