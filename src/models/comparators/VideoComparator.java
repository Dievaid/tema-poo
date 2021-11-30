package models.comparators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class VideoComparator {
  private String title;
  private int numberOfFavorites;
}
