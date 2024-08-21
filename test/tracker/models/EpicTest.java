package tracker.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private Epic epicOne;
    private Epic epicTwo;

    @Test
    public void shouldReturnTrueWhenEpicsHaveTheSameId() {
        epicOne = new Epic("Epic one","Do something");
        epicTwo = new Epic("Epic two","Do something else");
        epicOne.setId(1);
        epicTwo.setId(1);
        assertTrue(epicOne.equals(epicTwo), "Epics with the same id should be equal");
    }
}