package models;

import actor.ActorsAwards;
import functionalities.Database;
import java.util.ArrayList;
import java.util.Map;

public final class Actor {

  private String name;
  private String careerDescription;
  private ArrayList<String> filmography;
  private Map<ActorsAwards, Integer> awards;

  public Actor(final String name, final String careerDescription,
      final ArrayList<String> filmography, final Map<ActorsAwards, Integer> awards) {
    this.name = name;
    this.careerDescription = careerDescription;
    this.filmography = filmography;
    this.awards = awards;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCareerDescription() {
    return careerDescription;
  }

  public void setCareerDescription(String careerDescription) {
    this.careerDescription = careerDescription;
  }

  public ArrayList<String> getFilmography() {
    return filmography;
  }

  public void setFilmography(ArrayList<String> filmography) {
    this.filmography = filmography;
  }

  public Map<ActorsAwards, Integer> getAwards() {
    return awards;
  }

  public void setAwards(Map<ActorsAwards, Integer> awards) {
    this.awards = awards;
  }

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
