package functionalities.tasks;

import fileio.ActionInputData;
import fileio.Writer;
import functionalities.DataProcessor;
import functionalities.Database;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Actor;
import models.comparators.UserComparator;
import models.comparators.VideoComparator;
import models.playable.Video;
import org.json.simple.JSONObject;

public final class Query {
  private Query() { }

  /**
   *
   * @param action represents the query data
   * @param writer helper for returning JSON Object
   * @return JSON Object representing the result of the query
   * @throws IOException if there is something wrong with the file
   */
  public static JSONObject getActorAverage(final ActionInputData action, final Writer writer)
      throws IOException {
    var actors = Database.getInstance().getActors();
    List<Actor> actorsList = new ArrayList<>();

    for (var actor : actors) {
      if (actor.averageActor() != null) {
        actorsList.add(actor);
      }
    }

    actorsList.sort(Comparator.comparingDouble(Actor::averageActor).thenComparing(Actor::getName));

    String output;
    if (action.getSortType().equals("desc")) {
      Collections.reverse(actorsList);
    }

    if (action.getNumber() >= actorsList.size()) {
      output = "Query result: " + actorsList;
    } else {
      output = "Query result: " + actorsList.subList(0, action.getNumber());
    }

    return writer.writeFile(action.getActionId(), output, output);
  }

  /**
   *
   * @param action represents the query data
   * @param writer helper for returning JSON Object
   * @return JSON Object representing the result of the query
   * @throws IOException if there is something wrong with the file
   */
  public static JSONObject getActorAwards(final ActionInputData action, final Writer writer)
      throws IOException {
    List<Actor> actorsQuery = DataProcessor.filterActors(action);

    actorsQuery.sort((x, y) -> {
      switch (action.getSortType()) {
        case "asc" -> {
          if (x.numberOfAwards() == y.numberOfAwards()) {
            return x.getName().compareTo(y.getName());
          }
          return x.numberOfAwards() - y.numberOfAwards();
        }
        case "desc" -> {
          if (x.numberOfAwards() == y.numberOfAwards()) {
            return y.getName().compareTo(x.getName());
          }
          return y.numberOfAwards() - x.numberOfAwards();
        }
        default -> {
          return 0;
        }
      }
    });

    List<String> actorsQ = new ArrayList<>();
    actorsQuery.forEach(actor -> actorsQ.add(actor.getName()));

    String outputMessage = null;

    if (action.getNumber() >= actorsQ.size()) {
      outputMessage =  "Query result: " + actorsQ;
    } else {
      outputMessage = "Query result: " + actorsQ.subList(0, action.getNumber());
    }
    return writer.writeFile(action.getActionId(), outputMessage, outputMessage);
  }

  /**
   *
   * @param action represents the query data
   * @param writer helper for returning JSON Object
   * @return JSON Object representing the result of the query
   * @throws IOException if there is something wrong with the file
   */
  public static JSONObject getFilterActorsDescription(final ActionInputData action,
      final Writer writer) throws IOException {
    List<Actor> actorsQuery = DataProcessor.filterActors(action);
    actorsQuery.sort(Comparator.comparing(Actor::getName));
    String output = "Query result: []";

    List<String> actorsQ = new ArrayList<>();
    actorsQuery.forEach(actor -> actorsQ.add(actor.getName()));

    if (!action.getSortType().equals("asc")) {
      Collections.reverse(actorsQ);
    }

    if (action.getNumber() >= actorsQ.size()) {
      output = "Query result: " + actorsQ;
    } else {
      output = "Query result: " + actorsQ.subList(0, action.getActionId());
    }
    return writer.writeFile(action.getActionId(), output, output);
  }

  /**
   *
   * @param action represents the query data
   * @param writer helper for returning JSON Object
   * @return JSON Object representing the result of the query
   * @throws IOException if there is something wrong with the file
   */
  public static JSONObject getVideosByRating(final ActionInputData action, final Writer writer)
      throws IOException {
    List<Video> filteredVideos = DataProcessor.filterVideos(action);

    filteredVideos.removeIf(video -> video.average() == null);

    Map<String, Double> ratingMap = new HashMap<>();

    for (var video : filteredVideos) {
      ratingMap.put(video.getTitle(), video.average());
    }

    var sortedByValueMap = DataProcessor.sortByValue(ratingMap);

    ArrayList<String> sortedVideos = new ArrayList<>(sortedByValueMap.keySet());
    if (action.getSortType().equals("desc")) {
      Collections.reverse(sortedVideos);
    }

    String outputMessage;
    if (action.getNumber() >= sortedVideos.size()) {
      outputMessage = "Query result: " + sortedVideos;
    } else {
      outputMessage = "Query result: " + sortedVideos.subList(0, action.getNumber());
    }
    return writer.writeFile(action.getActionId(), outputMessage, outputMessage);
  }

  /**
   *
   * @param action represents the query data
   * @param writer helper for returning JSON Object
   * @return JSON Object representing the result of the query
   * @throws IOException if there is something wrong with the file
   */
  public static JSONObject getMostAddedToFavourite(final ActionInputData action,
      final Writer writer) throws IOException {

    var videos = DataProcessor.filterVideos(action);
    ArrayList<VideoComparator> favourites = new ArrayList<>();

    for (var video: videos) {
      int favouriteCounter = 0;
      for (var user : Database.getInstance().getUsers()) {
        if (user.getFavorites().contains(video.getTitle())) {
          favouriteCounter++;
        }
      }

      if (favouriteCounter != 0) {
        favourites.add(new VideoComparator(video.getTitle(), favouriteCounter));
      }
    }

    favourites.sort(Comparator.comparingInt(VideoComparator::getNumberOfFavorites)
        .thenComparing(VideoComparator::getTitle));

    ArrayList<String> sortedFavourites = new ArrayList<>();
    for (var fav : favourites) {
      sortedFavourites.add(fav.getTitle());
    }

    if (action.getSortType().equals("desc")) {
      Collections.reverse(sortedFavourites);
    }

    String outputMessage;
    if (action.getNumber() >= sortedFavourites.size()) {
      outputMessage = "Query result: " + sortedFavourites;
    } else {
      outputMessage = "Query result: " + sortedFavourites.subList(0, action.getNumber());
    }
    return writer.writeFile(action.getActionId(), outputMessage, outputMessage);
  }

  /**
   *
   * @param action represents the query data
   * @param writer helper for returning JSON Object
   * @return JSON Object representing the result of the query
   * @throws IOException if there is something wrong with the file
   */
  public static JSONObject getLongest(final ActionInputData action, final Writer writer)
      throws IOException {
    Map<String, Integer> timeMap = new HashMap<>();

    for (var video : DataProcessor.filterVideos(action)) {
      int time = video.duration();

      if (time != 0) {
        timeMap.put(video.getTitle(), time);
      }
    }

    List<VideoComparator> sortedList = new ArrayList<>();
    for (var entryTimeMap : timeMap.entrySet()) {
      sortedList.add(new VideoComparator(entryTimeMap.getKey(), entryTimeMap.getValue()));
    }

    if (action.getSortType().equals("desc")) {
      sortedList.sort((v1, v2) -> {
        if (v1.getNumberOfFavorites() == v2.getNumberOfFavorites()) {
          return v2.getTitle().compareTo(v1.getTitle());
        }
        return v2.getNumberOfFavorites() - v1.getNumberOfFavorites();
      });
    } else {
      sortedList.sort((v1, v2) -> {
        if (v1.getNumberOfFavorites() == v2.getNumberOfFavorites()) {
          return v1.getTitle().compareTo(v2.getTitle());
        }
        return v1.getNumberOfFavorites() - v2.getNumberOfFavorites();
      });
    }

    ArrayList<String> sortedTime = new ArrayList<>();
    sortedList.forEach(video -> sortedTime.add(video.getTitle()));

    String outputMessage;
    if (action.getNumber() >= sortedTime.size()) {
      outputMessage = "Query result: " + sortedTime;
    } else {
      outputMessage = "Query result: " + sortedTime.subList(0, action.getNumber());
    }
    return writer.writeFile(action.getActionId(), outputMessage, outputMessage);
  }

  /**
   *
   * @param action represents the query data
   * @param writer helper for returning JSON Object
   * @return JSON Object representing the result of the query
   * @throws IOException if there is something wrong with the file
   */
  public static JSONObject getMostViewed(final ActionInputData action, final Writer writer)
      throws IOException {

    Map<String, Integer> totalViewsMap = new HashMap<>();

    for (var video : DataProcessor.filterVideos(action)) {
      int totalViews = 0;
      for (var user : Database.getInstance().getUsers()) {
        if (user.getHistory().containsKey(video.getTitle())) {
          totalViews += user.getHistory().get(video.getTitle());
        }
      }
      if (totalViews != 0) {
        totalViewsMap.put(video.getTitle(), totalViews);
      }
    }

    ArrayList<VideoComparator> sortedList = new ArrayList<>();
    ArrayList<String> queryResult = new ArrayList<>();
    for (var entryViewMap : totalViewsMap.entrySet()) {
      sortedList.add(
          new VideoComparator(entryViewMap.getKey(), entryViewMap.getValue()));
    }

    if (action.getSortType().equals("desc")) {
      sortedList.sort((v1, v2) -> {
        if (v1.getNumberOfFavorites() == v2.getNumberOfFavorites()) {
          return v2.getTitle().compareTo(v1.getTitle());
        }
        return v2.getNumberOfFavorites() - v1.getNumberOfFavorites();
      });
    } else {
      sortedList.sort((v1, v2) -> {
        if (v1.getNumberOfFavorites() == v2.getNumberOfFavorites()) {
          return v1.getTitle().compareTo(v2.getTitle());
        }
        return v1.getNumberOfFavorites() - v2.getNumberOfFavorites();
      });
    }

    sortedList.forEach(video -> queryResult.add(video.getTitle()));

    String outputMessage;
    if (action.getNumber() >= queryResult.size()) {
      outputMessage = "Query result: " + queryResult;
    } else {
      outputMessage = "Query result: " + queryResult.subList(0, action.getNumber());
    }

    return writer.writeFile(action.getActionId(), outputMessage, outputMessage);
  }

  /**
   *
   * @param action represents the query data
   * @param writer helper for returning JSON Object
   * @return JSON Object representing the result of the query
   * @throws IOException if there is something wrong with the file
   */
  public static JSONObject mostDevotedUsers(final ActionInputData action, final Writer writer)
      throws IOException {
    Map<String, Integer> activeUsersMap = new HashMap<>();

    for (var user : Database.getInstance().getUsers()) {
      if (!user.getRated().isEmpty()) {
        activeUsersMap.put(user.getUsername(), user.getRated().size());
      }
    }

    ArrayList<UserComparator> userOrder = new ArrayList<>();

    for (var entryActiveUser : activeUsersMap.entrySet()) {
      userOrder.add(new UserComparator(entryActiveUser.getKey(), entryActiveUser.getValue()));
    }

    userOrder.sort((userNo1, userNo2) -> {
      if (userNo1.getNumberOfFavourites() == userNo2.getNumberOfFavourites()) {
        return userNo2.getUsername().compareTo(userNo1.getUsername());
      }
      return userNo2.getNumberOfFavourites() - userNo1.getNumberOfFavourites();
    });

    if (action.getSortType().equals("asc")) {
      Collections.reverse(userOrder);
    }

    String outputMessage;
    if (action.getNumber() >= userOrder.size()) {
      outputMessage = "Query result: " + userOrder;
    } else {
      outputMessage = "Query result: " + userOrder.subList(0, action.getNumber());
    }
    return writer.writeFile(action.getActionId(), outputMessage, outputMessage);
  }
}
