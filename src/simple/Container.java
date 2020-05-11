package simple;

import common.DependencyException;

import java.util.HashMap;

public class Container implements Injector {

    //Estructures de dades
    private HashMap<String, Object> constants;
    private HashMap<String, Factory> factories;
    private HashMap<String, String[]> dependencies;
    private HashMap<String, Factory> singleton;

    public Container() {
        //Inicialitzem les estructures
        this.constants = new HashMap<>();
        this.factories = new HashMap<>();
        this.dependencies = new HashMap<>();
        this.singleton = new HashMap<>();
    }

    @Override
    public void registerConstant(String name, Object value) throws DependencyException {
        //Asocia el nom al valor
        if(this.constants.containsKey(name)) {
            throw new DependencyException("Constant " + name + " is already registered");
        }
        else {
            this.constants.put(name, value);
        }
    }

    @Override
    public void registerFactory(String name, Factory creator, String... parameters) throws DependencyException {
        if(this.factories.containsKey(name)) {
            throw new DependencyException("Factory " + name + "is already registered");
        }
        else {
            this.factories.put(name, creator);
            this.dependencies.put(name, parameters);
        }
    }

    @Override
    public void registerSingleton(String name, Factory creator, String... parameters) throws DependencyException {
        if(this.singleton.containsKey(name)) {
            throw new DependencyException("Singleton " + name + " is already registered");
        }
        else {
            this.singleton.put(name, creator);
            this.dependencies.put(name, parameters);
        }
    }

    @Override
    public Object getObject(String name) throws DependencyException {
        if(this.constants.containsKey(name)) {
            return this.constants.get(name);
        }
        else if(this.factories.containsKey(name)) {
            return this.createFactory(name);
        }
        else if(this.singleton.containsKey(name)) {
            return this.createSingletonFactory(name);
        }
        else {
            throw new DependencyException(name + " is not registered");
        }
    }

    //Metodes auxiliars
    private Object createFactory(String name) throws DependencyException {
        try {

        }
        catch(DependencyException e) {
            throw new DependencyException(e);
        }
    }

    private Object createSingletonFactory(String name) throws DependencyException {
        try {

        }
        catch(DependencyException e) {
            throw new DependencyException(e);
        }
    }
}
