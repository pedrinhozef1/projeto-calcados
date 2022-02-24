package api.calcados.calcado;

import api.calcados.categoria.Categoria;
import api.calcados.exceptions.NotFoundException;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CalcadoService {
    private final CalcadoRepository calcadoRepository;

    public Calcado salvarCalcado(CalcadoRepresentation.CriarOuAtualizarCalcado criarOuAtualizarCalcado,
                                 Categoria categoria){

        Calcado calcado = Calcado.builder()
                .descricao(criarOuAtualizarCalcado.getDescricao())
                .preco(criarOuAtualizarCalcado.getPreco())
                .dataCadastro(LocalDateTime.now())
                .qtdeEstoque(criarOuAtualizarCalcado.getQtdeEstoque())
                .cor(criarOuAtualizarCalcado.getCor())
                .marca(criarOuAtualizarCalcado.getMarca())
                .categoria(categoria)
                .build();

        return this.calcadoRepository.save(calcado);
    }
    //
    public Calcado buscarCalcado(int id){
        BooleanExpression filtro = QCalcado.calcado.idCalcado.eq(id);

        return this.calcadoRepository.findOne(filtro)
                .orElseThrow(()-> new NotFoundException("Calçado não encontrado com o código " + id));
    }
    //
    public Calcado atualizarCalcado(CalcadoRepresentation.CriarOuAtualizarCalcado criarOuAtualizarCalcado,
        int id, Categoria categoria){

        Calcado calcadoAntigo = this.buscarCalcado(id);

        Calcado calcadoAtualizado = calcadoAntigo.toBuilder()
                .descricao(criarOuAtualizarCalcado.getDescricao())
                .preco(criarOuAtualizarCalcado.getPreco())
                .qtdeEstoque(criarOuAtualizarCalcado.getQtdeEstoque())
                .dataCadastro(calcadoAntigo.getDataCadastro())
                .cor(criarOuAtualizarCalcado.getCor())
                .marca(criarOuAtualizarCalcado.getMarca())
                .categoria(categoria)
                .build();

        return this.calcadoRepository.save(calcadoAtualizado);
    }
    //
    public void apagarCalcado(int id){
        Calcado calcado = this.buscarCalcado(id);

        this.calcadoRepository.delete(calcado);
    }
}
