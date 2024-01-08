import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class LinkedListNavigator implements Navigator {
    private LinkedList<Route> routes;

    public LinkedListNavigator() {
        routes = new LinkedList<>();
    }


    @Override
    public void addRoute(Route route) {
        routes.add(route);
    }

    @Override
    public void removeRoute(String id) {
        routes.removeIf(route -> route.getId().equals(id));
    }

    @Override
    public boolean contains(Route route) {
        return routes.contains(route);
    }

    @Override
    public int size() {
        return routes.size();
    }

    @Override
    public Route getRoute(String id) {
        return routes.stream()
                .filter(route -> route.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void chooseRoute(String id) {

        Route route = getRoute(id);
        if (route != null) {
            route.increasePopularity();
        }
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        return routes.stream()
                .filter(route -> route.getLocationPoints().contains(startPoint) && route.getLocationPoints().contains(endPoint))
                .sorted(Comparator.comparingInt((Route r) -> r.getLocationPoints().indexOf(endPoint) - r.getLocationPoints().indexOf(startPoint))
                        .thenComparingInt(Route::getPopularity).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        return routes.stream()
                .filter(route -> route.isFavorite() && route.getLocationPoints().contains(destinationPoint))
                .sorted(Comparator.comparingInt((Route r) -> Collections.frequency(r.getLocationPoints(), destinationPoint))
                        .thenComparingInt(Route::getPopularity).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Route> getTop5Routes() {
        return routes.stream()
                .sorted(Comparator.comparingInt(Route::getPopularity).reversed()
                        .thenComparingInt(r -> r.getLocationPoints().size()))
                .limit(5)
                .collect(Collectors.toList());
    }
}
