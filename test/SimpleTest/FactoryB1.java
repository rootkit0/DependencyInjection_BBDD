package SimpleTest;

import common.DependencyException;
import simple.Factory;
import Implementations.ImplementationB1;
import Implementations.ImplementationD1;
import Interfaces.InterfaceD;

public class FactoryB1 implements Factory {
    @Override
    public ImplementationB1 create(Object... parameters) throws DependencyException {
        InterfaceD d;
        try {
            d = (ImplementationD1)parameters[0];
        }catch (ClassCastException | ArrayIndexOutOfBoundsException ex){
            throw new DependencyException(ex);
        }
        return new ImplementationB1(d);
    }
}
