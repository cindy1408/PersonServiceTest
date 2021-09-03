
import java.util.List;
import java.util.Optional;

public class PersonService {

    private final PersonDAO personDAO;

    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public int savePerson(Person person) {
        if(person == null){
            throw new IllegalArgumentException("Person cannot be null");
        }
        if (person.getAge() == null ||
                person.getId() == null ||
                person.getName() == null || person.getName().length() == 0) {
            throw new IllegalStateException("Person cannot have empty fields");
        }

        boolean exists = doesPersonWithIdExists(person.getId());

        if (exists) {
            throw new IllegalStateException("person with id " + person.getId() + " already exists");
        }

        int result = personDAO.insertPerson(person);
        return result;
    }


    public int removePerson(int id) {
        boolean exists = doesPersonWithIdExists(id);
        if (!exists) {
            throw new IllegalStateException("person with id " + id + " not found");
        }
        return personDAO.deletePerson(id);
    }


    public List<Person> getPeople() {
        return personDAO.selectAllPeople();
    }

    public Optional<Person> getPersonById(int id) {
        return personDAO
                .selectAllPeople()
                .stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    private boolean doesPersonWithIdExists(Integer id) {
        return personDAO
                .selectAllPeople()
                .stream()
                .anyMatch(p -> p.getId().equals(id));
    }
}
