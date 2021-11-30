package models.playable;

import java.util.ArrayList;

public abstract class Video {
  private String title;
  private int year;
  private ArrayList<String> cast;
  private ArrayList<String> genres;

  public Video(final String title, final int year, final ArrayList<String> cast,
      final ArrayList<String> genres) {
    this.title = title;
    this.year = year;
    this.cast = cast;
    this.genres = genres;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public ArrayList<String> getCast() {
    return cast;
  }

  public void setCast(ArrayList<String> cast) {
    this.cast = cast;
  }

  public ArrayList<String> getGenres() {
    return genres;
  }

  public void setGenres(ArrayList<String> genres) {
    this.genres = genres;
  }

  /**
   * Abstract method for calculating:
   *  -> the average rating for a Series
   *  -> the average rating for a Movie
   * @return average
   */
  public abstract Double average();

  /**
   * Abstract method for calculating:
   *  -> duration of a Movie
   *  -> duration of a Series
   * @return duration
   */
  public abstract int duration();
}
