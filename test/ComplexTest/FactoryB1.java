package ComplexTest;

import common.DependencyException;
import complex.Factory;
import Implementations.ImplementationB1;
import Implementations.ImplementationD1;
import Interfaces.InterfaceD;

public class FactoryB1 implements Factory<ImplementationB1> {
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
