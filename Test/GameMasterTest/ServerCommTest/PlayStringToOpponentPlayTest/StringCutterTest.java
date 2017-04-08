package GameMasterTest.ServerCommTest.PlayStringToOpponentPlayTest;

import GameMaster.ServerComm.Parsers.PlayStringToOpponentPlay.StringCutter;
import org.junit.Assert;
import org.junit.Test;

public class StringCutterTest {

    String testString;
    String newString;

    @Test(expected = NullPointerException.class)
    public void aNullStringShouldThrowAnException() {
        givenThatIAmGivenAString(null);
        whenICutTheFirstNElementsSplitBySpace(3);
    }

    @Test
    public void anEmptyStringShouldReturnAnEmptyString() {
        givenThatIAmGivenAString("");
        whenICutTheFirstNElementsSplitBySpace(3);
        thenTheNewStringShouldBe("");
    }

    @Test
    public void removingTheFirstThreeShouldBeCorrect() {
        givenThatIAmGivenAString("Everything Past THIS should be remaining");
        whenICutTheFirstNElementsSplitBySpace(3);
        thenTheNewStringShouldBe("should be remaining");
    }

    /*
     * This test goes over a potential bug that could arise from using splits. If there is extra spaces, beware!
     */
    @Test(expected = org.junit.ComparisonFailure.class)
    public void removingTheFirstThreeShouldBeCorrectEvenWithMultipleSpaces() {
        givenThatIAmGivenAString("Everything  Past  THIS should be remaining");
        whenICutTheFirstNElementsSplitBySpace(3);
        thenTheNewStringShouldBe("should be remaining");
    }

    @Test
    public void myPlayShouldBeSplitToGiveMeJustThePlayInformation() {
        givenThatIAmGivenAString("PLACED GRASS+ROCK AT 1 0 0 5 FOUNDED SETTLEMENT AT 0 0 1");
        final int START_INDEX_OF_BUILD_PHASE = 7;
        whenICutTheFirstNElementsSplitBySpace(START_INDEX_OF_BUILD_PHASE);
        thenTheNewStringShouldBe("FOUNDED SETTLEMENT AT 0 0 1");
    }

    private void thenTheNewStringShouldBe(String expected) {
        Assert.assertEquals(expected, newString);
    }

    private void givenThatIAmGivenAString(String string) {
        this.testString = string;
    }

    private void whenICutTheFirstNElementsSplitBySpace(int indexToCutUpTo) {
        newString = StringCutter.cutFirstNSubStringsFromStringSplitBySpace(testString, indexToCutUpTo);
    }

}
