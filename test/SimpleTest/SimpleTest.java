package SimpleTest;

import Implementations.ImplementationD1;
import Interfaces.InterfaceD;
import common.DependencyException;
import org.junit.Before;
import org.junit.Test;
import simple.Container;
import simple.Injector;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SimpleTest {

    private Injector injector;

    @Before
    public void container() {
        injector = new Container();
    }

    @Test
    public void exampleTest() throws DependencyException {
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("D");
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.i, is(42));
    }
}
