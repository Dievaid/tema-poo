package models;

import java.util.ArrayList;
import java.util.Map;

public class User {
  private String username;
  private String subscription;
  private Map<String, Integer> history;
  private ArrayList<String> favorites;
  private Map<String, Integer> rated;

  public User(final String username, final String subscription,
      final Map<String, Integer> history, final ArrayList<String> favorites,
      final Map<String, Integer> rated) {
    this.username = username;
    this.subscription = subscription;
    this.history = history;
    this.favorites = favorites;
    this.rated = rated;
  }

  public String getUsername() {
    return username;
  }

  public String getSubscription() {
    return subscription;
  }

  public Map<String, Integer> getHistory() {
    return history;
  }

  public ArrayList<String> getFavorites() {
    return favorites;
  }

  public Map<String, Integer> getRated() {
    return rated;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setSubscription(String subscription) {
    this.subscription = subscription;
  }

  public void setHistory(Map<String, Integer> history) {
    this.history = history;
  }

  public void setFavorites(ArrayList<String> favorites) {
    this.favorites = favorites;
  }

  public void setRated(Map<String, Integer> rated) {
    this.rated = rated;
  }
}
