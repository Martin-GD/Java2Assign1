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
  private Set<Movie> movies;

  public static class Movie {
    private String URL;
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

    public Movie(String URL, String series_Title, int released_Year, String certificate,
                 int runtime, String[] genre, float IMDB_Rating, String overview,
                 int meta_score, String director, String star1, String star2,
                 String star3, String star4, long no_of_Votes, long gross) {
      this.URL = URL;
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

    public String getURL() {
      return URL;
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

  public static Set<Movie> readMovies(String filename) throws IOException {
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
      movieSet.add(new Movie(a[0], checkString(a[1]), Integer.parseInt(a[2]), a[3],
              Integer.parseInt(a[4].split(" ")[0]),
              gen,
              a[6].equals("")?0:Float.parseFloat(a[6]),
              checkString(a[7]),
              a[8].equals("")? 0: Integer.parseInt(a[8]), a[9], a[10], a[11], a[12], a[13],
              a[14].equals("") ? 0:Long.parseLong(a[14]),
              a[15].equals("") ? 0 :Long.parseLong(a[15].replace(",", "").replace("\"", ""))
      ));
      cur = sb.readLine();
    }

    return movieSet;
  }


  public MovieAnalyzer(String dataset_path) throws IOException {
    movies = readMovies(dataset_path);

  }

  public String findTitle(String url){
    List<String> s = new ArrayList<>();
    movies.stream().forEach(a->{
      if (url.equals(a.getURL()))
        s.add(a.getSeries_Title());
    });
    return s.get(0);
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
    private static Comparator<Map.Entry> compForGenre = (Map.Entry o1, Map.Entry o2) -> {
      if (((Comparable) o2.getValue()).compareTo(o1.getValue())!=0) {
        return ((Comparable) o2.getValue()).compareTo(o1.getValue());
      }else return ((Comparable) o1.getKey()).compareTo(o2.getKey());
    };


    private static Comparator<Map.Entry> compForQ4Overview = (Map.Entry o1, Map.Entry o2) -> {
      if ( o2.getValue().toString().length()-o1.getValue().toString().length()!=0) {
        return o2.getValue().toString().length()-o1.getValue().toString().length();
      }else return ((Comparable) o1.getKey()).compareTo(o2.getKey());
    };

    private static Comparator<Map.Entry> compForQ4Runtime = (Map.Entry o1, Map.Entry o2) -> {
      if (((Comparable) o2.getValue()).compareTo(o1.getValue())!=0) {
        return ((Comparable) o2.getValue()).compareTo(o1.getValue());
      }else return ((Comparable) o1.getKey()).compareTo(o2.getKey());
    };

    private static Comparator<Map.Entry> compForQ5Rating = (Map.Entry o1, Map.Entry o2) -> {
      if (((Comparable) o2.getValue()).compareTo(o1.getValue())!=0) {
        return ((Comparable) o2.getValue()).compareTo(o1.getValue());
      }else return ((Comparable) o1.getKey()).compareTo(o2.getKey());
    };
    private static Comparator<Map.Entry> compForQ5Gross = (Map.Entry o1, Map.Entry o2) -> {
      if (((Comparable) o2.getValue()).compareTo(o1.getValue())!=0) {
        return ((Comparable) o2.getValue()).compareTo(o1.getValue());
      }else return ((Comparable) o1.getKey()).compareTo(o2.getKey());
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

  public static String checkString(String s){
    if (s.charAt(0)=='\"'){
      return String.valueOf(s.toCharArray(),1,s.length()-2);
    }
    return s;
  }
  public Map<Integer, Integer> getMovieCountByYear() {

    Map<Integer, Integer> MovieCountByYear = movies.stream().collect(Collectors.groupingBy(Movie::getReleased_Year,Collectors.summingInt(x -> 1)));


    return MapSort.sortByKeyDesc(MovieCountByYear);
  }

  public Map<String, Integer> getMovieCountByGenre(){
//    Map<String, Integer> MovieCountByGenre = movies.collect(Collectors.groupingBy(,Collectors.summingInt(x -> 1)))
    Map<String, Integer> MovieCountByGenre = new HashMap<>();
    movies.stream().forEach(a -> {
      String[] genre = a.getGenre();
      for (int i = 0; i < genre.length; i++) {
        if (MovieCountByGenre.containsKey(genre[i]))
          MovieCountByGenre.put(genre[i],MovieCountByGenre.get(genre[i])+1);
        else
          MovieCountByGenre.put(genre[i], 1);
      }
    });

    return MapSort.sort(MovieCountByGenre,MapSort.compForGenre);
  }


  public Map<List<String>, Integer> getCoStarCount(){
    Map<List<String>, Integer> CoStarCount = new HashMap<>();

    movies.stream().forEach(a->{
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
      movies.stream().forEach(a -> {
        runTime.put(a.getURL(), a.getRuntime());
      });

      List<String[]> ans = new ArrayList<>();
      Map<String, Integer> URLRunTime = MapSort.sort(runTime,MapSort.compForQ4Runtime);
//      Map<String, Integer> trans = new HashMap<>();
//      System.out.println(finalRunTime);


      for (Map.Entry<String, Integer> s:URLRunTime.entrySet()
           ) {
        ans.add(new String[]{findTitle(s.getKey()),""+s.getValue()});
//        trans.put(findTitle(s.getKey()),s.getValue());
      }

//      Map<String, Integer> finalRunTime = MapSort.sort(trans,MapSort.compForQ4Runtime);
      Collections.sort(ans, new Comparator<String[]>() {
        @Override
        public int compare(String[] s1, String[] s2) {
          if (Integer.parseInt(s2[1])-Integer.parseInt(s1[1])!=0)
            return Integer.parseInt(s2[1])-Integer.parseInt(s1[1]);
          else return s1[0].compareTo(s2[0]);
        }
      });
      for (int i = 0; i < top_k; i++) {
        res.add(ans.get(i)[0]);
      }

    }
    else if (by.equals("overview")){
      Map<String, String> overView = new HashMap<>();
      movies.stream().forEach(a -> {
        overView.put(a.getURL(), a.getOverview());
      });
      Map<String, String> trans = new HashMap<>();
//      System.out.println(finalRunTime);


      for (Map.Entry<String, String> s:overView.entrySet()
      ) {
        trans.put(findTitle(s.getKey()),s.getValue());
      }

      Map<String, String> finalOverView = MapSort.sort(trans,MapSort.compForQ4Overview);

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

  public List<String> movieTitle(String s1, String s2){
    List<String> res = new ArrayList<>();
    res.add(s1);
    res.add(s2);
    return res;
  }

  public double[] countRating(double[] doubles, float rating){
    doubles[0] += 1;
    doubles[1] += rating;
    return doubles;
  }
  public double[] countGross(double[] doubles, long gross){
    doubles[0] += 1;
    doubles[1] += gross;
    return doubles;
  }
  public List<String> getTopStars(int top_k, String by){
    List<String> res = new ArrayList<>();
    if (by.equals("rating")){
      Map<String, double[]> starsRating = new HashMap<>();
      movies.stream().forEach(a -> {
        String[] name = new String[4];
        name[0] = a.getStar1();
        name[1] = a.getStar2();
        name[2] = a.getStar3();
        name[3] = a.getStar4();

        for (int i = 0; i < 4; i++) {
          if (starsRating.containsKey(name[i])){
            starsRating.put(name[i],countRating(starsRating.get(name[i]),a.getIMDB_Rating()));
          }else {
            starsRating.put(name[i],new double[]{1,a.getIMDB_Rating()});
          }
        }
      });
      Map<String, Double> avgRating = new HashMap<>();

      for(Map.Entry<String, double[]> entry : starsRating.entrySet()){
        avgRating.put(entry.getKey(),entry.getValue()[1]/entry.getValue()[0]);
      }
      Map<String, Double> finalAvgRating = new HashMap<>();
      finalAvgRating = MapSort.sort(avgRating,MapSort.compForQ5Rating);
      int cnt = 0;
      for (String s:finalAvgRating.keySet()
      ) {
        res.add(s);
        cnt++;
        if (cnt==top_k)
          break;
      }
    }else if (by.equals("gross")){
      Map<String, double[]> starsGross = new HashMap<>();
      movies.stream().forEach(a -> {
        String[] name = new String[4];
        name[0] = a.getStar1();
        name[1] = a.getStar2();
        name[2] = a.getStar3();
        name[3] = a.getStar4();

        for (int i = 0; i < 4; i++) {
          if (starsGross.containsKey(name[i])){
            if (a.getGross()!=0)
              starsGross.put(name[i],countGross(starsGross.get(name[i]),a.getGross()));
          }else {
            if (a.getGross()!=0)
              starsGross.put(name[i],new double[]{1,a.getGross()});
          }
        }
      });
      Map<String, Double> avgGross = new HashMap<>();

      for(Map.Entry<String, double[]> entry : starsGross.entrySet()){
        avgGross.put(entry.getKey(),entry.getValue()[1]/entry.getValue()[0]);
      }
      Map<String, Double> finalAvgGross = new HashMap<>();
      finalAvgGross = MapSort.sort(avgGross, MapSort.compForQ5Gross);
      int cnt = 0;
      for (String s:finalAvgGross.keySet()
      ) {
        res.add(s);
        cnt++;
        if (cnt==top_k)
          break;
      }
    }
    return res;
  }
  public boolean checkMovies(String genre, String[] curArr){
    for (int i = 0; i < curArr.length; i++) {
      if (genre.equals(curArr[i]))
        return true;
    }
    return false;
  }
  public List<String> searchMovies(String genre, float min_rating, int max_runtime){
    List<String> res = new ArrayList<>();
    movies.stream().forEach(a -> {
      if (checkMovies(genre,a.getGenre()) && a.getIMDB_Rating()>=min_rating && a.getRuntime()<=max_runtime && a.getRuntime()!=0 && a.getIMDB_Rating()!=0)
        res.add(a.getSeries_Title());
    });
    Collections.sort(res, new Comparator<String>() {
      @Override
      public int compare(String s1, String s2) {
        return s1.compareTo(s2);
      }
    });

    return res;
  }
}