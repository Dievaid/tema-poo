package functionalities.tasks;

import common.Constants;
import fileio.ActionInputData;
import fileio.Writer;
import functionalities.Database;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.User;
import models.comparators.GenreComparator;
import models.comparators.VideoComparator;
import models.playable.Video;
import org.json.simple.JSONObject;

public final class Recommend {
  private Recommend() { }

  /**
   *
   * @param action represents the needed data for the recommendation
   * @param writer util for generating json object
   * @return json object packing a standard recommendation
   * @throws IOException if there was something wrong with the file writer
   */
  public static JSONObject standard(final ActionInputData action, final Writer writer)
      throws IOException {
    User user = null;
    for (var u : Database.getInstance().getUsers()) {
      if (u.getUsername().equals(action.getUsername())) {
        user = u;
        break;
      }
    }

    String output;
    if (user == null) {
      output = "StandardRecommendation cannot be applied!";
    } else {
      List<Video> videos = new ArrayList<>(Database.getInstance().getVideos());
      User finalUser = user;
      videos.removeIf(video -> finalUser.getHistory().containsKey(video.getTitle()));
      if (videos.isEmpty()) {
        output = "StandardRecommendation cannot be applied!";
        return writer.writeFile(action.getActionId(), output, output);
      }
      output = "StandardRecommendation result: " + videos.get(0).getTitle();
    }
    return writer.writeFile(action.getActionId(), output, output);
  }

  /**
   *
   * @param action represents the needed data for the recommendation
   * @param writer util for generating json object
   * @return json object packing a BestRatedUnseen recommendation
   * @throws IOException if there was something wrong with the file writer
   */
  public static JSONObject bestUnseen(final ActionInputData action, final Writer writer)
      throws IOException {
    User user = null;
    for (var u : Database.getInstance().getUsers()) {
      if (u.getUsername().equals(action.getUsername())) {
        user = u;
        break;
      }
    }

    String output;
    if (user == null) {
      output = "BestRatedUnseenRecommendation cannot be applied!";
    } else {
      List<Video> videos = new ArrayList<>(Database.getInstance().getVideos());
      User finalUser = user;
      videos.removeIf(video -> finalUser.getHistory().containsKey(video.getTitle()));
      if (videos.isEmpty()) {
        output = "BestRatedUnseenRecommendation cannot be applied!";
      } else {
        videos.sort((videoNo1, videoNo2) -> {
          if (videoNo1.average() == null) {
            return 1;
          }
          if (videoNo2.average() == null) {
            return -1;
          }
          return videoNo2.average().compareTo(videoNo1.average());
        });
        output = "BestRatedUnseenRecommendation result: " + videos.get(0).getTitle();
      }
    }
    return writer.writeFile(action.getActionId(), output, output);
  }

  /**
   *
   * @param action represents the needed data for the recommendation
   * @param writer util for generating json object
   * @return json object packing the Popular recommendation
   * @throws IOException if there was something wrong with the file writer
   */
  public static JSONObject popular(final ActionInputData action, final Writer writer)
      throws IOException {
    User user = null;
    for (var u : Database.getInstance().getUsers()) {
      if (u.getUsername().equals(action.getUsername())) {
        user = u;
        break;
      }
    }

    String output = "PopularRecommendation cannot be applied!";
    if (user == null) {
      return writer.writeFile(action.getActionId(), output, output);
    }

    if (user.getSubscription().equals(Constants.BASIC_SUB)) {
      return writer.writeFile(action.getActionId(), output, output);
    }

    List<GenreComparator> genreComparators = new ArrayList<>();
    Map<String, Integer> genreMap = new HashMap<>();

    for (var video : Database.getInstance().getVideos()) {
      for (var genre : video.getGenres()) {
        if (genreMap.containsKey(genre)) {
          int views = genreMap.get(genre) + 1;
          genreMap.replace(genre, views);
        } else {
          genreMap.put(genre, 1);
        }
      }
    }

    for (var genreMapEntry : genreMap.entrySet()) {
      genreComparators.add(
          new GenreComparator(genreMapEntry.getKey(), genreMapEntry.getValue()));
    }

    genreComparators.sort(Comparator.comparingInt(GenreComparator::getPopularity));
    Collections.reverse(genreComparators);

    for (var genre : genreComparators) {
      for (var video : Database.getInstance().getVideos()) {
        if (!user.getHistory().containsKey(video.getTitle())
            && video.getGenres().contains(genre.getGenre())) {
          output = "PopularRecommendation result: " + video.getTitle();
          return writer.writeFile(action.getActionId(), output, output);
        }
      }
    }
    return writer.writeFile(action.getActionId(), output, output);
  }

  /**
   *
   * @param action represents the needed data for the recommendation
   * @param writer util for generating json object
   * @return json object packing the Favourite recommendation
   * @throws IOException if there was something wrong with the file writer
   */
  public static JSONObject favourite(final ActionInputData action, final Writer writer)
      throws IOException {
    User user = null;
    for (var u : Database.getInstance().getUsers()) {
      if (u.getUsername().equals(action.getUsername())) {
        user = u;
        break;
      }
    }

    String output = "FavoriteRecommendation cannot be applied!";

    if (user == null) {
      return writer.writeFile(action.getActionId(), output, output);
    }

    if (user.getSubscription().equals(Constants.BASIC_SUB)) {
      return writer.writeFile(action.getActionId(), output, output);
    }

    Map<String, Integer> favouriteMap = new HashMap<>();

    for (var userExplorer : Database.getInstance().getUsers()) {
      for (var userFavourite : userExplorer.getFavorites()) {
        if (favouriteMap.containsKey(userFavourite)) {
          int views = favouriteMap.get(userFavourite) + 1;
          favouriteMap.replace(userFavourite, views);
        } else {
          favouriteMap.put(userFavourite, 1);
        }
      }
    }

    List<VideoComparator> videoList = new ArrayList<>();
    for (var favouriteMapEntry : favouriteMap.entrySet()) {
      videoList.add(
          new VideoComparator(favouriteMapEntry.getKey(), favouriteMapEntry.getValue()));
    }

    videoList.sort((v1, v2) -> v2.getNumberOfFavorites() - v1.getNumberOfFavorites());

    for (var fav : videoList) {
      if (!user.getHistory().containsKey(fav.getTitle())) {
        output = "FavoriteRecommendation result: " + fav.getTitle();
        return writer.writeFile(action.getActionId(), output, output);
      }
    }

    return writer.writeFile(action.getActionId(), output, output);
  }

  /**
   *
   * @param action represents the needed data for the recommendation
   * @param writer util for generating json object
   * @return json object packing the search recommendation
   * @throws IOException if there was something wrong with the file writer
   */
  public static JSONObject search(final ActionInputData action, final Writer writer)
      throws IOException {
    User user = null;
    List<Video> videos = new ArrayList<>(Database.getInstance().getVideos());

    for (var u : Database.getInstance().getUsers()) {
      if (u.getUsername().equals(action.getUsername())) {
        user = u;
        break;
      }
    }

    String output = "SearchRecommendation cannot be applied!";
    if (user == null) {
      return writer.writeFile(action.getActionId(), output, output);
    }

    if (user.getSubscription().equals(Constants.BASIC_SUB)) {
      return writer.writeFile(action.getActionId(), output, output);
    }

    User finalUser = user;
    videos.removeIf(video -> !video.getGenres().contains(action.getGenre()));
    videos.removeIf(video -> finalUser.getHistory().containsKey(video.getTitle()));

    if (videos.isEmpty()) {
      return writer.writeFile(action.getActionId(), output, output);
    }

    videos.sort((videoNo1, videoNo2) -> {
      if (videoNo1.average() == null || videoNo2.average() == null
          || videoNo1.average().equals(videoNo2.average())) {
        return videoNo1.getTitle().compareTo(videoNo2.getTitle());
      }
      return videoNo1.average().compareTo(videoNo2.average());
    });

    ArrayList<String> order = new ArrayList<>();
    videos.forEach(video -> order.add(video.getTitle()));

    output = "SearchRecommendation result: " + order;
    return writer.writeFile(action.getActionId(), output, output);
  }
}
