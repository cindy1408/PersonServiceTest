import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;

class PersonServiceTest {

    // TODO: Annotate this with @Mock
    @Mock private PersonDAO personDAO;

    private PersonService underTest;


    @BeforeEach
    void setUp() {
        // TODO: Uncomment line bellow to initialise mocks
        MockitoAnnotations.openMocks(this);
        // TODO: pass mock into PersonService constructor

        underTest = new PersonService(personDAO);
//        personDAO = Mockito.mock(PersonDAO.class);
    }

    /*
       TODO: Test all these methods.
        You might need to create additional methods. Check test coverage
    */

//    Good luck :)

    @Test
    void itCanSavePerson() {
        //Given
        Person person = new Person(1, "Sara", 25);
        Mockito.when(personDAO.insertPerson(eq(person)))
                .thenReturn(1);

        Mockito.when(personDAO.selectAllPeople()).thenReturn(List.of(
                new Person(2, "Lizzie", 25)
        ));
        // When
        int result = underTest.savePerson(person);
        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        Mockito.verify(personDAO).insertPerson(personArgumentCaptor.capture());
        Person expectedSarah = personArgumentCaptor.getValue();
        //THEN
        assertThat(expectedSarah).isEqualTo(person);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void throwErrorIfPersonExist() {
        //Given
        Person person = new Person(1, "Sara", 25);
        // When
        Mockito.when(personDAO.selectAllPeople()).thenReturn(List.of(
                new Person(1, "Sara", 25),
                new Person(3, "Busayo", 25)
        ));
        // Then
        assertThatThrownBy(() -> {
            underTest.savePerson(person);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("person with id " + person.getId() + " already exists");
        Mockito.verify(personDAO, Mockito.never()).insertPerson(person);
    }

    @Test
    void shouldThrowWhenPersonIdIsNull() {
        Person person = new Person(null, "sara", 25);

        assertThatThrownBy(() -> {
            underTest.savePerson(person);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Person cannot have empty fields");

        Mockito.verifyNoInteractions(personDAO);
    }

    @Test
    void shouldThrowWhenPersonAgeIsNull() {
        //Given
        Person person = new Person(1, "sara", null);
        // When
        // Then
        assertThatThrownBy(() -> {
            underTest.savePerson(person);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Person cannot have empty fields");

        Mockito.verifyNoInteractions(personDAO);
    }

    @Test
    void shouldThrowWhenPersonIsNull() {
        //Given
        // When
        // Then
        assertThatThrownBy(() -> {
            underTest.savePerson(null);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Person cannot be null");

        Mockito.verifyNoInteractions(personDAO);
    }

    @Test
    void shouldThrowWhenPersonNameIsNull() {
        //Given
        Person person = new Person(1, null, 25);
        // When
        // Then
        assertThatThrownBy(() -> {
            underTest.savePerson(person);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Person cannot have empty fields");

        Mockito.verifyNoInteractions(personDAO);
    }

    @Test
    void itCanDeletePersonWhenTheyExist() {
        //Given
        Person person = new Person(2, "Sara", 25);
        Mockito.when(personDAO.selectAllPeople()).thenReturn(List.of(
                new Person(2, "Sara", 25)
        ));
        Mockito.when(personDAO.deletePerson(person.getId())).thenReturn(1);
        // When
        int result = underTest.removePerson(person.getId());
        Mockito.verify(personDAO).deletePerson(person.getId());
        Integer expectedId = person.getId();
        //THEN
        assertThat(expectedId).isEqualTo(person.getId());
        assertThat(result).isEqualTo(1);
    }

    @Test
    void cantDeletePersonAsTheyDoNotExist(){
        Person mockPerson = new Person(1, "Kenny", 25);
        Mockito.when(personDAO.selectAllPeople()).thenReturn(List.of(
                new Person(2, "Jerry",30)
        ));
        Mockito.when(personDAO.deletePerson(mockPerson.getId())).thenReturn(1);
        assertThatThrownBy(() -> {
            underTest.removePerson(mockPerson.getId());
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("person with id " + mockPerson.getId() + " not found");
        Mockito.verify(personDAO, Mockito.never()).deletePerson(mockPerson.getId());
    }

    @Test
    void canGetPeopleFromDB() {
        Mockito.when(personDAO.selectAllPeople()).thenReturn(List.of(
                new Person(1, "James", 25),
                new Person(2, "Gabby", 25),
                new Person(3, "Brenda", 25)
        ));
        List<Person> fullList = underTest.getPeople();
        assertThat(fullList).isEqualTo(personDAO.selectAllPeople());
    }

    @Test
    void canGetPersonById() {
        Person mockPerson = new Person(1, "Hannah", 25);

        Mockito.when(personDAO.getPersonById(mockPerson.getId())).thenReturn(Optional.of(mockPerson));
        Mockito.when(personDAO.selectAllPeople()).thenReturn(List.of(
                mockPerson = new Person(1, "Hannah", 25)
        ));
        Optional<Person> expected = personDAO.getPersonById(mockPerson.getId());
        Optional<Person> actual = underTest.getPersonById(mockPerson.getId());
        assertThat(actual).isEqualTo(expected);
    }
}