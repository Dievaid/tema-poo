package models.playable;

import java.util.ArrayList;

public class Movie extends Video {
  private int duration;
  private ArrayList<Double> ratings;

  public Movie(final String title, final int year,
      final ArrayList<String> cast, final ArrayList<String> genres, int duration) {
    super(title, year, cast, genres);
    this.duration = duration;
    this.ratings = new ArrayList<>();
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public ArrayList<Double> getRatings() {
    return ratings;
  }

  public void setRatings(ArrayList<Double> ratings) {
    this.ratings = ratings;
  }

  /**
   * Iterates through a rating list and gets the average rating for this movie instance
   * @return Double representing the average rating of a movie
   */
  @Override
  public Double average() {
    Double rating = 0.0;

    if (ratings.isEmpty()) {
      return null;
    }

    for (var r : ratings) {
      rating += r;
    }

    return rating / ratings.size();
  }

  /**
   * Calls getter because the duration is already known
    @return duration of a Movie
   */
  @Override
  public int duration() {
    return getDuration();
  }
}
