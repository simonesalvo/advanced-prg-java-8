# Java 8 Features

The following exercises require using the files oscar_age_female.csv and oscar_age_male.csv that store information about the winners of the Oscar prize. 
These are text files where each line is a record storing the following fields in csv format:
Index, Year, Age, Name, Movie
where the field Year denotes the year when the Oscar was won, and Age the age the winners had when they were awarded. The other fields are self-explanatory.

__Solution format: implement all the required methods below in a file WinnerOpsDB.java__

__Provide implementation of Winner interface that represents a record of the winner database.__

## Exercise 1
Write a static method loadData that given a String[] containing the paths of winners databases returns a Stream<Winner> representing the content of the databases. Note that the first line of each file describes the format of the fields and that the values of fields Name and Movie are enclosed in double quotes, e.g., "Coquette". Your solution must opportunely deal with these cases.

## Exercise 2
Implement a static method youngWinners that given a Stream<Winner> returns a new Stream<Winner> containing the winners that are younger than 35 ordered alphabetically by names.

## Exercise 3
Write a static method extremeWinners that given a Stream<Winner> returns a Stream<Winner> containing exactly two winners, one among the youngests and one among the oldests, ordered alphabetically by names.

## Exercise 4
Write a static method multiAwardedPerson that given a Stream<Winner> returns a Stream<String> containing the names of winners that won a prize at least twice ordered alphabetically by names.

## Exercise 5
Write a static method multiAwardedFilm that given a Stream<Winner> returns a Stream<String> containing the title of films who won two prizes. 
The elements of the stream must be in chronological order, i.e. in increasing order of year of the corresponding records in the database.

## Exercise 6
Write a static method measure that given a Function<Stream<T>,Stream<U.>> f and a Stream<T> s1 returns a long denoting the time in nanosecs required to invoke s2.collect(Collectors.toList()), where s2 is the stream returned by f.apply(s1). 
Furthermore, write another static method runJobs that given a Stream<Function<Stream<T>,Stream<U.>>> of jobs and a Stream<T> s returns a LongStream of the times required to run each job by invoking measure. Then, provide parallel versions of the methods developed for Exercises 2 to 5 (their names must end the Parallel suffix, e.g, multiAwardedFilmParallel). Finally, implement a method comparison that given a Stream<Winner> returns an array of 11 long values as follows: elements with indexes in [2,5] report the time required to run the sequential version of Exercises 2 to 5; whereas elements with indexes [7,10] report the time to run the corresponding parallel versions.
Note: You can decide whether to use method runJobs or not, when writing method comparison. 


### Test code coverage
######(%Element, %Class, %method, %Line9)

WinnerImpl	100% (1/1)	83% (5/6)	94% (16/17)

WinnerOpsDB	100% (1/1)	93% (14/15)	95% (94/98)

WinnerOpsDBTest	100% (1/1)	91% (11/12)	94% (70/74)