package ge.bog.bookstore.repository;

import ge.bog.bookstore.domain.BookUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BookUsers, Integer> {

    Optional<List<BookUsers> >findBookUsersByFirstNameAndLastName(String firstName, String lastName);
    Optional<BookUsers>  findByPin(int pin);

}
