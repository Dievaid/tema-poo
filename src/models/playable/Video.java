package models.playable;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class Video {
  private String title;
  private int year;
  private ArrayList<String> cast;
  private ArrayList<String> genres;

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
