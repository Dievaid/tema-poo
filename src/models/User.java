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

  /**
   *
   * @return username of the current instance
   */
  public String getUsername() {
    return username;
  }

  /**
   *
   * @return subscription type of the current instance
   */
  public String getSubscription() {
    return subscription;
  }

  /**
   *
   * @return history of the current user instance
   */
  public Map<String, Integer> getHistory() {
    return history;
  }

  /**
   *
   * @return favourite video titles of the current user instance
   */
  public ArrayList<String> getFavorites() {
    return favorites;
  }

  /**
   *
   * @return map of rated video titles
   */
  public Map<String, Integer> getRated() {
    return rated;
  }

  /**
   *
   * @param username modifies the username
   */
  public void setUsername(final String username) {
    this.username = username;
  }

  /**
   *
   * @param subscription modifies the subscription type
   */
  public void setSubscription(final String subscription) {
    this.subscription = subscription;
  }

  /**
   *
   * @param history modifies the history of the user instance
   */
  public void setHistory(final Map<String, Integer> history) {
    this.history = history;
  }

  /**
   *
   * @param favorites modifies the favourite list of the user
   */
  public void setFavorites(final ArrayList<String> favorites) {
    this.favorites = favorites;
  }

  /**
   *
   * @param rated modifies the rated videos map
   */
  public void setRated(final Map<String, Integer> rated) {
    this.rated = rated;
  }
}
