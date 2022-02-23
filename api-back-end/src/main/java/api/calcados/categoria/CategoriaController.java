package api.calcados.categoria;

import api.calcados.utilitarios.Paginacao;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("categoria")
@AllArgsConstructor
public class CategoriaController {
    private CategoriaService categoriaService;
    private CategoriaRepository categoriaRepository;

    @PostMapping("/cadastrar")
    public ResponseEntity<CategoriaRepresentation.DetalheCategoria> cadastrarCategoria(
            @Valid @RequestBody CategoriaRepresentation.CriarOuAtualizarCategoria criarOuAtualizarCategoria){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoriaRepresentation.DetalheCategoria
                        .from(this.categoriaService.salvarCategoria(criarOuAtualizarCategoria)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaRepresentation.DetalheCategoria> buscarCategoria(@PathVariable("id") int id){
        return ResponseEntity.ok(CategoriaRepresentation.DetalheCategoria
                .from(this.categoriaService.buscarCategoria(id)));
    }

    @GetMapping("/")
    public ResponseEntity<Paginacao> todasCategorias(
            @QuerydslPredicate(root = Categoria.class) Predicate filtroURI,
            @RequestParam(name = "tamanhoPagina", defaultValue = "10") int tamanhoPagina,
            @RequestParam(name = "paginaDesejada", defaultValue = "0") int pagina){
        //
        BooleanExpression filtro = Objects.isNull(filtroURI) ?
                QCategoria.categoria.status.eq(Categoria.Status.ATIVO) :
                QCategoria.categoria.status.eq(Categoria.Status.ATIVO)
                        .and(filtroURI);
        //
        Pageable paginaDesejada = PageRequest.of(pagina, tamanhoPagina);
        //
        Page<Categoria> listaCategoria = this.categoriaRepository.findAll(filtro, paginaDesejada);
        //
        Paginacao paginacao = Paginacao.builder()
                .paginaSelecionada(pagina)
                .tamanho(tamanhoPagina)
                .proximaPagina(listaCategoria.hasNext())
                .qtdeTotalRegistro(listaCategoria.getTotalElements())
                .conteudoPagina(CategoriaRepresentation.ListaCategoria
                        .from(listaCategoria.getContent()))
                .build();
        //
        return ResponseEntity.ok(paginacao);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CategoriaRepresentation.DetalheCategoria> atualizarCategoria(@PathVariable("id") int id,
       @Valid @RequestBody CategoriaRepresentation.CriarOuAtualizarCategoria criarOuAtualizarCategoria){

        return  ResponseEntity.status(HttpStatus.OK)
                .body(CategoriaRepresentation.DetalheCategoria
                        .from(this.categoriaService.atualizarCategoria(criarOuAtualizarCategoria, id)));
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagarCategoria(@PathVariable("id") int id){
        this.categoriaService.apagarCategoria(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
