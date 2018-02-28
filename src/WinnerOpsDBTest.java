import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

/**
 * Created by Simone Salvo on 10/02/2018.
 */
public class WinnerOpsDBTest {


    private String OSCAR_AGE_MALE = "/Users/ssalvo/IdeaProjects/WinnerOpsDB/src/oscar_age_male.csv";
    private String OSCAR_AGE_FEMALE = "/Users/ssalvo/IdeaProjects/WinnerOpsDB/src/oscar_age_female.csv";


    @BeforeEach
    void setUp() { }


    @Test
    Stream<Winner> loadData() {
        String[] pathString = {OSCAR_AGE_MALE, OSCAR_AGE_FEMALE};
        Stream<Winner> result = WinnerOpsDB.loadData(pathString);

        assert result != null;

        //WinnerPrinter(result);

        return result;

    }

    @Test
    void youngWinners() {
        Stream<Winner> winnerStream = this.loadData();
        Stream<Winner> youngWinners = WinnerOpsDB.youngWinners(winnerStream);
        assert  youngWinners != null;
        youngWinners.forEach(winner -> System.out.println("Winner name" + winner.getWinnerName() + ", Age: " + winner.getWinnerAge()));

    }


    @Test
    void extremeWinners() {
        Stream<Winner> winnerStream = this.loadData();
        Stream<Winner> extremeWinners = WinnerOpsDB.extremeWinners(winnerStream);
        assert  extremeWinners != null;
        extremeWinners.forEach(winner -> System.out.println("Winner name" + winner.getWinnerName() + ", Age: " + winner.getWinnerAge()));

    }

    @Test
    void multiAwardedPerson() {
        Stream<Winner> winnerStream = this.loadData();
        Stream<String> multiAwardedPerson = WinnerOpsDB.multiAwardedPerson(winnerStream);
        assert  multiAwardedPerson != null;
        multiAwardedPerson.forEach(System.out::println);
    }

    @Test
    void multiAwardedFilm() {
        Stream<Winner> winnerStream = this.loadData();
        Stream<String> multiAwardedFilm = WinnerOpsDB.multiAwardedFilm(winnerStream);
        assert  multiAwardedFilm != null;
        multiAwardedFilm.forEach(System.out::println);
    }

    @Test
    void measure() {
    }

    @Test
    void runJobs() {
    }

    @Test
    void comparison() {
    }

    @Test
    void youngWinnersParallel() {
        Stream<Winner> winnerStream = this.loadData();
        Stream<Winner> youngWinners = WinnerOpsDB.youngWinnersParallel(winnerStream);
        assert  youngWinners != null;
        youngWinners.forEach(winner -> System.out.println("Winner name" + winner.getWinnerName() + ", Age: " + winner.getWinnerAge()));

    }


    @Test
    void extremeWinnersParallel() {
        Stream<Winner> winnerStream = this.loadData();
        Stream<Winner> extremeWinners = WinnerOpsDB.extremeWinnersParallel(winnerStream);
        assert  extremeWinners != null;
        extremeWinners.forEach(winner -> System.out.println("Winner name" + winner.getWinnerName() + ", Age: " + winner.getWinnerAge()));

    }

    @Test
    void multiAwardedPersonParallel() {
        Stream<Winner> winnerStream = this.loadData();
        Stream<String> multiAwardedPerson = WinnerOpsDB.multiAwardedPersonParallel(winnerStream);
        assert  multiAwardedPerson != null;
        multiAwardedPerson.forEach(System.out::println);
    }

    @Test
    void multiAwardedFilmParallel() {
        Stream<Winner> winnerStream = this.loadData();
        Stream<String> multiAwardedFilm = WinnerOpsDB.multiAwardedFilmParallel(winnerStream);
        assert  multiAwardedFilm != null;
        multiAwardedFilm.forEach(System.out::println);
    }


    private void WinnerPrinter(Stream<Winner> winners) {
        winners.forEach(e -> {
            System.out.println(e.getYear());
            System.out.println(e.getFilmTitle());
            System.out.println(e.getWinnerName());
            System.out.println(e.getWinnerAge());
        });
    }

}
