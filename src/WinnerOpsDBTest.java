import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.stream.LongStream;
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

        return result;
    }

    @Test
    void youngWinners() {
        String[] pathString = {OSCAR_AGE_MALE, OSCAR_AGE_FEMALE};
        Stream<Winner> winnerStream = WinnerOpsDB.loadData(pathString);
        Stream<Winner> youngWinners = WinnerOpsDB.youngWinners(winnerStream);
        assert  youngWinners != null;
        youngWinners.forEachOrdered(winner -> System.out.println("Winner name " + winner.getWinnerName() + ", Age: " + winner.getWinnerAge()));

    }


    @Test
    void extremeWinners() {
        String[] pathString = {OSCAR_AGE_MALE, OSCAR_AGE_FEMALE};
        Stream<Winner> winnerStream = WinnerOpsDB.loadData(pathString);
        Stream<Winner> extremeWinners = WinnerOpsDB.extremeWinners(winnerStream);
        assert  extremeWinners != null;
        extremeWinners.forEachOrdered(winner -> System.out.println("Winner name " + winner.getWinnerName() + ", Age: " + winner.getWinnerAge()));

    }

    @Test
    void multiAwardedPerson() {
        String[] pathString = {OSCAR_AGE_MALE, OSCAR_AGE_FEMALE};
        Stream<Winner> winnerStream = WinnerOpsDB.loadData(pathString);
        Stream<String> multiAwardedPerson = WinnerOpsDB.multiAwardedPerson(winnerStream);
        assert  multiAwardedPerson != null;
        multiAwardedPerson.forEach(System.out::println);
    }

    @Test
    void multiAwardedFilm() {
        String[] pathString = {OSCAR_AGE_MALE, OSCAR_AGE_FEMALE};
        Stream<Winner> winnerStream = WinnerOpsDB.loadData(pathString);
        Stream<String> multiAwardedFilm = WinnerOpsDB.multiAwardedFilm(winnerStream);
        assert  multiAwardedFilm != null;
        multiAwardedFilm.forEach(System.out::println);
    }

    @Test
    void measure() {
        String[] pathString = {OSCAR_AGE_MALE, OSCAR_AGE_FEMALE};
        Stream<Winner> winnerStream = WinnerOpsDB.loadData(pathString);

        long measure = WinnerOpsDB.measure(WinnerOpsDB::multiAwardedPerson, winnerStream);

        assert measure > 0;

        System.out.println("Measure of multiAwardedPerson execution " + measure);
    }

    @Test
    void runJobs() {
        String[] pathString = {OSCAR_AGE_MALE, OSCAR_AGE_FEMALE};
        Stream<Winner> winnerStream = WinnerOpsDB.loadData(pathString);

        Stream<Function<Stream<Winner>, Stream<String>>> functions_blocks = Stream.of(
                WinnerOpsDB::multiAwardedFilm,
                WinnerOpsDB::multiAwardedPerson);

        LongStream longStream = WinnerOpsDB.runJobs(functions_blocks, winnerStream);

        assert longStream != null;

        longStream.forEach(measure -> System.out.println("Measure multiAwardedFilm, multiAwardedPerson: " + measure));

    }

    @Test
    void comparison() {
        String[] pathString = {OSCAR_AGE_MALE, OSCAR_AGE_FEMALE};
        Stream<Winner> winnerStream = WinnerOpsDB.loadData(pathString);
        Long[] timeComparison = WinnerOpsDB.comparison(winnerStream);
        assert timeComparison != null;
        Stream.of(timeComparison).peek(System.out::println);
    }

    @Test
    void youngWinnersParallel() {
        String[] pathString = {OSCAR_AGE_MALE, OSCAR_AGE_FEMALE};
        Stream<Winner> winnerStream = WinnerOpsDB.loadData(pathString);
        Stream<Winner> youngWinners = WinnerOpsDB.youngWinnersParallel(winnerStream);
        assert  youngWinners != null;
        youngWinners.forEachOrdered(winner -> System.out.println("Winner name " + winner.getWinnerName() + ", Age: " + winner.getWinnerAge()));

    }


    @Test
    void extremeWinnersParallel() {
        String[] pathString = {OSCAR_AGE_MALE, OSCAR_AGE_FEMALE};
        Stream<Winner> winnerStream = WinnerOpsDB.loadData(pathString);
        Stream<Winner> extremeWinners = WinnerOpsDB.extremeWinnersParallel(winnerStream);
        assert  extremeWinners != null;
        extremeWinners.forEachOrdered(winner -> System.out.println("Winner name " + winner.getWinnerName() + ", Age: " + winner.getWinnerAge()));

    }

    @Test
    void multiAwardedPersonParallel() {
        String[] pathString = {OSCAR_AGE_MALE, OSCAR_AGE_FEMALE};
        Stream<Winner> winnerStream = WinnerOpsDB.loadData(pathString);
        Stream<String> multiAwardedPerson = WinnerOpsDB.multiAwardedPersonParallel(winnerStream);
        assert  multiAwardedPerson != null;
        multiAwardedPerson.forEachOrdered(System.out::println);
    }

    @Test
    void multiAwardedFilmParallel() {
        String[] pathString = {OSCAR_AGE_MALE, OSCAR_AGE_FEMALE};
        Stream<Winner> winnerStream = WinnerOpsDB.loadData(pathString);
        Stream<String> multiAwardedFilm = WinnerOpsDB.multiAwardedFilmParallel(winnerStream);
        assert  multiAwardedFilm != null;
        multiAwardedFilm.forEachOrdered(System.out::println);
    }


}
