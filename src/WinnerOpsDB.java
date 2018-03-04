import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

/**
 * Created by Simone Salvo on 17/01/2018.
 */

public class WinnerOpsDB {

    public static void main(String[] args) {
    }

    WinnerOpsDB(){

    }

    /**
     * Exercise 1
     * Return the database rows as Stream of Winner.
     * @param paths Input csv paths
     * @return rows of the given databases as stream of Winner
     */
    public static Stream<Winner> loadData (String[] paths) {

        ArrayList dataBuilder = new ArrayList();

        Stream.of(paths).forEach(p -> {
            try {
                Files.lines(Paths.get(p)).skip(1).forEach(dataBuilder::add);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    return dataBuilder.parallelStream().map(s -> WinnerImpl.Build((s.toString())));
    }

    /**
     * Exercise 2
     * Given a Stream<Winner> returns a new Stream<Winner> containing the winners that are
     * younger than 35 ordered alphabetically by names.
     */
    public static Stream<Winner> youngWinners (Stream<Winner> winners){
        return winners.filter(winner -> !winner.isNull() && winner.getWinnerAge() < 35)
                .sorted(Comparator.comparing(Winner::getWinnerName));
    }

    /**
     * Exercise 3
     * given a Stream<Winner> returns a Stream<Winner> containing exactly two winners,
     * one among the youngest and one among the oldest, ordered alphabetically by names.
     */
    public static Stream<Winner> extremeWinners (Stream<Winner> winners){

        List<Winner> streamList = new ArrayList<>();

        IntSummaryStatistics intSummaryStatistics = winners
                .filter(winner -> !winner.isNull())
                .peek(streamList::add)
                .mapToInt(Winner::getWinnerAge).summaryStatistics();


        return streamList.stream()
                .filter(winner -> winner.getWinnerAge() == intSummaryStatistics.getMin() || winner.getWinnerAge() == intSummaryStatistics.getMax())
                .filter(distinctByKey(Winner::getWinnerAge))
                .limit(2)
                .sorted(Comparator.comparing(Winner::getWinnerName));


    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * Exercise 4
     * Given a Stream<Winner> returns a Stream<String> containing the names of winners that
     * won a prize at least twice ordered alphabetically by names.
     */
    public static Stream<String> multiAwardedPerson (Stream<Winner> winners){
        return winners
                .filter(winner -> !winner.isNull())
                .collect(groupingBy(Winner::getWinnerName, counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .sorted();
    }

    /**
     * Exercise 5
     * Given a Stream<Winner> returns a Stream<String> containing the title of films who won two prizes.
     * The elements of the stream must be in chronological order, i.e. in increasing order of year
     * of the corresponding records in the database.
     */
    public static Stream<String> multiAwardedFilm (Stream<Winner> winners) {
        return winners
                .filter(winner -> !winner.isNull())
                .collect(groupingBy(Winner::getFilmTitle, LinkedHashMap::new, counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 2)
                .map(Map.Entry::getKey);
    }

    /**
     * Exercise 6 alpha
     * Given a Function<Stream<T>,Stream<U>> f and a Stream<T> s1 returns a long denoting the time
     * in nanosecs required to invoke s2.collect(Collectors.toList()),
     * where s2 is the stream returned by f.apply(s1)
     */
    public static <T,U> long measure (Function<Stream<T>,Stream<U>> f, Stream<T> s1){
        long startTime = System.nanoTime();
        f.apply(s1).collect(Collectors.toList());
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }

    /**
     * Exercise 6 bravo
     * given a Stream<Function<Stream<T>,Stream<U>>> of jobs and a Stream<T> s returns a LongStream
     * of the times required to run each job by invoking measure
     */
    public static <T,U> LongStream runJobs (Stream<Function<Stream<T>, Stream<U>>> jobs, Stream<T> s){
        List<T> tList = s.collect(toList());
        Supplier<Stream<T>> sup = () -> tList.stream();
        return jobs
                .map(f -> measure(f,sup.get()))
                .mapToLong(Long::longValue);
    }

    /**
     * Exercise 6 charlie
     * given a Stream<Winner> returns an array of 11 long values as follows:
     * elements with indexes in [2,5] report the time required to run the sequential version of Exercises 2 to 5;
     * whereas elements with indexes [7,10] report the time to run the corresponding parallel versions.
     * Note: You can decide whether to use method runJobs or not, when writing method comparison.
     * In both cases justify your choice.
     */
    public static Long[] comparison (Stream<Winner> winners){
        Long[] times = new Long[11];
        Arrays.fill(times, Long.MAX_VALUE);

        Stream<Function<Stream<Winner>, Stream<Winner>>> first_functions_blocks = Stream.of(
                WinnerOpsDB::youngWinners,
                WinnerOpsDB::extremeWinners);

        Stream<Function<Stream<Winner>, Stream<String>>> second_functions_blocks = Stream.of(
                WinnerOpsDB::multiAwardedFilm,
                WinnerOpsDB::multiAwardedPerson);

        Long[] longs = Stream.of(runJobs(first_functions_blocks, winners).toArray(),
                        runJobs(second_functions_blocks, winners).toArray())
                        .flatMap(Stream::of).toArray(Long[]::new);

        for (int i = 0, currentIndex = 2, skipIndex = 6; i < longs.length ; i++, currentIndex++) {
            if (currentIndex == skipIndex)
                currentIndex++;
            times[currentIndex] = longs[i];
        }

        return times;
    }

    /**
     * Exercise 2 Parallel
     * Given a Stream<Winner> returns a new Stream<Winner> containing the winners that are
     * younger than 35 ordered alphabetically by names.
     */
    public static Stream<Winner> youngWinnersParallel (Stream<Winner> winners){
        return winners
                .parallel()
                .filter(winner -> winner.getWinnerAge() < 35)
                .sorted(comparing(Winner::getWinnerName));
    }

    /**
     * Exercise 3 Parallel
     * given a Stream<Winner> returns a Stream<Winner> containing exactly two winners,
     * one among the youngest and one among the oldest, ordered alphabetically by names.
     */
    public static Stream<Winner> extremeWinnersParallel (Stream<Winner> winners){
        List<Winner> streamList = new ArrayList<>();

        IntSummaryStatistics intSummaryStatistics = winners
                .parallel()
                .filter(winner -> !winner.isNull())
                .peek(streamList::add)
                .mapToInt(Winner::getWinnerAge).summaryStatistics();


        return streamList.stream()
                .parallel()
                .filter(winner -> winner.getWinnerAge() == intSummaryStatistics.getMin() || winner.getWinnerAge() == intSummaryStatistics.getMax())
                .filter(distinctByKey(Winner::getWinnerAge))
                .limit(2)
                .sorted(Comparator.comparing(Winner::getWinnerName));

    }

    /**
     * Exercise 4 Parallel
     * Given a Stream<Winner> returns a Stream<String> containing the names of winners that
     * won a prize at least twice ordered alphabetically by names.
     */
    public static Stream<String> multiAwardedPersonParallel (Stream<Winner> winners){
        return winners
                .parallel()
                .collect(groupingBy(Winner::getWinnerName, counting()))
                .entrySet()
                .parallelStream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .sorted();
    }

    /**
     * Exercise 5 Parallel
     * Given a Stream<Winner> returns a Stream<String> containing the title of films who won two prizes.
     * The elements of the stream must be in chronological order, i.e. in increasing order of year
     * of the corresponding records in the database.
     */
    public static Stream<String> multiAwardedFilmParallel (Stream<Winner> winners){
        return winners
                .parallel()
                .collect(groupingBy(Winner::getFilmTitle, LinkedHashMap::new, counting()))
                .entrySet()
                .parallelStream()
                .filter(e -> e.getValue() == 2)
                .map(Map.Entry::getKey);
    }
}
