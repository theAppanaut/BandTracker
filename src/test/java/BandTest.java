import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Band_instantiatesCorrectly_true() {
    Band newBand = new Band("Radiohead", "Alternative");
    assertEquals(true, newBand instanceof Band);
  }

  @Test
  public void getName_returnsBandName_String() {
    Band newBand = new Band("Radiohead", "Alternative");
    assertEquals("Radiohead", newBand.getName());
  }

  @Test
  public void getGenre_returnsBandGenre_String() {
    Band newBand = new Band("Radiohead", "Alternative");
    assertEquals("Alternative", newBand.getGenre());
  }

  @Test
  public void save_savesBand_true() {
    Band newBand = new Band("Radiohead", "Alternative");
    newBand.save();
    assertTrue(newBand.all().size() == 1);
  }

  @Test
  public void all_returnsListOfBands_List() {
    Band newBand = new Band("Radiohead", "Alternative");
    newBand.save();
    assertTrue(newBand.all() instanceof List);
  }
}
