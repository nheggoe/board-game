package edu.ntnu.idi.bidata.boardgame.games.monopoly.repository;

import edu.ntnu.idi.bidata.boardgame.backend.io.DAO;
import edu.ntnu.idi.bidata.boardgame.backend.io.json.JsonService;
import edu.ntnu.idi.bidata.boardgame.backend.model.Game;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Stream;

public class JsonGameRepository implements DataRepository<Game> {

  private static JsonGameRepository repository;

  private final DAO<Game> jsonService;
  private final ConcurrentMap<UUID, Game> entities;
  private final Function<Game, UUID> idExtractor;

  private JsonGameRepository() {
    jsonService = new JsonService<>(Game.class);
    entities = new ConcurrentHashMap<>();
    idExtractor = Game::getId;
    refresh();
  }

  public static synchronized JsonGameRepository getInstance() {
    if (repository == null) {
      repository = new JsonGameRepository();
    }
    return repository;
  }

  @Override
  public Stream<Game> getAll() {
    return entities.values().stream();
  }

  @Override
  public Optional<Game> getById(UUID id) {
    return Optional.ofNullable(entities.get(id));
  }

  @Override
  public Game add(Game entity) {
    return entities.put(idExtractor.apply(entity), entity);
  }

  @Override
  public Game update(Game entity) {
    return entities.computeIfPresent(idExtractor.apply(entity), (k, v) -> entity);
  }

  @Override
  public boolean remove(UUID id) {
    return entities.remove(id) != null;
  }

  @Override
  public void refresh() {
    entities.clear();
    loadFromSource().forEach(entity -> entities.put(idExtractor.apply(entity), entity));
  }

  @Override
  public Stream<Game> loadFromSource() {
    return jsonService.loadEntities();
  }

  @Override
  public void persistEntities() {
    jsonService.persistEntities(new HashSet<>(entities.values()));
  }
}
