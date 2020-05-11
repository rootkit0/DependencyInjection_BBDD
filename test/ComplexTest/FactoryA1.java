package ComplexTest;

import common.DependencyException;
import complex.Factory;
import Implementations.ImplementationA1;
import Implementations.ImplementationB1;
import Implementations.ImplementationC1;
import Interfaces.InterfaceB;
import Interfaces.InterfaceC;

public class FactoryA1 implements Factory<ImplementationA1> {
    @Override
    public ImplementationA1 create(Object... parameters) throws DependencyException {
        InterfaceB b;
        InterfaceC c;

        try{
            b = (ImplementationB1) parameters[0];
            c = (ImplementationC1) parameters[1];
        }catch (ClassCastException | ArrayIndexOutOfBoundsException ex){
            throw new DependencyException(ex);
        }
        return new ImplementationA1(b, c);
    }
}
