package edu.ntnu.idi.bidata.boardgame.backend.io;

import java.util.Set;
import java.util.stream.Stream;

public interface DAO<T> {

  Stream<T> loadEntities();

  void persistEntities(Set<T> entities);
}
