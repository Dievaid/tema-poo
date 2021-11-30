package models.playable;

import entertainment.Season;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Series extends Video {
  private int numberOfSeasons;
  private ArrayList<Season> seasons;

  public Series(final String title, final int year, final ArrayList<String> cast,
      final ArrayList<String> genres, final int numberOfSeasons, final ArrayList<Season> seasons) {
    super(title, year, cast, genres);
    this.numberOfSeasons = numberOfSeasons;
    this.seasons = seasons;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Double average() {
    double rating = 0.0;

    for (Season season : seasons) {
      rating += this.seasonRating(season);
    }

    if (rating == 0.0) {
      return null;
    }

    return rating / numberOfSeasons;
  }

  /**
   * Calculates the average rating for a given season of a Tv Series
   * @param season the input season of the series
   * @return average rating of the season
   */
  public Double seasonRating(final Season season) {
    double avg = 0.0;
    for (var rating : season.getRatings()) {
      avg += rating;
    }
    if (season.getRatings().size() != 0) {
      return avg / season.getRatings().size();
    }
    return 0.0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int duration() {
    int duration = 0;
    for (var season : this.seasons) {
      duration += season.getDuration();
    }
    return duration;
  }
}
