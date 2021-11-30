package models;

import java.util.ArrayList;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
  private String username;
  private String subscription;
  private Map<String, Integer> history;
  private ArrayList<String> favorites;
  private Map<String, Integer> rated;
}
