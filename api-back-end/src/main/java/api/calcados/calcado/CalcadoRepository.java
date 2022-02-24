package api.calcados.calcado;

import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CalcadoRepository extends PagingAndSortingRepository<Calcado, Long>,
        QuerydslPredicateExecutor<Calcado> {
    List<Calcado> findAll(Predicate filtro);
}
