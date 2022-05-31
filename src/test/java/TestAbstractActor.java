import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.noahnewmanmack.polis.actor.IActor;
import com.noahnewmanmack.polis.actor.character.Character;
import com.noahnewmanmack.polis.actor.character.ICharacter;
import com.noahnewmanmack.polis.actor.faction.Faction;
import com.noahnewmanmack.polis.actor.faction.IFaction;
import com.noahnewmanmack.polis.enums.Autonomy;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;

public class TestAbstractActor {

  IActor character;
  IActor subFaction;
  IActor faction;
  Character character2;


  @Before
  public void setUp() {

    this.character = new Character(UUID.randomUUID(),"Bill", "Test Character");
    this.subFaction = new Faction(UUID.randomUUID(),"Lollipop Guild", "Subfaction");
    this.faction = new Faction(UUID.randomUUID(),"The Candy Kingdom", "Kingdom of Candy!");
    this.character2 = new Character(UUID.randomUUID(),"Bob", "Alternate");

    this.subFaction.receive(100);
    this.character.receive(1000);
    this.faction.receive(10);

    this.subFaction.swearFealty((IFaction) this.faction, Autonomy.MEDIUM_AUTONOMY);
    this.character.swearFealty((IFaction) this.subFaction);
  }

  @Test
  public void setUpTest() {

    //assert balances are correct.
    assertEquals(character.receive(0), 1000,0);
    assertEquals(this.subFaction.receive(0), 100,0);
    assertEquals(faction.receive(0), 10,0);

    //Assert integrity of fealty tree
    assertEquals(this.character.topOfChain(), this.faction);
    assertTrue(character.isLesser((IFaction) subFaction));
    assertTrue(character.isLesser((IFaction) faction));
  }

  @Test
  public void testTaxationAndTransfers (){
    character2.swearFealty((IFaction) subFaction);
    this.character.transfer(500, character2);
    assertEquals(character2.receive(0), 500*.85,0.01);
    assertEquals(this.subFaction.receive(0), (500*.15)*(.8)+100,.01);
    assertEquals(this.faction.receive(0), 500*(.2)*(.15) + 10, .01);
    assertEquals(this.character.receive(0), 500 ,.01);
  }

  @Test
  public void CreateGroup() {
    character2.swearFealty((Faction) this.faction);

    // Should have same top of chain.
    assertEquals(character2.topOfChain(), character.topOfChain());
    // should have different direct authorities with characters.
    assertFalse(character2.isDirectLesser((IFaction) this.faction));
    //should not be direct peers with the subfaction.
    assertFalse(character2.isPeer(this.subFaction));

    ICharacter character3 = new Character(UUID.randomUUID(), "Bill", "blah");
    character3.swearFealty((Faction) this.faction);
    // should be in same sub-faction as char2
    assertTrue(character3.isPeer(character2));
    //should not be direct lesser of the main faction
    assertFalse(character2.isDirectLesser((IFaction) this.faction));
    // but should still be indirect lesser.
    assertTrue(character2.isLesser((IFaction) this.faction));
  }


}
