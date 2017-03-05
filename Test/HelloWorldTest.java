import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gabriel Tapizquent on 3/3/17.
 */
public class HelloWorldTest {
    @Test
    public void HelloWorldTest() {
        HelloWorld hello = new HelloWorld();
        Assert.assertEquals("Hello, world!", hello.sayHello());
    }

    @Test
    public void HelloWorldTwiceTest() {
        HelloWorld hello = new HelloWorld();
        Assert.assertEquals("Hello Hello, World!", hello.sayHelloTwice());
    }
}
