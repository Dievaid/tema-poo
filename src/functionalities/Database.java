package functionalities;

import fileio.ActionInputData;
import fileio.Input;
import functionalities.data.Data;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import models.Actor;
import models.User;
import models.playable.Movie;
import models.playable.Series;
import models.playable.Video;

@Getter
public final class Database {
  private static final Database instance = new Database();

  private List<ActionInputData> actions;

  private List<Actor> actors;
  private List<User> users;
  private List<Video> videos;
  private List<Movie> movies;
  private List<Series> series;

  private Database() { }


  /**
   * Return the instance of eager singleton
   * @return current instance of the Database
   */
  public static Database getInstance() {
    if (instance == null) {
      return new Database();
    }
    return instance;
  }

  /**
   * Filling fields of the singleton from the input
   * @param input represents the data we want to consume
   */
  public void fetchData(final Input input) {
    actions = input.getCommands();
    actors = Data.importActorData(input);
    users = Data.importUserData(input);
    movies = Data.importMovieData(input);
    series = Data.importSeriesData(input);

    videos = new ArrayList<>();
    videos.addAll(movies);
    videos.addAll(series);
  }
}
