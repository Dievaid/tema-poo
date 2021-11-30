package models.comparators;

public final class UserComparator {
  private String username;
  private int numberOfFavourites;

  public UserComparator(String username, int numberOfFavourites) {
    this.username = username;
    this.numberOfFavourites = numberOfFavourites;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getNumberOfFavourites() {
    return numberOfFavourites;
  }

  public void setNumberOfFavourites(int numberOfFavourites) {
    this.numberOfFavourites = numberOfFavourites;
  }

  @Override
  public String toString() {
    return getUsername();
  }
}
