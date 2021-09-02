import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
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
        assertThatThrownBy(() -> {

        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("");

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
        //Given
        Person person = new Person(null, "sara", 25);
        // When

        // Then
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
    void itCanDeletePerson() {

    }

    @Test
    void canGetPeopleFromDB() {

    }

    @Test
    void canGetPersonById() {

    }
}