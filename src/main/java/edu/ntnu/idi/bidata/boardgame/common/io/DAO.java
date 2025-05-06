package edu.ntnu.idi.bidata.boardgame.common.io;

import java.util.Set;
import java.util.stream.Stream;

public interface DAO<T> {

  Stream<T> loadEntities();

  void persistEntities(Set<T> entities);
}
