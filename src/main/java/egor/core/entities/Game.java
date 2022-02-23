package egor.core.entities;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

public class Game {

  @NotBlank(message = "Название игры не может быть пустым!")
  private String name;

  @Min(value = 1990, message = "Игра не может быть выпущена раньше!")
  @Max(value = 2022, message = "Этот год ещё не наступил!")
  private int year;

  private GameType type;

  public Game(String name, int year, GameType type) {
    this.name = name;
    this.year = year;
    this.type = type;
  }

  public Game() {
    name = LocalTime.now().toString();
    year = LocalDate.now().getYear();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public GameType getType() {
    return type;
  }

  public void setType(GameType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "[name=" + name + ", year=" + year + ", type=" + type + "]";
  }

}
