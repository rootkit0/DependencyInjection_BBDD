package complex;

import common.DependencyException;

public class Container implements Injector {

    public Container() {

    }

    @Override
    public <E> void registerConstant(Class<E> name, E value) throws DependencyException {

    }

    @Override
    public <E> void registerFactory(Class<E> name, Factory<? extends E> creator, Class<?>... parameters) throws DependencyException {

    }

    @Override
    public <E> void registerSingleton(Class<E> name, Factory<? extends E> creator, Class<?>... parameters) throws DependencyException {

    }

    @Override
    public <E> E getObject(Class<E> name) throws DependencyException {
        return null;
    }
}
