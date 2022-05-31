import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.noahnewmanmack.polis.actor.character.Character;
import com.noahnewmanmack.polis.actor.character.ICharacter;
import java.util.UUID;
import org.junit.*;

public class TestCharacter {

  ICharacter char1;
  ICharacter char2;

  @Before
  public void setUp() {
    char1 = new Character(UUID.randomUUID(), "John","test character 1");
    char2 = new Character(UUID.randomUUID(),"Bill", "second test character");
    char1.receive(100);
    char2.receive(100);
  }

  @Test
  public void testReceive() {
    // assert starting balances work and that
    assertEquals(char1.receive(0), char2.receive(0), 0);
    assertEquals(char1.receive(0), 100, 0);
    assertEquals(char2.receive(0), 100, 0);

    char1.transact(10);
    assertEquals(char1.receive(0), 90,0);
    assertEquals(char1.transact(0), 10, 0);

    assertNotEquals(char1.receive(0), char2.receive(0));
    assertNotEquals(char1.transact(0), char2.transact(0));

    char1.pocketToPocket(10, char2);
    assertEquals(char2.transact(0), 10, 0);
    assertEquals(char1.transact(0), 0, 0);

    char2.transact(-10);
    assertEquals(char2.transact(0), 0);
    assertEquals(char2.receive(0), 110, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadPocketToPocket() {
    char1.pocketToPocket(10, char2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadAddToPocket() {
    char1.addToPocket(-10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoCounterparty() {
    char1.transfer(1, null);
  }

}
