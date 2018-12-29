package ee.iconsulting.test.util;

import ee.iconsulting.pathfinder.search.ISearchHelper;

public class TestSearchHelper implements ISearchHelper {
    @Override
    public boolean match(String string) {
        return true;
    }
}
