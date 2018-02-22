/**
 * Created by Simone Salvo on 23/01/2018.
 */
public class WinnerImpl implements Winner {
    private final int year;
    private final int age;
    private final String winnerName;
    private final String filmTitle;

    public WinnerImpl(String[] string) {
        this.year = Integer.parseInt(string[1]);
        this.age = Integer.parseInt(string[2]);
        this.winnerName = string[3].replace("\"", "");
        this.filmTitle = string[4].replace("\"", "");

    }

    @Override
    public int getYear() {
        return this.year;
    }

    @Override
    public int getWinnerAge() {
        return this.age;
    }

    @Override
    public String getWinnerName() {
        return this.winnerName;
    }

    @Override
    public String getFilmTitle() {
        return this.filmTitle;
    }
}
