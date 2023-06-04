package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class User {
  @JsonProperty("fullName")
  private String fullName;
  @JsonProperty("username")
  private String username;
  @JsonProperty("password")
  private String password;

  public User(String fullName, String username, String password) {
    this.fullName = fullName;
    this.username = username;
    this.password = password;
  }

  public User() {

  }

  public String getFullName() {
    return fullName;
  }
  public String getUsername() {
    return username;
  }
  public String getPassword() {
    return password;
  }
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public void setPassword (String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User Info { Name: " + fullName + ", Username: "
            + username + ", Password: " + password + "}";
  }
  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }
}
