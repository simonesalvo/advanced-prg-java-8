import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Simone Salvo on 23/01/2018.
 */
public class WinnerImpl implements Winner {
    private int year;
    private int age;
    private String winnerName;
    private String filmTitle;

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

    @Override
    public boolean isNull(){
        return this.age == 0 || this.year == 0 || this.filmTitle == null ||
                this.filmTitle.isEmpty() || this.getWinnerName() == null || this.winnerName.isEmpty();
    }

    public static Winner Build(String s) {
        String regexBuilder = "(\\d{4}),(\\d{2}),\"((\\w+\\s?\\W?){1,4})\",\"((\\w+\\s?\\W?){1,8})\"";
        WinnerImpl builtWinner = new WinnerImpl();
        Pattern p = Pattern.compile(regexBuilder);
        Matcher m = p.matcher(s);

        if (m.find()) {
            builtWinner.year = Integer.parseInt(m.group(1));
            builtWinner.age = Integer.parseInt(m.group(2));
            builtWinner.winnerName = m.group(3);
            builtWinner.filmTitle = m.group(5);
        }

        return  builtWinner;
    }
}
