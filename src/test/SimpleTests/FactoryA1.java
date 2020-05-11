package test.SimpleTests;

import common.DependencyException;
import simple.Factory;
import test.Implementations.ImplementationA1;
import test.Implementations.ImplementationB1;
import test.Implementations.ImplementationC1;
import test.Interfaces.InterfaceB;
import test.Interfaces.InterfaceC;

public class FactoryA1 implements Factory {
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
