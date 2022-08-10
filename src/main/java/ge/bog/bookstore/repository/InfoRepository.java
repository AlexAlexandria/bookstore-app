package ge.bog.bookstore.repository;

import ge.bog.bookstore.domain.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InfoRepository extends JpaRepository<BookInfo, Integer> {

    Optional<List<BookInfo>> findAllByName(String name);
}
