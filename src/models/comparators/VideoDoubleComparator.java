package models.comparators;

public final class VideoDoubleComparator {
  private String title;
  private Double average;

  public VideoDoubleComparator(String title, Double average) {
    this.title = title;
    this.average = average;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Double getAverage() {
    return average;
  }

  public void setAverage(Double average) {
    this.average = average;
  }

  @Override
  public String toString() {
    return getTitle();
  }
}
