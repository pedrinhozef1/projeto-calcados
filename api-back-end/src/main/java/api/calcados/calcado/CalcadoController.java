package api.calcados.calcado;

import api.calcados.categoria.Categoria;
import api.calcados.categoria.CategoriaService;
import api.calcados.utilitarios.Paginacao;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/calcado")
@AllArgsConstructor
public class CalcadoController {
    private CalcadoService calcadoService;
    private CalcadoRepository calcadoRepository;
    private CategoriaService categoriaService;

    @PostMapping("/cadastro")
    public ResponseEntity<CalcadoRepresentation.DetalheCalcado> salvarCalcado(
            @Valid @RequestBody CalcadoRepresentation.CriarOuAtualizarCalcado criarOuAtualizarCalcado){

        Categoria categoria = this.categoriaService.buscarCategoria(criarOuAtualizarCalcado.getCategoria());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CalcadoRepresentation.DetalheCalcado
                        .from(this.calcadoService.salvarCalcado(criarOuAtualizarCalcado, categoria)));
    }
    //
    @GetMapping("/{id}")
    public ResponseEntity<CalcadoRepresentation.DetalheCalcado> buscarCalcado(@PathVariable("id") int id){
        return ResponseEntity
                .ok(CalcadoRepresentation.DetalheCalcado
                        .from(this.calcadoService.buscarCalcado(id)));
    }
    //
    @GetMapping("/")
    public ResponseEntity<Paginacao> todosCalcados(
            @QuerydslPredicate(root = Calcado.class)Predicate filtroURI,
            @RequestParam(name = "tamanhoPagina", defaultValue = "10")int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0")int paginaDesejada){
        //
        Pageable pagina = PageRequest.of(paginaDesejada, tamanhoPagina);
        //
        Page<Calcado> listaCalcado = this.calcadoRepository.findAll(filtroURI, pagina);
        //
        Paginacao paginacao = Paginacao.builder()
                .paginaSelecionada(paginaDesejada)
                .tamanho(tamanhoPagina)
                .proximaPagina(listaCalcado.hasNext())
                .qtdeTotalRegistro(listaCalcado.getTotalElements())
                .conteudoPagina(CalcadoRepresentation.ListaCalcado
                        .from(listaCalcado.getContent()))
                .build();

        return ResponseEntity.ok(paginacao);
    }
    //
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CalcadoRepresentation.DetalheCalcado> atualizarCalcado(@PathVariable("id") int id,
        @Valid @RequestBody CalcadoRepresentation.CriarOuAtualizarCalcado criarOuAtualizarCalcado){

        Categoria categoria = this.categoriaService.buscarCategoria(criarOuAtualizarCalcado.getCategoria());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CalcadoRepresentation.DetalheCalcado
                        .from(this.calcadoService.atualizarCalcado(criarOuAtualizarCalcado, id, categoria)));
    }
    //
    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagarCalcado(@PathVariable("id") int id){
        this.calcadoService.apagarCalcado(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
