import java.util.stream.Stream;

/**
 * Created by Simone Salvo on 10/02/2018.
 */
public class WinnerOpsDBTest {


    private String OSCAR_AGE_MALE = "/Users/ssalvo/IdeaProjects/WinnerOpsDB/src/oscar_age_male.csv";
    private String OSCAR_AGE_FEMALE = "/Users/ssalvo/IdeaProjects/WinnerOpsDB/src/oscar_age_female.csv";


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void loadData() {
        String[] pathString = {OSCAR_AGE_MALE, OSCAR_AGE_FEMALE};
        Stream<Winner> result = WinnerOpsDB.loadData(pathString);

        assert result != null;

        result.forEach(e -> {
            System.out.println(e.getFilmTitle());
            System.out.println(e.getYear());
            System.out.println(e.getWinnerName());
            System.out.println(e.getWinnerAge());
        });
    }

    @org.junit.jupiter.api.Test
    void youngWinners() {
    }

    @org.junit.jupiter.api.Test
    void extremeWinners() {
    }

    @org.junit.jupiter.api.Test
    void multiAwardedPerson() {
    }

    @org.junit.jupiter.api.Test
    void multiAwardedFilm() {
    }

    @org.junit.jupiter.api.Test
    void measure() {
    }

    @org.junit.jupiter.api.Test
    void runJobs() {
    }

    @org.junit.jupiter.api.Test
    void comparison() {
    }

    @org.junit.jupiter.api.Test
    void youngWinnersParallel() {
    }

    @org.junit.jupiter.api.Test
    void extremeWinnersParallel() {
    }

    @org.junit.jupiter.api.Test
    void multiAwardedPersonParallel() {
    }

    @org.junit.jupiter.api.Test
    void multiAwardedFilmParallel() {
    }
}
