import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
//    MovieAnalyzer.Movie a = new MovieAnalyzer.Movie("",1,"",1,new String[]{""},"","",1,"","","","","",1,null);
    MovieAnalyzer movieAnalyzer = new MovieAnalyzer("D:\\CS209Assign1\\src\\main\\resources\\imdb_top_500.csv");
//    System.out.println(movieAnalyzer.getMovieCountByYear());
//     movieAnalyzer = new MovieAnalyzer("D:\\CS209Assign1\\src\\main\\resources\\imdb_top_500.csv");
//
//    System.out.println(movieAnalyzer.getMovieCountByGenre());
//    movieAnalyzer = new MovieAnalyzer("D:\\CS209Assign1\\src\\main\\resources\\imdb_top_500.csv");
//    System.out.println(movieAnalyzer.getTopMovies(2,"runtime"));
    System.out.println(movieAnalyzer.searchMovies("Adventure", 8.0f, 150));
//    System.out.println(movieAnalyzer.checkString("\"hhhhhh"));
//    String s1 = "abc";
//    String s2 = "bc";
//    String s3 = "bcd";
//    System.out.println(s3.compareTo(s2));


  }
}
