package functionalities;

import fileio.ActionInputData;
import fileio.Input;
import functionalities.data.Data;
import java.util.ArrayList;
import java.util.List;
import models.Actor;
import models.User;
import models.playable.Movie;
import models.playable.Series;
import models.playable.Video;

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
   *
   * @return gets the Action List
   */
  public List<ActionInputData> getActions() {
    return actions;
  }

  /**
   *
   * @param actions modifies the action list
   */
  public void setActions(final List<ActionInputData> actions) {
    this.actions = actions;
  }

  /**
   *
   * @return gets the actor list
   */
  public List<Actor> getActors() {
    return actors;
  }

  /**
   *
   * @param actors list of actors to be modified with
   */
  public void setActors(final List<Actor> actors) {
    this.actors = actors;
  }

  /**
   *
   * @return gets the user list
   */
  public List<User> getUsers() {
    return users;
  }

  /**
   *
   * @param users list of users to be modified with
   */
  public void setUsers(final List<User> users) {
    this.users = users;
  }

  /**
   *
   * @return gets a list with all videos
   */
  public List<Video> getVideos() {
    return videos;
  }

  /**
   *
   * @param videos Video list to modify with
   */
  public void setVideos(final List<Video> videos) {
    this.videos = videos;
  }

  /**
   *
   * @return gets a list of all movies
   */
  public List<Movie> getMovies() {
    return movies;
  }

  /**
   *
   * @param movies list of movies to modify with
   */
  public void setMovies(final List<Movie> movies) {
    this.movies = movies;
  }

  /**
   *
   * @return gets a list of all series
   */
  public List<Series> getSeries() {
    return series;
  }

  /**
   *
   * @param series list of series to modify with
   */
  public void setSeries(final List<Series> series) {
    this.series = series;
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
