package edu.ntnu.idi.bidata.boardgame.common.io;

import java.util.Set;
import java.util.stream.Stream;

/**
 * Data Access Object (DAO) interface that provides methods for serializing and deserializing
 * entities of a specified generic type. Implementations of this interface should provide concrete
 * mechanisms for reading from a data source and writing to a data source.
 *
 * @param <T> the type of the entities managed by this DAO
 * @author Nick Heggø
 * @version 2025.05.08
 */
public interface DAO<T> {

  Stream<T> deserializeFromSource();

  void serializeToSource(Set<T> entities);
}
