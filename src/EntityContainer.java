
import java.awt.Color;
import java.util.*;
import java.util.List;

public class EntityContainer<T extends ChartEntity> {
    private Map<T, Color> entities;

    public EntityContainer() {
        entities = new HashMap<>();
    }

    public void add(T ce) {
        entities.put(ce, getRandomColor());
    }

    public double getChartValuesSum() {
        return entities.keySet().stream().mapToDouble(ChartEntity::getValue).sum();
    }

    public T getByTitle(String title) {
        return entities.keySet().stream().filter(x -> x.getTitle().equals(title)).findFirst().get();
    }

    public List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        entities.keySet().forEach(x -> titles.add(x.getTitle()));
        return titles;
    }

    public List<ChartEntity> getEntities() {
        return new ArrayList<>(entities.keySet());
    }

    public Color getColorFor(T e) {
        return entities.entrySet().stream().filter(x -> x.getKey().equals(e)).findFirst().get().getValue();
    }

    public static Color getRandomColor() {
        Random random = new Random();
        return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
    }
}
