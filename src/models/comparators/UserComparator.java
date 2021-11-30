package models.comparators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class UserComparator {
  private String username;
  private int numberOfFavourites;

  @Override
  public String toString() {
    return getUsername();
  }
}
