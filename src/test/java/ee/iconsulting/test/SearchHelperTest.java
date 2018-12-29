package ee.iconsulting.test;

import ee.iconsulting.pathfinder.search.SearchHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchHelperTest {
    @Test
    @DisplayName("SearchHelper without specified the search input")
    void searchHelperWithoutInput() {
        SearchHelper searchHelper = new SearchHelper(null, false);
        SearchHelper searchHelperExt = new SearchHelper(null, true);

        assertAll(
                () -> assertTrue(searchHelper.match(""), "Empty string, false extended"),
                () -> assertTrue(searchHelper.match("/dir-880176375/file-1073842118.java"),
                        "Full path, false extended"),
                () -> assertTrue(searchHelperExt.match(""), "Empty string, true extended"),
                () -> assertTrue(searchHelperExt.match("/dir-880176375/file-1073842118.java"),
                        "Full path, true extended")
        );
    }

    @Test
    @DisplayName("SearchHelper with specified the simple search input")
    void searchHelperWithSimpleSearch() {
        SearchHelper searchHelper = new SearchHelper("file-1437276269.java", false);

        assertAll(
                () -> assertTrue(searchHelper.match("/dir-880176375/file-1437276269.java"),
                        "Full path with appropriate file name"),
                () -> assertFalse(searchHelper.match("/dir-1637357383/dir-735358292/file-701516470.xhtml"),
                        "Full path with other file name"),
                () -> assertFalse(searchHelper.match(""), "Empty string")
        );
    }

    @Test
    @DisplayName("SearchHelper with specified the simple search with mask input")
    void searchHelperWithSimpleSearchMask() {
        SearchHelper searchHelper1 = new SearchHelper("'*.java'", false);
        SearchHelper searchHelper2 = new SearchHelper("'*.????'", false);
        SearchHelper searchHelper3 = new SearchHelper("'*dw[]^qd${}+dw?.*'", false);

        assertAll(
                () -> assertTrue(searchHelper1.match("/dir-880176375/file-1073842118.java"),
                        "'*.java' mask and appropriate file path"),
                () -> assertFalse(searchHelper1.match("/dir-880176375/file-1073842118.html"),
                        "'*.java' mask and inappropriate file path"),
                () -> assertTrue(searchHelper2.match("/dir-880176375/file-1073842118.java"),
                        "'*.????' mask and appropriate file path"),
                () -> assertFalse(searchHelper2.match("/dir-1637357383/dir-735358292/file-701516470.xhtml"),
                        "'*.????' mask and inappropriate file path"),
                () -> assertTrue(searchHelper3.match("/dir-1637357383/dw[]^qd${}+dwq.txt"),
                        "'*dw[]^qd${}+dw?.*' mask and appropriate file path")
        );
    }

    @Test
    @DisplayName("SearchHelper with specified the extended search input")
    void searchHelperWithExtendedSearch() {
        SearchHelper searchHelper = new SearchHelper("'.*?[a-z]{4}-\\d+\\.[a-z]+'", true);

        assertAll(
                () -> assertTrue(searchHelper.match("/dir-880176375/file-1073842118.java"),
                        "Appropriate file path"),
                () -> assertFalse(searchHelper.match("/dir-1637357383/dir-735358292/git-701516470.xhtml"),
                        "Inappropriate file path")
        );
    }
}
