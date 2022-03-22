package seedu.address.model.person;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name, tags} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> {
                    String keyword_to_lower_case = keyword.toLowerCase(Locale.ROOT);
                    boolean nameMatch = person.getName().fullName.toLowerCase(Locale.ROOT)
                            .contains(keyword_to_lower_case);
                    boolean tagsMatch = person.getTags().stream().anyMatch(tag ->
                        tag.tagName.toLowerCase(Locale.ROOT).equals(keyword_to_lower_case));
                    return nameMatch || tagsMatch;
                    });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
