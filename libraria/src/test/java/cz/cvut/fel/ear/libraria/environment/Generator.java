package cz.cvut.fel.ear.libraria.environment;

import cz.cvut.fel.ear.libraria.model.*;
import cz.cvut.fel.ear.libraria.util.Constants;

import java.time.LocalDate;
import java.sql.Date;
import java.util.Random;

public class Generator {

    private static final Random RAND = new Random();

    public static int randomInt() {
        return RAND.nextInt();
    }

    public static boolean randomBoolean() {
        return RAND.nextBoolean();
    }

    public static java.sql.Date randomDate() {
        int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.now().toEpochDay();
        long randomDay = minDay + RAND.nextInt(maxDay - minDay);

        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
        return Date.valueOf(randomBirthDate);
    }

    public static Author generateAuthor() {
        final Author author = new Author();
        author.setFirstName("FirstName" + randomInt());
        author.setLastName("LastName" + randomInt());
        author.setBiography("Biography" + randomInt());
        return author;
    }

    public static Borrowing generateBorrowing() {
        final Borrowing borrowing = new Borrowing();
        borrowing.setCopyId(randomInt());
        borrowing.setPersonId(randomInt());
        borrowing.setStartingDate(randomDate());
        borrowing.setEndingDate(randomDate());
        return borrowing;
    }

    public static Category generateCategory() {
        final Category category = new Category();
        category.setName("Name" + randomInt());
        category.setDescription("Description" + randomInt());
        return category;
    }

    public static Copy generateCopy() {
        final Copy copy = new Copy();
        copy.setLocationId(randomInt());
        copy.setBookId(randomInt());
        copy.setSignature("Singature" + randomInt());
        copy.setPublisher("Publisher" + randomInt());
        copy.setDateOfPublication(randomDate());
        copy.setPagesCount(randomInt());
        copy.setIsbn(randomInt());
        return copy;
    }

    public static Location generateLocation() {
        final Location location = new Location();
        location.setFloor(randomInt());
        location.setSection("Section" + randomInt());
        location.setShelf("Shelf" + randomInt());
        return location;
    }

    public static Person generatePerson() {
        final Person person = new Person();
        person.setUsername("Username" + randomInt());
        person.setPassword("Password" + randomInt());
        person.setRole(randomBoolean() ? Role.ROLE_USER.getRole() : Role.ROLE_ADMIN.getRole());
        person.setDateOfBirth(randomDate());
        person.setEmail("Email" + randomInt());
        person.setPhoneNumber("PhoneNumber" + randomInt());
        return person;
    }

    public static Person generateAdminPerson() {
        final Person person = new Person();
        person.setUsername("Username" + randomInt());
        person.setPassword("Password" + randomInt());
        person.setRole(Role.ROLE_ADMIN.getRole());
        person.setDateOfBirth(randomDate());
        person.setEmail("Email" + randomInt());
        person.setPhoneNumber("PhoneNumber" + randomInt());
        return person;
    }

    public static Book generateBook() {
        final Book title = new Book();
        title.setCategoryId(randomInt());
        title.setName("Name" + randomInt());
        title.setLanguage("Language" + randomInt());
        return title;
    }

    public static Written generateWritten() {
        final Written written = new Written();
        written.setAuthorId(randomInt());
        written.setBookId(randomInt());
        return written;
    }
}
