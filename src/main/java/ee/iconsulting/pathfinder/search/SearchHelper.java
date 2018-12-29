package ee.iconsulting.pathfinder.search;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchHelper implements ISearchHelper {
    private final Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+^$\\\\|]");
    private Pattern pattern = null;

    public SearchHelper(String search, boolean extended) {
        if (search == null) {
            return;
        }
        if (!(search.startsWith("'") && search.endsWith("'"))) {
            pattern = Pattern.compile(search);
        } else {
            search = StringUtils.strip(search, "'");
            if (extended) {
                pattern = Pattern.compile(search);
            } else {
                pattern = Pattern.compile(transformSearchMask(search));
            }
        }
    }

    @Override
    public boolean match(String string) {
        if (pattern == null) {
            return true;
        }
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }

    private String transformSearchMask(String mask) {
        return "^" + SPECIAL_REGEX_CHARS.matcher(mask).replaceAll("\\\\$0")
                .replaceAll("\\?", ".")
                .replaceAll("\\*", ".*?") + "$";
    }
}
