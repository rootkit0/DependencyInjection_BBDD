package complex;

import common.DependencyException;

import java.util.HashMap;

public class Container implements Injector {

    //Estructures de dades
    private HashMap<Class, Object> constants;
    private HashMap<Class, complex.Factory> factories;
    private HashMap<Class, complex.Factory> singleton;
    private HashMap<Class, Class[]> dependencies;

    public Container() {
        this.constants = new HashMap<>();
        this.factories = new HashMap<>();
        this.singleton = new HashMap<>();
        this.dependencies = new HashMap<>();
    }

    @Override
    public <E> void registerConstant(Class<E> name, E value) throws DependencyException {
        if(this.constants.containsKey(name)) {
            throw new DependencyException("Constant " + name + " is already registered");
        }
        else {
            this.constants.put(name, value);
        }
    }

    @Override
    public <E> void registerFactory(Class<E> name, Factory<? extends E> creator, Class<?>... parameters) throws DependencyException {
        if(this.factories.containsKey(name)) {
            throw new DependencyException("Factory " + name + "is already registered");
        }
        else {
            this.factories.put(name, creator);
            this.dependencies.put(name, parameters);
        }
    }

    @Override
    public <E> void registerSingleton(Class<E> name, Factory<? extends E> creator, Class<?>... parameters) throws DependencyException {
        if(this.singleton.containsKey(name)) {
            throw new DependencyException("Singleton " + name + " is already registered");
        }
        else {
            this.singleton.put(name, creator);
            this.dependencies.put(name, parameters);
        }
    }

    @Override
    public <E> E getObject(Class<E> name) throws DependencyException {
        if(this.constants.containsKey(name)) {
            return (E)this.constants.get(name);
        }
        else if(this.factories.containsKey(name)) {
            return (E)this.createFactory(name, factories);
        }
        else if(this.singleton.containsKey(name)) {
            return (E)this.createFactory(name, singleton);
        }
        else {
            throw new DependencyException(name + " is not registered");
        }
    }

    //Metode auxiliar
    private <E> Object createFactory(Class<E> name, HashMap<Class, complex.Factory> hashMap) throws DependencyException {
        try {
            //Obtenim la factoria a crear
            complex.Factory creator = hashMap.get(name);
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
