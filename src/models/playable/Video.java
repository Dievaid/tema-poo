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

  /**
   *
   * @return title of current instance
   */
  public String getTitle() {
    return title;
  }

  /**
   *
   * @param title title to be changed
   */
  public void setTitle(final String title) {
    this.title = title;
  }

  /**
   *
   * @return year of current instance
   */
  public int getYear() {
    return year;
  }

  /**
   *
   * @param year to be changed with
   */
  public void setYear(final int year) {
    this.year = year;
  }

  /**
   *
   * @return Cast array of current instance
   */
  public ArrayList<String> getCast() {
    return cast;
  }

  /**
   *
   * @param cast list to be modified with
   */
  public void setCast(final ArrayList<String> cast) {
    this.cast = cast;
  }

  /**
   *
   * @return Genre array current instance
   */
  public ArrayList<String> getGenres() {
    return genres;
  }

  /**
   *
   * @param genres list to be modified with
   */
  public void setGenres(final ArrayList<String> genres) {
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
