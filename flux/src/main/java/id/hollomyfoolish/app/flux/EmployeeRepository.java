package id.hollomyfoolish.app.flux;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class EmployeeRepository {
    public Mono<Employee> findEmployeeById(int id) {
        return Mono.just(new Employee(id, "I am " + id));
    }
}
