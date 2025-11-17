package co.uniquindio.edu.sendifly.session;

import co.uniquindio.edu.sendifly.models.User;

/**
 * Gestor de sesión para mantener el usuario actualmente autenticado.
 * Implementa Singleton para garantizar una única sesión en la aplicación.
 */
public class SessionManager {

    //Singleton
    private static SessionManager instance;
    private static final Object lock = new Object();

    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new SessionManager();
                }
            }
        }
        return instance;
    }

    private SessionManager() {
    }

    //Estado sesión
    private User currentUser;

    /**
     * Inicia sesión con un usuario
     */
    public void login(User user) {
        if (user == null) {
            throw new IllegalArgumentException("[SessionManager] El usuario no puede ser nulo");
        }
        this.currentUser = user;
        System.out.println("[SessionManager] Sesión iniciada para: " + user.getEmail());
    }

    /**
     * Obtiene el usuario actualmente autenticado
     */
    public User getCurrentUser() {
        if (currentUser == null) {
            throw new IllegalStateException(
                    "[SessionManager] No hay ningún usuario autenticado. Debe iniciar sesión primero.");
        }
        return currentUser;
    }

    /**
     * Verifica si hay un usuario autenticado
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Cierra la sesión actual
     */
    public void logout() {
        if (currentUser != null) {
            System.out.println("[SessionManager] Sesión cerrada para: " + currentUser.getEmail());
            currentUser = null;
        }
    }

    /**
     * Obtiene el ID del usuario actual
     */
    public String getCurrentUserId() {
        return getCurrentUser().getId();
    }

    /**
     * Obtiene el email del usuario actual
     */
    public String getCurrentUserEmail() {
        return getCurrentUser().getEmail();
    }

    /**
     * Obtiene el nombre del usuario actual
     */
    public String getCurrentUserName() {
        return getCurrentUser().getName();
    }
}