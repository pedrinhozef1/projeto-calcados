package api.calcados.categoria;

import api.calcados.exceptions.NotFoundException;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public Categoria salvarCategoria(CategoriaRepresentation.CriarOuAtualizarCategoria criarOuAtualizarCategoria){
        return this.categoriaRepository.save(Categoria.builder()
                .descricao(criarOuAtualizarCategoria.getDescricao())
                .status(Categoria.Status.ATIVO)
                .build());
    }

    public Categoria buscarCategoria(int id){
        BooleanExpression filtro = QCategoria.categoria.idCategoria.eq(id);

        return this.categoriaRepository.findOne(filtro)
                .orElseThrow(() -> new NotFoundException(("Categoria não encontrada com o codigo " + id)));
    }

    public void apagarCategoria(int id){
        Categoria categoria = this.buscarCategoria(id);

        this.categoriaRepository.delete(categoria);
    }

    public Categoria atualizarCategoria(
            CategoriaRepresentation.CriarOuAtualizarCategoria criarOuAtualizarCategoria, int id){

        Categoria categoriaAntiga = this.buscarCategoria(id);

        Categoria categoriaAtualizada = categoriaAntiga.toBuilder()
                .descricao(criarOuAtualizarCategoria.getDescricao())
                .build();

        return this.categoriaRepository.save(categoriaAtualizada);
    }
}
