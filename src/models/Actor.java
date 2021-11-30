package models;

import actor.ActorsAwards;
import functionalities.Database;
import java.util.ArrayList;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class Actor {

  private String name;
  private String careerDescription;
  private ArrayList<String> filmography;
  private Map<ActorsAwards, Integer> awards;

  /**
   * Calculates the number of awards of the current Actor instance
   * @return number of awards for the current instance
   */
  public int numberOfAwards() {
    int awards = 0;
    for (var award : this.awards.entrySet()) {
      awards += award.getValue();
    }
    return awards;
  }

  /**
   * Calculates the average rating of all the videos
   * In which the current instance of Actor is being cast to
   * @return average rating
   */
  public Double averageActor() {
    double average = 0.0;
    int count = 0;
    for (var video : Database.getInstance().getVideos()) {
      if (filmography.contains(video.getTitle()) && video.average() != null) {
        average += video.average();
        count++;
      }
    }

    if (count == 0) {
      return null;
    }

    average /= count;
    return average;
  }

  @Override
  public String toString() {
    return getName();
  }
}
