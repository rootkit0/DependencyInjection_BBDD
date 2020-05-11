package SimpleTest;

import Implementations.ImplementationA1;
import Implementations.ImplementationB1;
import Implementations.ImplementationC1;
import Implementations.ImplementationD1;
import Interfaces.InterfaceA;
import Interfaces.InterfaceB;
import Interfaces.InterfaceC;
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

    //Example test (Testejar si FactoryD1 es registra correctament i obtenim la instancia correcta)
    @Test
    public void exampleTest() throws DependencyException {
        injector.registerConstant("I", 42);
        injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("D");
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.i, is(42));
    }

    //Test1: Testejar si FactoryA1 es registra correctament i obtenim la instancia correcta
    @Test
    public void registerA1() throws DependencyException {
        System.out.println("------- Running TEST1 -------");
        injector.registerConstant("I", 42);
        injector.registerConstant("S", "Test");
        registerFactories();
        InterfaceA a = (InterfaceA)injector.getObject("A");
        assertThat(a, is(instanceOf(ImplementationA1.class)));
        ImplementationA1 a1 = (ImplementationA1) a;
        assertThat(a1.b, is(instanceOf(ImplementationB1.class)));
        assertThat(a1.c, is(instanceOf(ImplementationC1.class)));
        System.out.println("------- Finished TEST1 -------");
    }

    //Test2: Testejar si FactoryB1 es registra correctament i obtenim la instancia correcta
    @Test
    public void registerB1() throws DependencyException {
        System.out.println("------- Running TEST2 -------");
        injector.registerConstant("I", 42);
        injector.registerFactory("B", new FactoryB1(), "D");
        injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceB b = (InterfaceB) injector.getObject("B");
        assertThat(b, is(instanceOf(ImplementationB1.class)));
        ImplementationB1 b1 = (ImplementationB1) b;
        assertThat(b1.d, is(instanceOf(ImplementationD1.class)));
        System.out.println("------- Finished TEST2 -------");
    }

    //Test3: Testejar si FactoryC1 es registra correctament i obtenim la instancia correcta
    @Test
    public void registerC1() throws DependencyException {
        System.out.println("------- Running TEST3 -------");
        injector.registerConstant("S", "exampleString");
        injector.registerFactory("C", new FactoryC1(), "S");
        InterfaceC c = (InterfaceC) injector.getObject("C");
        assertThat(c, is(instanceOf(ImplementationC1.class)));
        ImplementationC1 c1 = (ImplementationC1) c;
        assertThat(c1.s,is("exampleString"));
        System.out.println("------- Finished TEST3 -------");
    }

    //Test4: Testejar la creacio de FactoryA1 sense introduir una constant
    @Test(expected = DependencyException.class)
    public void createA1WithoutConstant() throws DependencyException {
        System.out.println("------- Running TEST4 -------");
        injector.registerConstant("S", "Test");
        registerFactories();
        InterfaceA a = (InterfaceA) injector.getObject("A");
        System.out.println("------- Finished TEST4 -------");
    }

    //Test5: Testejar la creacio de FactoryA1 introduint arguments erronis
    @Test(expected = DependencyException.class)
    public void createA1BadArguments() throws DependencyException {
        System.out.println("------- Running TEST5 -------");
        injector.registerConstant("I", 42);
        injector.registerConstant("S", "Test");
        injector.registerFactory("A",  new FactoryA1(), "I", "S");
        InterfaceA a = (InterfaceA) injector.getObject("A");
        System.out.println("------- Finished TEST5 -------");
    }

    //Test6: Testejar la creacio de FactoryB1 introduint arguments erronis
    @Test(expected = DependencyException.class)
    public void createB1BadArguments() throws DependencyException {
        System.out.println("------- Running TEST6 -------");
        injector.registerConstant("I", 42);
        injector.registerConstant("S", "Test");
        injector.registerFactory("B", new FactoryB1(), "S");
        InterfaceB b = (InterfaceB) injector.getObject("B");
        System.out.println("------- Finished TEST6 -------");
    }

    //Test7: Testejar la creacio de FactoryC1 introduint arguments erronis
    @Test(expected = DependencyException.class)
    public void createC1BadArguments() throws DependencyException {
        System.out.println("------- Running TEST7 -------");
        injector.registerConstant("I", 42);
        injector.registerConstant("S", "Test");
        injector.registerFactory("C", new FactoryC1(), "I");
        InterfaceC c = (InterfaceC) injector.getObject("C");
        System.out.println("------- Finished TEST7 -------");
    }

    //Test8: Testejar la creacio de FactoryD1 introduint arguments erronis
    @Test(expected = DependencyException.class)
    public void createD1BadArguments() throws DependencyException {
        System.out.println("------- Running TEST8 -------");
        String[] strings = {"Test1", "Test1"};
        injector.registerConstant("I", strings);
        injector.registerFactory("D", new FactoryD1(), "I");
        InterfaceD d =(InterfaceD) injector.getObject("D");
        System.out.println("------- Finished TEST8 -------");
    }

    @Test(expected = DependencyException.class)
    public void twoTimesSameConstant() throws DependencyException {
        System.out.println("------- Running TEST9 -------");
        injector.registerConstant("I", 42);
        injector.registerConstant("I", 42);
        System.out.println("------- Finished TEST9 -------");
    }

    @Test(expected = DependencyException.class)
    public void twoTimesSameFactory() throws DependencyException {
        System.out.println("------- Running TEST10 -------");
        registerFactories();
        registerFactories();
        System.out.println("------- Finished TEST10 -------");
    }

    //Test9: Testejar si FactoryA1 es registra correctament utilitzant singletons i obtenim la instancia correcta
    @Test
    public void registerA1Singleton() throws DependencyException {
        System.out.println("------- Running TEST11 -------");
        injector.registerConstant("I", 42);
        injector.registerConstant("S", "Test");
        registerSingletons();
        InterfaceA a =(InterfaceA) injector.getObject("A");
        assertThat(a, is(instanceOf(ImplementationA1.class)));
        ImplementationA1 a1 = (ImplementationA1) a;
        assertThat(a1.b, is(instanceOf(ImplementationB1.class)));
        assertThat(a1.c, is(instanceOf(ImplementationC1.class)));
        System.out.println("------- Finished TEST11 -------");
    }

    //Test10: Testejar si FactoryB1 es registra correctament utilitzant singletons i obtenim la instancia correcta
    @Test
    public void registerB1Singleton() throws DependencyException {
        System.out.println("------- Running TEST12 -------");
        injector.registerConstant("I", 42);
        injector.registerSingleton("B", new FactoryB1(), "D");
        injector.registerSingleton("D", new FactoryD1(), "I");
        InterfaceB b = (InterfaceB) injector.getObject("B");
        assertThat(b, is(instanceOf(ImplementationB1.class)));
        ImplementationB1 b1 = (ImplementationB1) b;
        assertThat(b1.d, is(instanceOf(ImplementationD1.class)));
        System.out.println("------- Finished TEST12 -------");
    }

    //Test11: Testejar si FactoryC1 es registra correctament utilitzant singletons i obtenim la instancia correcta
    @Test
    public void registerC1Singleton() throws DependencyException {
        System.out.println("------- Running TEST13 -------");
        injector.registerConstant("S", "Test");
        injector.registerSingleton("C", new FactoryC1(), "S");
        InterfaceC c = (InterfaceC) injector.getObject("C");
        assertThat(c, is(instanceOf(ImplementationC1.class)));
        ImplementationC1 c1 = (ImplementationC1) c;
        assertThat(c1.s,is("Test"));
        System.out.println("------- Finished TEST13 -------");
    }

    //Test12: Testejar si FactoryD1 es registra correctament utilitzant singletons i obtenim la instancia correcta
    @Test
    public void registerD1Singleton() throws DependencyException {
        System.out.println("------- Running TEST14 -------");
        injector.registerConstant("I", 42);
        injector.registerSingleton("D", new FactoryD1(), "I");
        InterfaceD d = (InterfaceD) injector.getObject("D");
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.i, is(42));
        System.out.println("------- Finished TEST14 -------");
    }

    //Aux methods
    private void registerFactories() throws DependencyException{
        injector.registerFactory("A", new FactoryA1(), "B", "C");
        injector.registerFactory("B", new FactoryB1(), "D");
        injector.registerFactory("C", new FactoryC1(), "S");
        injector.registerFactory("D", new FactoryD1(), "I");
    }

    private void registerSingletons() throws DependencyException{
        injector.registerSingleton("A", new FactoryA1(), "B", "C");
        injector.registerSingleton("B", new FactoryB1(), "D");
        injector.registerSingleton("C", new FactoryC1(), "S");
        injector.registerSingleton("D", new FactoryD1(), "I");
    }
}
