/**
 * The Navigator interface represents a system for managing routes in an application.
 * It provides methods for adding, removing, and retrieving routes, as well as for selecting
 * and searching for specific routes based on different criteria.
 */
public interface Navigator {
    /**
     * Adds a route to "Navigator".
     *
     * @param route The Route object to be added.
     */
    void addRoute(Route route);

    /**
     * Removes a route from "Navigator".
     *
     * @param id The unique identifier of the route.
     */
    void removeRoute(String id);

    /**
     * Checks if a specific route is present in the "Navigator".
     *
     * @param route The Route object to be checked.
     * @return true if the route is present, false otherwise.
     */
    boolean contains(Route route);

    /**
     * Method for getting the number of "Route" elements.
     *
     * @return an integer number of routes in the navigator.
     */
    int size();

    /**
     * Retrieves a route based on its identifier.
     *
     * @param id The unique identifier of the route to be retrieved.
     * @return The Route object corresponding to the id, or null if not found.
     */
    Route getRoute(String id);

    /**
     * Increases the popularity of the selected route by its identifier by 1.
     *
     * @param id The unique identifier of the route.
     */
    void chooseRoute(String id);

    /**
     * Searches for routes that start and end at specified points.
     *
     * @param startPoint The starting point of the desired routes.
     * @param endPoint The ending point of the desired routes.
     * @return An iterable collection of routes that match the criteria.
     * If there are no such routes, returns an empty collection.
     */
    Iterable<Route> searchRoutes(String startPoint, String endPoint);

    /**
     * Retrieves a list of favorite routes for a specified destination.
     *
     * @param destinationPoint The destination point for which favorite routes are requested.
     * @return An iterable collection of favorite routes for the specified destination.
     * If there are no such routes, returns an empty collection.
     */
    Iterable<Route> getFavoriteRoutes(String destinationPoint);

    /**
     * Retrieves the top five routes in terms of popularity or ranking.
     *
     * @return An iterable collection of the top three routes.
     * If there are no such routes, returns an empty collection.
     */
    Iterable<Route> getTop5Routes();
}
