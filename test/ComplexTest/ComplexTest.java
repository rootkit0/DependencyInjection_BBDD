package ComplexTest;

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
import complex.Container;
import complex.Injector;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ComplexTest {

    private Injector injector;
    @Before
    public void container() {
        injector = new Container();
    }

    //Example test (Testejar si FactoryD1 es registra correctament i obtenim la instancia correcta)
    @Test
    public void exampleTest() throws DependencyException {
        injector.registerConstant(Integer.class, 42);
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        InterfaceD d = (InterfaceD) injector.getObject(InterfaceD.class);
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.i, is(42));
    }

    //Test1: Testejar si FactoryA1 es registra correctament i obtenim la instancia correcta
    @Test
    public void registerA1() throws DependencyException {
        System.out.println("------- Running TEST1 -------");
        injector.registerConstant(Integer.class, 42);
        injector.registerConstant(String.class, "Test");
        registerFactories();
        InterfaceA a = (InterfaceA) injector.getObject(InterfaceA.class);
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
        injector.registerConstant(Integer.class, 42);
        injector.registerFactory(InterfaceB.class, new FactoryB1(), InterfaceD.class);
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
        InterfaceB b = (InterfaceB) injector.getObject(InterfaceB.class);
        assertThat(b, is(instanceOf(ImplementationB1.class)));
        ImplementationB1 b1 = (ImplementationB1) b;
        assertThat(b1.d, is(instanceOf(ImplementationD1.class)));
        System.out.println("------- Finished TEST2 -------");
    }

    //Test3: Testejar si FactoryC1 es registra correctament i obtenim la instancia correcta
    @Test
    public void registerC1() throws DependencyException {
        System.out.println("------- Running TEST3 -------");
        injector.registerConstant(String.class, "Test");
        injector.registerFactory(InterfaceC.class, new FactoryC1(), String.class);
        InterfaceC c = (InterfaceC) injector.getObject(InterfaceC.class);
        assertThat(c, is(instanceOf(ImplementationC1.class)));
        ImplementationC1 c1 = (ImplementationC1) c;
        assertThat(c1.s,is("Test"));
        System.out.println("------- Finished TEST3 -------");
    }

    //Test4: Testejar la creacio de FactoryA1 sense introduir una constant
    @Test(expected = DependencyException.class)
    public void createA1WithoutConstant() throws DependencyException {
        System.out.println("------- Running TEST4 -------");
        injector.registerConstant(String.class, "Test");
        registerFactories();
        InterfaceA a = (InterfaceA) injector.getObject(InterfaceA.class);
        System.out.println("------- Finished TEST4 -------");
    }

    //Test5: Testejar la creacio de FactoryA1 introduint arguments erronis
    @Test(expected = DependencyException.class)
    public void createA1BadArguments() throws DependencyException {
        System.out.println("------- Running TEST5 -------");
        injector.registerConstant(Integer.class, 42);
        injector.registerConstant(String.class, "Test");
        injector.registerFactory(InterfaceA.class,  new FactoryA1(), Integer.class, String.class);
        InterfaceA a = (InterfaceA) injector.getObject(InterfaceA.class);
        System.out.println("------- Finished TEST5 -------");
    }

    //Test6: Testejar la creacio de FactoryB1 introduint arguments erronis
    @Test(expected = DependencyException.class)
    public void createB1BadArguments() throws DependencyException {
        System.out.println("------- Running TEST6 -------");
        injector.registerConstant(Integer.class, 42);
        injector.registerConstant(String.class, "Test");
        injector.registerFactory(InterfaceB.class, new FactoryB1(), String.class);
        InterfaceB b = (InterfaceB) injector.getObject(InterfaceB.class);
        System.out.println("------- Finished TEST6 -------");
    }

    //Test7: Testejar la creacio de FactoryC1 introduint arguments erronis
    @Test(expected = DependencyException.class)
    public void createC1BadArguments() throws DependencyException {
        System.out.println("------- Running TEST7 -------");
        injector.registerConstant(Integer.class, 42);
        injector.registerConstant(String.class, "Test");
        injector.registerFactory(InterfaceC.class, new FactoryC1(), Integer.class);
        InterfaceC c = (InterfaceC) injector.getObject(InterfaceC.class);
        System.out.println("------- Finished TEST7 -------");
    }

    //Test8: Testejar quan registrem dos cops la mateixa Constant
    @Test(expected = DependencyException.class)
    public void twoTimesSameConstant() throws DependencyException {
        System.out.println("------- Running TEST8 -------");
        injector.registerConstant(Integer.class, 42);
        injector.registerConstant(Integer.class, 42);
        System.out.println("------- Finished TEST8 -------");
    }

    //Test9: Testejar quan registrem dos cops la mateixa Factory
    @Test(expected = DependencyException.class)
    public void twoTimesSameFactory() throws DependencyException {
        System.out.println("------- Running TEST9 -------");
        registerFactories();
        registerFactories();
        System.out.println("------- Finished TEST9 -------");
    }

    //Test10: Testejar si FactoryA1 es registra correctament utilitzant singletons i obtenim la instancia correcta
    @Test
    public void registerA1Singleton() throws DependencyException {
        System.out.println("------- Running TEST10 -------");
        injector.registerConstant(Integer.class, 42);
        injector.registerConstant(String.class, "Test");
        registerSingletons();
        InterfaceA a =(InterfaceA) injector.getObject(InterfaceA.class);
        assertThat(a, is(instanceOf(ImplementationA1.class)));
        ImplementationA1 a1 = (ImplementationA1) a;
        assertThat(a1.b, is(instanceOf(ImplementationB1.class)));
        assertThat(a1.c, is(instanceOf(ImplementationC1.class)));
        System.out.println("------- Finished TEST10 -------");
    }

    //Test11: Testejar si FactoryB1 es registra correctament utilitzant singletons i obtenim la instancia correcta
    @Test
    public void registerB1Singleton() throws DependencyException {
        System.out.println("------- Running TEST11 -------");
        injector.registerConstant(Integer.class, 42);
        injector.registerSingleton(InterfaceB.class, new FactoryB1(), InterfaceD.class);
        injector.registerSingleton(InterfaceD.class, new FactoryD1(), Integer.class);
        InterfaceB b = (InterfaceB) injector.getObject(InterfaceB.class);
        assertThat(b, is(instanceOf(ImplementationB1.class)));
        ImplementationB1 b1 = (ImplementationB1) b;
        assertThat(b1.d, is(instanceOf(ImplementationD1.class)));
        System.out.println("------- Finished TEST11 -------");
    }

    //Test12: Testejar si FactoryC1 es registra correctament utilitzant singletons i obtenim la instancia correcta
    @Test
    public void registerC1Singleton() throws DependencyException {
        System.out.println("------- Running TEST12 -------");
        injector.registerConstant(String.class, "Test");
        injector.registerSingleton(InterfaceC.class, new FactoryC1(), String.class);
        InterfaceC c = (InterfaceC) injector.getObject(InterfaceC.class);
        assertThat(c, is(instanceOf(ImplementationC1.class)));
        ImplementationC1 c1 = (ImplementationC1) c;
        assertThat(c1.s,is("Test"));
        System.out.println("------- Finished TEST12 -------");
    }

    //Test13: Testejar si FactoryD1 es registra correctament utilitzant singletons i obtenim la instancia correcta
    @Test
    public void registerD1Singleton() throws DependencyException {
        System.out.println("------- Running TEST13 -------");
        injector.registerConstant(Integer.class, 42);
        injector.registerSingleton(InterfaceD.class, new FactoryD1(), Integer.class);
        InterfaceD d = (InterfaceD) injector.getObject(InterfaceD.class);
        assertThat(d, is(instanceOf(ImplementationD1.class)));
        ImplementationD1 d1 = (ImplementationD1) d;
        assertThat(d1.i, is(42));
        System.out.println("------- Finished TEST13 -------");
    }

    //Aux methods
    private void registerFactories() throws DependencyException{
        injector.registerFactory(InterfaceA.class, new FactoryA1(), InterfaceB.class, InterfaceC.class);
        injector.registerFactory(InterfaceB.class, new FactoryB1(), InterfaceD.class);
        injector.registerFactory(InterfaceC.class, new FactoryC1(), String.class);
        injector.registerFactory(InterfaceD.class, new FactoryD1(), Integer.class);
    }

    private void registerSingletons() throws DependencyException{
        injector.registerSingleton(InterfaceA.class, new FactoryA1(), InterfaceB.class, InterfaceC.class);
        injector.registerSingleton(InterfaceB.class, new FactoryB1(), InterfaceD.class);
        injector.registerSingleton(InterfaceC.class, new FactoryC1(), String.class);
        injector.registerSingleton(InterfaceD.class, new FactoryD1(), Integer.class);
    }
}
