package skarlat.dev.ecoproject.network;

import skarlat.dev.ecoproject.User;

public abstract class Authenticator<T> {
    protected T instance;

    public Authenticator(T instance) {
        this.instance = instance;
    }

    public abstract User getCurrentUser();

    T getInstance() {
        return instance;
    }
}
