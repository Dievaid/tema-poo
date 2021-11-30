package functionalities.data;

import fileio.Input;
import java.util.ArrayList;
import java.util.HashMap;
import models.Actor;
import models.User;
import models.playable.Movie;
import models.playable.Series;

public final class Data {
  private Data() { }

  /**
   *
   * @param input contains the data we want to copy
   * @return ArrayList of User from the input given
   */
  public static ArrayList<User> importUserData(final Input input) {
    ArrayList<User> users = new ArrayList<>();
    for (var user : input.getUsers()) {
      var newUser = new User(
          user.getUsername(),
          user.getSubscriptionType(),
          user.getHistory(),
          user.getFavoriteMovies(),
          new HashMap<>()
      );
      users.add(newUser);
    }
    return users;
  }

  /**
   *
   * @param input contains the data we want to copy
   * @return ArrayList of Actor from the input given
   */
  public static ArrayList<Actor> importActorData(final Input input) {
    ArrayList<Actor> actors = new ArrayList<>();
    for (var actor : input.getActors()) {
      var newActor = new Actor(
          actor.getName(),
          actor.getCareerDescription(),
          actor.getFilmography(),
          actor.getAwards()
      );
      actors.add(newActor);
    }
    return actors;
  }

  /**
   *
   * @param input contains the data we want to copy
   * @return ArrayList of Movie from the input given
   */
  public static ArrayList<Movie> importMovieData(final Input input) {
    ArrayList<Movie> movies = new ArrayList<>();
    for (var movie : input.getMovies()) {
      var newMovie = new Movie(
          movie.getTitle(),
          movie.getYear(),
          movie.getCast(),
          movie.getGenres(),
          movie.getDuration()
      );
      movies.add(newMovie);
    }
    return movies;
  }

  /**
   *
   * @param input contains the data we want to copy
   * @return ArrayList of Series from the input given
   */
  public static ArrayList<Series> importSeriesData(final Input input) {
    ArrayList<Series> series = new ArrayList<>();
    for (var s : input.getSerials()) {
      var newSeries = new Series(
          s.getTitle(),
          s.getYear(),
          s.getCast(),
          s.getGenres(),
          s.getNumberSeason(),
          s.getSeasons()
      );
      series.add(newSeries);
    }
    return series;
  }
}
