package edu.ntnu.idi.bidata.boardgame.games.monopoly.repository;

import edu.ntnu.idi.bidata.boardgame.common.io.DAO;
import edu.ntnu.idi.bidata.boardgame.common.io.json.JsonService;
import edu.ntnu.idi.bidata.boardgame.games.monopoly.model.MonopolyGame;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Stream;

public class JsonMonopolyGameRepository implements DataRepository<MonopolyGame> {

  private static JsonMonopolyGameRepository repository;

  private final DAO<MonopolyGame> jsonService;
  private final ConcurrentMap<UUID, MonopolyGame> entities;
  private final Function<MonopolyGame, UUID> idExtractor;

  private JsonMonopolyGameRepository() {
    jsonService = new JsonService<>(MonopolyGame.class);
    entities = new ConcurrentHashMap<>();
    idExtractor = MonopolyGame::getId;
    refresh();
  }

  public static synchronized JsonMonopolyGameRepository getInstance() {
    if (repository == null) {
      repository = new JsonMonopolyGameRepository();
    }
    return repository;
  }

  @Override
  public Stream<MonopolyGame> getAll() {
    return entities.values().stream();
  }

  @Override
  public Optional<MonopolyGame> getById(UUID id) {
    return Optional.ofNullable(entities.get(id));
  }

  @Override
  public MonopolyGame add(MonopolyGame entity) {
    return entities.put(idExtractor.apply(entity), entity);
  }

  @Override
  public MonopolyGame update(MonopolyGame entity) {
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
  public Stream<MonopolyGame> loadFromSource() {
    return jsonService.loadEntities();
  }

  @Override
  public void persistEntities() {
    jsonService.persistEntities(new HashSet<>(entities.values()));
  }
}
