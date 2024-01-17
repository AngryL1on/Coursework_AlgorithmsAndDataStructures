import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Navigator navigator = new LinkedListNavigator();

        // Adding routes
        Route route1 = new Route("1", 12.80, 0, true, Arrays.asList("A", "B", "C"));
        Route route2 = new Route("2", 11.80, 1, true, Arrays.asList("B", "C"));
        Route route3 = new Route("3", 11.80, 2, true, Arrays.asList("С", "B", "A"));
        Route route4 = new Route("4", 10.80, 3, false, Arrays.asList("A", "B"));
        Route route5 = new Route("5", 11.80, 4, true, Arrays.asList("C", "D"));
        Route route6 = new Route("6", 12.80, 5, false, Arrays.asList("B", "A", "C"));
        Route route7 = new Route("7", 16.80, 5, true, Arrays.asList("B", "C"));

        navigator.addRoute(route1);
        navigator.addRoute(route2);
        navigator.addRoute(route3);
        navigator.addRoute(route4);
        navigator.addRoute(route5);
        navigator.addRoute(route6);
        navigator.addRoute(route7);

        // Демонстрация работы получения 5 самых популярных маршрутов
        System.out.println("5 самых популярных маршрутов: ");
        navigator.chooseRoute(route5.getId()); // Прибавляем одну единицу пятому маршруту
        Iterable<Route> topRoutes = navigator.getTop5Routes();
        System.out.println(topRoutes);

        System.out.println();

        // Демонстрация работы получения избранных маршрутов
        System.out.println("Избранные маршруты: ");
        Iterable<Route> favoriteRoutes = navigator.getFavoriteRoutes("B");
        System.out.println(favoriteRoutes);
//        favoriteRoutes.forEach(System.out::println);

        System.out.println();

        // Демонстрация работы поиска нужных маршрутов
        System.out.println(" Поиск маршрутов: ");
        Iterable<Route> searchRoutes = navigator.searchRoutes("B", "C");
        System.out.println(searchRoutes);

        System.out.println();

        System.out.println("Общее количество маршрутов: " + navigator.size());
        System.out.println("Информация о маршруте с ID 6: " + navigator.getRoute("6"));
        navigator.removeRoute("7");
        System.out.println("Удаление маршрута с ID 7");
        System.out.println("Общее количество маршрутов после удаления: " + navigator.size());
        System.out.println("Поиск маршрута с параметрами (id: 7, distance: 16.80, popularity: 5, isFavorite: true, locationPoints: [B, C]): " + navigator.contains(new Route("7", 16.80, 5, true, Arrays.asList("B", "C"))));

    }
}