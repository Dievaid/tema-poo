package functionalities;

import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import functionalities.tasks.Command;
import functionalities.tasks.Query;
import functionalities.tasks.Recommend;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import models.Actor;
import models.playable.Video;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.Utils;

public final class DataProcessor {
  private DataProcessor() { }

  /**
   *
   * @param input needed for iterating through the commands
   * @param writer needed for generating JSON Object
   * @param arrayResult needed to add the JSON Object into the result array
   * @throws IOException when the file was not found
   */
  public static void execute(final Input input, final Writer writer, final JSONArray arrayResult)
      throws IOException {
    for (var action : input.getCommands()) {
      switch (action.getActionType()) {
        case Constants.COMMAND -> {
          for (var user : Database.getInstance().getUsers()) {
            if (user.getUsername().equals(action.getUsername())) {
              var json = switch (action.getType()) {
                case "favorite" -> Command.addToFavorites(action, writer);
                case "view" -> Command.addView(action, writer);
                case "rating" -> Command.rating(action, writer);
                default -> null;
              };

              if (json != null) {
                arrayResult.add(json);
              }
            }
          }
        }
        case Constants.QUERY -> {
          switch (action.getObjectType()) {
            case Constants.ACTORS -> {
              var json = switch (action.getCriteria()) {
                case "average" -> Query.getActorAverage(action, writer);
                case "awards" -> Query.getActorAwards(action, writer);
                case "filter_description" -> Query.getFilterActorsDescription(action, writer);
                default -> null;
              };

              if (json != null) {
                arrayResult.add(json);
              }
            }
            case Constants.MOVIES, Constants.SHOWS -> {
              var json = switch (action.getCriteria()) {
                case "ratings" -> Query.getVideosByRating(action, writer);
                case "favorite" -> Query.getMostAddedToFavourite(action, writer);
                case "longest" -> Query.getLongest(action, writer);
                case "most_viewed" -> Query.getMostViewed(action, writer);
                default -> null;
              };

              if (json != null) {
                arrayResult.add(json);
              }
            }
            case Constants.USERS -> {
              JSONObject json = null;
              if (action.getCriteria().equals(Constants.NUM_RATINGS)) {
                json = Query.mostDevotedUsers(action, writer);
              }
              if (json != null) {
                arrayResult.add(json);
              }
            }
            default -> {
              arrayResult.add(writer.writeFile(
                  action.getActionId(), "Query result []", "Query result []"));
            }
          }
        }

        case Constants.RECOMMENDATION -> {
          var json = switch (action.getType()) {
            case Constants.STANDARD -> Recommend.standard(action, writer);
            case Constants.BEST_UNSEEN -> Recommend.bestUnseen(action, writer);
            case Constants.POPULAR -> Recommend.popular(action, writer);
            case Constants.FAVORITE -> Recommend.favourite(action, writer);
            case Constants.SEARCH -> Recommend.search(action, writer);
            default -> null;
          };

          if (json != null) {
            arrayResult.add(json);
          }
        }
        default -> { }
      }
    }
  }

  /**
   *
   * @param map Any kind of map you want to sort by value
   * @param <K> Generic Type
   * @param <V> Generic Type
   * @return LinkedHashMap ordered by values
   */
  public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> map) {
    List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
    list.sort(Entry.comparingByValue());

    Map<K, V> result = new LinkedHashMap<>();
    for (Entry<K, V> entry : list) {
      result.put(entry.getKey(), entry.getValue());
    }

    return result;
  }

  /**
   *
   * @param action needed for applying the filters
   * @return a list of actors with all the filters given applied
   */
  public static List<Actor> filterActors(final ActionInputData action) {
    List<Actor> filteredActors = new ArrayList<>(Database.getInstance().getActors());

    var filters = action.getFilters();

    if (filters.get(Constants.Filter.WORD) != null) {
      for (var filterWord : filters.get(Constants.Filter.WORD)) {
        if (filterWord != null) {
          var regexPattern = Pattern.compile("[ -]" + filterWord + "[ .,]");
          filteredActors.removeIf(actor -> !regexPattern.matcher(
              actor.getCareerDescription().toLowerCase()).find());
        }
      }
    }

    if (filters.get(Constants.Filter.AWARDS) != null) {
      for (var award : filters.get(Constants.Filter.AWARDS)) {
        if (award != null) {
          filteredActors.removeIf(actor ->
              !actor.getAwards().containsKey(Utils.stringToAwards(award)));
        }
      }
    }

    return filteredActors;
  }

  /**
   *
   * @param action needed for applying the filters
   * @return a list of videos with all the filters given applied
   */
  public static List<Video> filterVideos(final ActionInputData action) {
    List<Video> filteredVideos = new ArrayList<>();

    var filters = action.getFilters();

    switch (action.getObjectType()) {
      case Constants.SHOWS -> filteredVideos.addAll(Database.getInstance().getSeries());
      case Constants.MOVIES -> filteredVideos.addAll(Database.getInstance().getMovies());
      default -> { }
    }

    if (filters.get(Constants.Filter.YEAR) != null) {
      for (var year : filters.get(Constants.Filter.YEAR)) {
        if (year != null) {
          filteredVideos.removeIf(video -> video.getYear() != Integer.parseInt(year));
        }
      }
    }

    if (filters.get(Constants.Filter.GENRE) != null) {
      for (var genre : filters.get(Constants.Filter.GENRE)) {
        if (genre != null) {
          filteredVideos.removeIf(video -> !video.getGenres().contains(genre));
        }
      }
    }

    return filteredVideos;
  }
}
