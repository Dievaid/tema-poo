package models.comparators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class GenreComparator {
  private String genre;
  private int popularity;
}
