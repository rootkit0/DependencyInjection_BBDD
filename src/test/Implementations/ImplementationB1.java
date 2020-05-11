package test.Implementations;

import test.Interfaces.InterfaceB;
import test.Interfaces.InterfaceD;

public class ImplementationB1 implements InterfaceB {
    public final InterfaceD d;
    public ImplementationB1(InterfaceD d) {
        this.d = d;
    }
}
