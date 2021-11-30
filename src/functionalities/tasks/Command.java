package functionalities.tasks;

import fileio.ActionInputData;
import fileio.Writer;
import functionalities.Database;
import java.io.IOException;
import org.json.simple.JSONObject;

public final class Command {
  private Command() { }

  /**
   *
   * @param action represents the command data
   * @param writer helper for returning JSON Object
   * @return JSON Object representing the status of the command
   * @throws IOException if there is something wrong with the file
   */
  public static JSONObject addToFavorites(final ActionInputData action, final Writer writer)
      throws IOException {
    String outputMessage;
    for (var user : Database.getInstance().getUsers()) {
      if (user.getUsername().equals(action.getUsername())) {
        if (user.getFavorites().contains(action.getTitle())) {
          outputMessage = "error -> " + action.getTitle() + " is already in favourite list";
          return writer.writeFile(action.getActionId(), outputMessage, outputMessage);
        }

        if (user.getHistory().containsKey(action.getTitle())) {
          user.getFavorites().add(action.getTitle());
          outputMessage = "success -> " + action.getTitle() + " was added as favourite";
        } else {
          outputMessage = "error -> " + action.getTitle() + " is not seen";
        }
        return writer.writeFile(action.getActionId(), outputMessage, outputMessage);
      }
    }
    return null;
  }

  /**
   *
   * @param action represents the command data
   * @param writer helper for returning JSON Object
   * @return JSON Object representing the status of the command
   * @throws IOException if there is something wrong with the file
   */
  public static JSONObject addView(final ActionInputData action, final Writer writer)
      throws IOException {
    int views = 1;
    for (var user : Database.getInstance().getUsers()) {
      if (user.getUsername().equals(action.getUsername())) {
        if (user.getHistory().containsKey(action.getTitle())) {
          views = user.getHistory().get(action.getTitle()) + 1;
          user.getHistory().replace(action.getTitle(), views);
        } else {
          user.getHistory().put(action.getTitle(), 1);
        }
      }
    }
    String outputMessage =
        "success -> " + action.getTitle() + " was viewed with total views of " + views;
    return writer.writeFile(action.getActionId(), outputMessage, outputMessage);
  }

  /**
   *
   * @param action represents the command data
   * @param writer helper for returning JSON Object
   * @return JSON Object representing the status of the command
   * @throws IOException if there is something wrong with the file
   */
  public static JSONObject rating(final ActionInputData action, final Writer writer)
      throws IOException {
    String outputMessage;
    for (var user : Database.getInstance().getUsers()) {
      if (user.getUsername().equals(action.getUsername())) {
        if (!user.getHistory().containsKey(action.getTitle())) {
          outputMessage = "error -> " + action.getTitle() + " is not seen";
          return writer.writeFile(action.getActionId(), outputMessage, outputMessage);
        }

        if (user.getRated().containsKey(action.getTitle())) {
          for (var entry : user.getRated().entrySet()) {
            if (entry.getKey().equals(action.getTitle())
                && entry.getValue().equals(action.getSeasonNumber())) {
              outputMessage = "error -> " + action.getTitle() + " has been already rated";
              return writer.writeFile(action.getActionId(), outputMessage, outputMessage);
            }
          }
        }

        if (action.getSeasonNumber() != 0) {
          for (var serie : Database.getInstance().getSeries()) {
            if (serie.getTitle().equals(action.getTitle())) {
              var season = serie.getSeasons().get(action.getSeasonNumber() - 1);

              season.getRatings().add(action.getGrade());
              user.getRated().put(serie.getTitle(), action.getSeasonNumber());

              outputMessage =
                  "success -> " + serie.getTitle() + " was rated with " + action.getGrade() + " by "
                      + user.getUsername();
              return writer.writeFile(action.getActionId(), outputMessage, outputMessage);
            }
          }
        } else {
          for (var movie : Database.getInstance().getMovies()) {
            if (movie.getTitle().equals(action.getTitle())) {
              movie.getRatings().add(action.getGrade());
              user.getRated().put(action.getTitle(), 0);

              outputMessage =
                  "success -> " + movie.getTitle() + " was rated with " + action.getGrade() + " by "
                      + user.getUsername();
              return writer.writeFile(action.getActionId(), outputMessage, outputMessage);
            }
          }
        }
      }
    }
    return null;
  }
}
