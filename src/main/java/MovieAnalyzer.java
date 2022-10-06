import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MovieAnalyzer {
//  public static Stream<Moive> moives;
  private Stream<Moive> movies;

  public static class Moive {
    private String Series_Title;
    private int Released_Year;
    private String Certificate;
    private int Runtime;
    private String[] Genre;
    private float IMDB_Rating;
    private String Overview;
    private int Meta_score;
    private String Director;
    private String Star1;
    private String Star2;
    private String Star3;
    private String Star4;
    private long No_of_Votes;
    private long Gross;

    public Moive(String series_Title, int released_Year, String certificate,
                 int runtime, String[] genre, float IMDB_Rating, String overview,
                 int meta_score, String director, String star1, String star2,
                 String star3, String star4, long no_of_Votes, long gross) {
      Series_Title = series_Title;
      Released_Year = released_Year;
      Certificate = certificate;
      Runtime = runtime;
      Genre = genre;
      this.IMDB_Rating = IMDB_Rating;
      Overview = overview;
      Meta_score = meta_score;
      Director = director;
      Star1 = star1;
      Star2 = star2;
      Star3 = star3;
      Star4 = star4;
      No_of_Votes = no_of_Votes;
      Gross = gross;
    }

    public String getSeries_Title() {
      return Series_Title;
    }

    public int getReleased_Year() {
      return Released_Year;
    }

    public String getCertificate() {
      return Certificate;
    }

    public int getRuntime() {
      return Runtime;
    }

    public String[] getGenre() {
      return Genre;
    }

    public float getIMDB_Rating() {
      return IMDB_Rating;
    }

    public String getOverview() {
      return Overview;
    }

    public int getMeta_score() {
      return Meta_score;
    }

    public String getDirector() {
      return Director;
    }

    public String getStar1() {
      return Star1;
    }

    public String getStar2() {
      return Star2;
    }

    public String getStar3() {
      return Star3;
    }

    public String getStar4() {
      return Star4;
    }

    public long getNo_of_Votes() {
      return No_of_Votes;
    }

    public long getGross() {
      return Gross;
    }
  }


  public static Stream<Moive> readMovies(String filename) throws IOException {

    return Files.lines(Paths.get(filename))
            .map(l -> l.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1))
            .map(a -> new Moive(a[1], Integer.parseInt(a[2]), a[3],
                    Integer.parseInt(a[4].split(" ")[0]), a[5].replace("\"", "").split(","), Float.parseFloat(a[6]),
                    a[7], Integer.parseInt(a[8]), a[9], a[10], a[11], a[12], a[13],
                    Long.parseLong(a[14]), Long.parseLong(a[15].replace(",", "").replace("\"", ""))
            ));
  }

  public MovieAnalyzer(String dataset_path) throws IOException {
    movies = readMovies(dataset_path);

  }

  public Map<Integer, Integer> getMovieCountByYear() {

    Map<Integer, Integer> MovieCountByYear = movies.collect(Collectors.groupingBy(Moive::getReleased_Year,Collectors.summingInt(x -> 1)));

    return MovieCountByYear;
  }

}