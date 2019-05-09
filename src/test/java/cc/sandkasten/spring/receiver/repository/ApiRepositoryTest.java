package cc.sandkasten.spring.receiver.repository;

import cc.sandkasten.spring.receiver.domain.KeyCounter;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class ApiRepositoryTest {
    private static final String TEST_KEY = "testKey";
    private ApiRepository objectUnderTest;

    @Before
    public void initTest() {
        objectUnderTest = new ApiRepository();
    }

    @Test
    public void findIfPresent() {
        objectUnderTest.increment(TEST_KEY);
        assertThat(objectUnderTest.get(TEST_KEY).isPresent(), is(true));
        assertThat(objectUnderTest.get(TEST_KEY).get(), is(1));
    }

    @Test
    public void nullIfNotPresent() {
        assertThat(objectUnderTest.get(TEST_KEY).isPresent(), is(false));
    }

    @Test
    public void getCount() {
        assertThat(objectUnderTest.get(TEST_KEY).isPresent(), is(false));
        assertThat(objectUnderTest.incrementAndGetCount(TEST_KEY), is(1));
        assertThat(objectUnderTest.incrementAndGetCount(TEST_KEY), is(2));
        assertThat(objectUnderTest.get(TEST_KEY).isPresent(), is(true));
        assertThat(objectUnderTest.get(TEST_KEY).get(), is(2));
    }

    @Test
    public void getAll() {
        assertThat(objectUnderTest.getAll(), is(empty()));
        objectUnderTest.increment(TEST_KEY);
        assertThat(objectUnderTest.getAll(), hasSize(1));
        assertThat(objectUnderTest.getAll(), hasItem(new KeyCounter(TEST_KEY, 1)));
    }

    @Test
    public void reset() {
        assertThat(objectUnderTest.getAll(), is(empty()));
        objectUnderTest.increment(TEST_KEY);
        assertThat(objectUnderTest.getAll(), hasSize(1));
        objectUnderTest.reset();
        assertThat(objectUnderTest.getAll(), is(empty()));
    }
}