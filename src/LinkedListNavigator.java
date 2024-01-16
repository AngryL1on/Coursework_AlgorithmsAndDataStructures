import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LinkedListNavigator implements Navigator {
    private CustomLinkedList routes;

    public LinkedListNavigator() {
        routes = new CustomLinkedList();
    }

    @Override
    public void addRoute(Route route) {
        routes.add(route);
    }

    @Override
    public void removeRoute(String id) {
        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).getId().equals(id)) {
                routes.remove(i);
                break;
            }
        }
    }

    @Override
    public boolean contains(Route route) {
        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).equals(route)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return routes.size();
    }

    @Override
    public Route getRoute(String id) {
        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            if (route.getId().equals(id)) {
                return route;
            }
        }
        return null;
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
        List<Route> result = new ArrayList<>();
        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            int startIndex = route.getLocationPoints().indexOf(startPoint);
            int endIndex = route.getLocationPoints().indexOf(endPoint);
            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                result.add(route);
            }
        }
        result.sort(new Comparator<Route>() {
            @Override
            public int compare(Route r1, Route r2) {
                // Сначала сортировка по признаку избранного
                int favoriteComparison = Boolean.compare(r2.isFavorite(), r1.isFavorite());
                if (favoriteComparison != 0) return favoriteComparison;

                // Затем сортировка по расстоянию между начальной и конечной точками
                int startEndDistanceComparison = Integer.compare(
                        r1.getLocationPoints().indexOf(endPoint) - r1.getLocationPoints().indexOf(startPoint),
                        r2.getLocationPoints().indexOf(endPoint) - r2.getLocationPoints().indexOf(startPoint)
                );
                if (startEndDistanceComparison != 0) return startEndDistanceComparison;

                // Наконец, сортировка по популярности
                return Integer.compare(r2.getPopularity(), r1.getPopularity());
            }
        });

        return result;
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        List<Route> result = new ArrayList<>();
        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            if (route.isFavorite() &&
                    route.getLocationPoints().contains(destinationPoint) &&
                    !route.getLocationPoints().get(0).equals(destinationPoint)) {
                result.add(route);
            }
        }
        result.sort(new Comparator<Route>() {
            @Override
            public int compare(Route r1, Route r2) {
                // Сначала сортировка по расстоянию
                int distanceComparison = Double.compare(r1.getDistance(), r2.getDistance());
                if (distanceComparison != 0) return distanceComparison;

                // Затем сортировка по популярности
                return Integer.compare(r1.getPopularity(), r2.getPopularity());
            }
        });

        return result;
    }

    @Override
    public Iterable<Route> getTop5Routes() {
        List<Route> allRoutes = new ArrayList<>();
        for (int i = 0; i < routes.size(); i++) {
            allRoutes.add(routes.get(i));
        }

        allRoutes.sort(new Comparator<Route>() {
            @Override
            public int compare(Route r1, Route r2) {
                int popularityComparison = Integer.compare(r2.getPopularity(), r1.getPopularity());
                if (popularityComparison != 0) return popularityComparison;

                int distanceComparison = Double.compare(r1.getDistance(), r2.getDistance());
                if (distanceComparison != 0) return distanceComparison;

                return Integer.compare(r1.getLocationPoints().size(), r2.getLocationPoints().size());
            }
        });

        List<Route> top5Routes = new ArrayList<>();
        for (int i = 0; i < Math.min(5, allRoutes.size()); i++) {
            top5Routes.add(allRoutes.get(i));
        }

        return top5Routes;
    }
}
