public class ChartEntity {
    private String title;
    private double value;

    public ChartEntity(String title, double value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChartEntity))
            return false;
        if (o == this)
            return true;
        return (((ChartEntity) o).getTitle().equals(title) && ((ChartEntity) o).getValue() == value);
    }

    @Override
    public int hashCode() {
        return (int) (3 * title.hashCode() + 7 * value);
    }
}
