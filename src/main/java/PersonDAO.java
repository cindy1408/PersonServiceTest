import java.util.List;
import java.util.Optional;

public interface PersonDAO {
    int insertPerson(Person person);
    int deletePerson(int id);
    List<Person> selectAllPeople();
    Optional<Person> getPersonById(int id);
}
