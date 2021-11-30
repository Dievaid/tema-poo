package models.comparators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class VideoDoubleComparator {
  private String title;
  private Double average;

  @Override
  public String toString() {
    return getTitle();
  }
}
