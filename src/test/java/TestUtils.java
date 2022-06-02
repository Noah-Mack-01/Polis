import static org.junit.Assert.assertEquals;

import com.noahnewmanmack.polis.utils.StorageUtils;
import java.io.IOException;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;

public class TestUtils {

  @Before
  public void setup() throws IOException {
    StorageUtils.loadFromFile("test.json");
  }

  @Test
  public void testLoad() throws NullPointerException {
    UUID faction1UUID = UUID.randomUUID();
    UUID character1UUID = UUID.randomUUID();
    String test = "Test", desc = "Description";
    StorageUtils.createFaction(faction1UUID, test, desc, 100);
    assertEquals(StorageUtils.getFaction(faction1UUID).receive(0), 100, 0);
    assertEquals(StorageUtils.getFaction(faction1UUID).getName(), "Test");
    assertEquals(StorageUtils.getFaction(faction1UUID).getDesc(), "Description");
  }

}
