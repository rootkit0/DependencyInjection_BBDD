package simple;

import common.DependencyException;

import java.util.HashMap;

public class Container implements Injector {

    //Estructures de dades
    private HashMap<String, Object> constants;
    private HashMap<String, simple.Factory> factories;
    private HashMap<String, simple.Factory> singleton;
    private HashMap<String, String[]> dependencies;

    public Container() {
        this.constants = new HashMap<>();
        this.factories = new HashMap<>();
        this.dependencies = new HashMap<>();
        this.singleton = new HashMap<>();
    }

    @Override
    public void registerConstant(String name, Object value) throws DependencyException {
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
            return this.createFactory(name, factories);
        }
        else if(this.singleton.containsKey(name)) {
            return this.createFactory(name, singleton);
        }
        else {
            throw new DependencyException(name + " is not registered");
        }
    }

    //Metode auxiliar
    private Object createFactory(String name, HashMap<String, Factory> hashMap) throws DependencyException {
        try {
            //Obtenim la factoria a crear
            Factory creator = hashMap.get(name);
            //Obtenim les dependencies de la factoria
            int num_dependencies = this.dependencies.get(name).length;
            Object[] dependencies = new Object[num_dependencies];
            for(int i=0; i<num_dependencies; ++i) {
                dependencies[i] = this.getObject(this.dependencies.get(name)[i]);
            }
            //Cridem al metode create amb les dependencies obtingudes
            return creator.create(dependencies);
        }
        catch(DependencyException e) {
            throw new DependencyException(e);
        }
    }
}
