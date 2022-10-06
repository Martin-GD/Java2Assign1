import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MovieAnalyzer {
//  public static Stream<Movie> moives;
  private Stream<Movie> movies;

  public static class Movie {
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

    public Movie(String series_Title, int released_Year, String certificate,
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


//  public static Stream<Movie> readMovies(String filename) throws IOException {
////    Stream<Movie> movieStream = HashMap
//
//    return Files.lines(Paths.get(filename), StandardCharsets.UTF_8)
//            .map(l -> l.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1))
//            .map(a -> new Movie(a[1], Integer.parseInt(a[2]), a[3],
//                    Integer.parseInt(a[4].split(" ")[0]), a[5].replace("\"", "").split(","), Float.parseFloat(a[6]),
//                    a[7], Integer.parseInt(a[8]), a[9], a[10], a[11], a[12], a[13],
//                    Long.parseLong(a[14]), Long.parseLong(a[15].replace(",", "").replace("\"", ""))
//            ));
//  }
  public static Stream<Movie> readMovies(String filename) throws IOException {
//    Stream<Movie> movieStream = HashMap
    Set<Movie> movieSet = new HashSet<>();
    BufferedReader sb = new BufferedReader(new FileReader(new File(filename),StandardCharsets.UTF_8));
    String cur = sb.readLine();
    cur = sb.readLine();
    while (cur!=null){
      String[] a = cur.replace("\n","").split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);
      String[] gen = a[5].replace("\"", "").split(",");
      for (int i = 0; i < gen.length; i++) {
        gen[i] = gen[i].strip();
      }
      movieSet.add(new Movie(a[1], Integer.parseInt(a[2]), a[3],
              Integer.parseInt(a[4].split(" ")[0]),
              gen,
              a[6].equals("")?0:Float.parseFloat(a[6]),
              a[7],
              a[8].equals("")? 0: Integer.parseInt(a[8]), a[9], a[10], a[11], a[12], a[13],
              a[14].equals("") ? 0:Long.parseLong(a[14]),
              a[15].equals("") ? 0 :Long.parseLong(a[15].replace(",", "").replace("\"", ""))
      ));
      cur = sb.readLine();
    }

    return movieSet.stream();

//    return Files.lines(Paths.get(filename), StandardCharsets.UTF_8)
//            .map(l -> l.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1))
//            .map(a -> new Movie(a[1], Integer.parseInt(a[2]), a[3],
//                    Integer.parseInt(a[4].split(" ")[0]), a[5].replace("\"", "").split(","), Float.parseFloat(a[6]),
//                    a[7], Integer.parseInt(a[8]), a[9], a[10], a[11], a[12], a[13],
//                    Long.parseLong(a[14]), Long.parseLong(a[15].replace(",", "").replace("\"", ""))
//            ));
  }

  public MovieAnalyzer(String dataset_path) throws IOException {
    movies = readMovies(dataset_path);

  }


  public class MapSort {

    private static Comparator<Map.Entry> compKeyAsc = (Map.Entry o1, Map.Entry o2) -> ((Comparable) o1.getKey()).compareTo(o2.getKey());

    private static Comparator<Map.Entry> compKeyDesc = (Map.Entry o1, Map.Entry o2) -> ((Comparable) o2.getKey()).compareTo(o1.getKey());


    private static Comparator<Map.Entry> compValueAsc = (Map.Entry o1, Map.Entry o2) -> ((Comparable) o1.getValue()).compareTo(o2.getValue());


    private static Comparator<Map.Entry> compValueDesc = (Map.Entry o1, Map.Entry o2) -> ((Comparable) o2.getValue()).compareTo(o1.getValue());

    private static Comparator<Map.Entry> compForTopMovies = (Map.Entry o1, Map.Entry o2) -> {
      if (((Comparable) o2.getValue()).compareTo(o1.getValue())!=0) {
        return ((Comparable) o2.getValue()).compareTo(o1.getValue());
      }else return ((Comparable) o2.getKey()).compareTo(o1.getKey());
    };
    public static <K, V> Map<K, V> sortByKeyAsc(Map<K, V> originMap) {
      if (originMap == null) {
        return null;
      }
      return sort(originMap, compKeyAsc);
    }

    public static <K, V> Map<K, V> sortByKeyDesc(Map<K, V> originMap) {
      if (originMap == null) {
        return null;
      }
      return sort(originMap, compKeyDesc);
    }

    public static <K, V> Map<K, V> sortByValueAsc(Map<K, V> originMap) {
      if (originMap == null) {
        return null;
      }
      return sort(originMap, compValueAsc);
    }

    public static <K, V> Map<K, V> sortByValueDesc(Map<K, V> originMap) {
      if (originMap == null) {
        return null;
      }
      return sort(originMap, compValueDesc);
    }
    public static <K, V> Map<K, V> sortForTopMovies(Map<K, V> originMap) {
      if (originMap == null) {
        return null;
      }
      return sort(originMap, compForTopMovies);
    }

    private static <K, V> Map<K, V> sort(Map<K, V> originMap, Comparator<Map.Entry> comparator) {
      return originMap.entrySet()
              .stream()
              .sorted(comparator)
              .collect(
                      Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                              LinkedHashMap::new));
    }

  }

  public Map<Integer, Integer> getMovieCountByYear() {

    Map<Integer, Integer> MovieCountByYear = movies.collect(Collectors.groupingBy(Movie::getReleased_Year,Collectors.summingInt(x -> 1)));


    return MapSort.sortByKeyDesc(MovieCountByYear);
  }

  public Map<String, Integer> getMovieCountByGenre(){
//    Map<String, Integer> MovieCountByGenre = movies.collect(Collectors.groupingBy(,Collectors.summingInt(x -> 1)))
    Map<String, Integer> MovieCountByGenre = new HashMap<>();
    movies.forEach(a -> {
      String[] genre = a.getGenre();
      for (int i = 0; i < genre.length; i++) {
        if (MovieCountByGenre.containsKey(genre[i]))
          MovieCountByGenre.put(genre[i],MovieCountByGenre.get(genre[i])+1);
        else
          MovieCountByGenre.put(genre[i], 1);
      }
    });

    return MapSort.sortByValueDesc(MovieCountByGenre);
  }


  public Map<List<String>, Integer> getCoStarCount(){
    Map<List<String>, Integer> CoStarCount = new HashMap<>();

    movies.forEach(a->{
      String[] name = new String[4];
      name[0] = a.getStar1();
      name[1] = a.getStar2();
      name[2] = a.getStar3();
      name[3] = a.getStar4();
      for (int i = 0; i < 4; i++) {
        for (int j = i+1; j < 4; j++) {
          if (CoStarCount.containsKey(getSortedList(name[i],name[j]))){
            CoStarCount.put(getSortedList(name[i],name[j]),CoStarCount.get(getSortedList(name[i],name[j]))+1);

          }else
            CoStarCount.put(getSortedList(name[i],name[j]),1);

        }
      }
    });

    return CoStarCount;
  }

  public List<String> getSortedList(String s1, String s2){
    List<String> list = new ArrayList<>();
    if (s1.compareTo(s2)>0){
      list.add(s2);
      list.add(s1);
    }else {
      list.add(s1);
      list.add(s2);
    }

    return list;
  }

  public List<String> getTopMovies(int top_k, String by){
    List<String> res = new ArrayList<>();
    if (by.equals("runtime")){

      Map<String, Integer> runTime = new HashMap<>();
      movies.forEach(a -> {
        runTime.put(a.getSeries_Title(), a.getRuntime());
      });
      Map<String, Integer> finalRunTime = MapSort.sortForTopMovies(runTime);
//      System.out.println(finalRunTime);
      int cnt = 0;
      for (String s:finalRunTime.keySet()
           ) {
        res.add(s);
        cnt++;
        if (cnt==top_k)
          break;

      }


    }
    else if (by.equals("overview")){
      Map<String, String> overView = new HashMap<>();
      movies.forEach(a -> {
        overView.put(a.getSeries_Title(), a.getOverview());
      });
      Map<String, String> finalOverView = MapSort.sortByValueDesc(overView);

      int cnt = 0;
      for (String s:finalOverView.keySet()
      ) {
        res.add(s);
        cnt++;
        if (cnt==top_k)
          break;

      }
    }
    return res;
  }

  public List<String> getTopStars(int top_k, String by){

  }
}