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
                // Фильтрация: выбираем маршруты, содержащие начальную и конечную точку в правильном порядке
                .filter(route -> {
                    int startIndex = route.getLocationPoints().indexOf(startPoint);
                    int endIndex = route.getLocationPoints().indexOf(endPoint);
                    return startIndex != -1 && endIndex != -1 && startIndex < endIndex;
                })
                // Sorting: by favorite routes
                .sorted(Comparator.<Route, Boolean>comparing(route -> !route.isFavorite())
                        // Sorting: by distance
                        .thenComparingInt(route -> route.getLocationPoints().indexOf(endPoint) - route.getLocationPoints().indexOf(startPoint))
                        // Sorting: by popularity
                        .thenComparingInt(Route::getPopularity).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        return routes.stream()
                // Filtering: selecting favorite routes that contain a destination without being the starting point
                .filter(route -> route.isFavorite() &&
                        route.getLocationPoints().contains(destinationPoint) &&
                        !route.getLocationPoints().get(0).equals(destinationPoint))
                // Sorting: by distance
                .sorted(Comparator.comparingDouble(Route::getDistance)
                        // Sorting: by popularity
                        .thenComparingInt(Route::getPopularity))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Route> getTop5Routes() {
        return routes.stream()
                // First, sort by popularity in descending order
                .sorted(Comparator.comparingInt(Route::getPopularity).reversed()
                        // Then by distance in ascending order
                        .thenComparingDouble(Route::getDistance)
                        // Then by the number of location points in ascending order
                        .thenComparingInt(r -> r.getLocationPoints().size()))
                // We limit the number of results to five
                .limit(5)
                .collect(Collectors.toList());
    }
}
