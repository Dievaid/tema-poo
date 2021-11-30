package models.comparators;

public final class VideoComparator {
  private String title;
  private int numberOfFavorites;

  public VideoComparator(String title, int numberOfFavorites) {
    this.title = title;
    this.numberOfFavorites = numberOfFavorites;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getNumberOfFavorites() {
    return numberOfFavorites;
  }

  public void setNumberOfFavorites(int numberOfFavorites) {
    this.numberOfFavorites = numberOfFavorites;
  }
}
