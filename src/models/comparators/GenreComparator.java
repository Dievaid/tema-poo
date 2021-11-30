package models.comparators;

public final class GenreComparator {
  private String genre;
  private int popularity;

  public GenreComparator(String genre, int popularity) {
    this.genre = genre;
    this.popularity = popularity;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public int getPopularity() {
    return popularity;
  }

  public void setPopularity(int popularity) {
    this.popularity = popularity;
  }
}
