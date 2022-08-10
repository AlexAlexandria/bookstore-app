package ge.bog.bookstore.repository;

import ge.bog.bookstore.domain.BookPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<BookPurchase, Integer> {

    Optional<List<BookPurchase>> findAllByUserId (int userId);

}
