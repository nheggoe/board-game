package edu.ntnu.idi.bidata.boardgame.common.repository;

import edu.ntnu.idi.bidata.boardgame.common.io.json.JsonService;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * An abstract base class for repositories that manage entities of a specified type using JSON as
 * the underlying storage mechanism. This class integrates a JSON service to handle the
 * serialization and deserialization of entities and provides methods that adhere to the contract
 * defined in the {@link DataRepository} interface.
 *
 * @param <T> The type of entities this repository will manage.
 * @author Nick Heggø
 * @version 2025.05.08
 */
public abstract class JsonRepository<T> implements DataRepository<T> {

  private final JsonService<T> jsonService;
  private final Function<T, UUID> idExtractor;
  private final ConcurrentMap<UUID, T> entities;

  protected JsonRepository(Class<T> entityClass, Function<T, UUID> idExtractor) {
    this.jsonService = new JsonService<>(entityClass);
    this.idExtractor = Objects.requireNonNull(idExtractor, "idExtractor cannot be null!");
    this.entities = new ConcurrentHashMap<>();
    initializeEntities();
  }

  private void initializeEntities() {
    loadFromSource().forEach(entity -> entities.put(idExtractor.apply(entity), entity));
  }

  @Override
  public Stream<T> loadFromSource() {
    return jsonService.deserializeFromSource();
  }

  @Override
  public void saveToSource(Set<T> entities) {
    jsonService.serializeToSource(entities);
  }

  @Override
  public T add(T entity) {
    return entities.put(idExtractor.apply(entity), entity);
  }

  @Override
  public Stream<T> getAll() {
    return entities.values().stream();
  }

  @Override
  public Optional<T> getById(UUID id) {
    return Optional.ofNullable(entities.get(id));
  }

  @Override
  public T update(T entity) {
    return entities.computeIfPresent(idExtractor.apply(entity), (k, v) -> entity);
  }

  @Override
  public boolean remove(UUID id) {
    return entities.remove(id) != null;
  }
}
