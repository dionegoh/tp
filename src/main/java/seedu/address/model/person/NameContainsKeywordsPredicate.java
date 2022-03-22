package seedu.address.model.person;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name, tags, logs title} matches any of the keywords given.
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

                    //check if (substring) name of a person matches keyword
                    boolean nameMatch = person.getName().fullName.toLowerCase(Locale.ROOT)
                            .contains(keyword_to_lower_case);

                    //check if tag of a person matches keyword
                    boolean tagsMatch = person.getTags().stream().anyMatch(tag ->
                        tag.tagName.toLowerCase(Locale.ROOT).equals(keyword_to_lower_case));

                    //check if (substring) logs title of a person matches keyword
                    boolean logsMatch = person.getLogs().stream().anyMatch(log ->
                        log.getTitle().fullName.toLowerCase(Locale.ROOT).contains(keyword_to_lower_case));

                    return nameMatch || tagsMatch || logsMatch ;
                    });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
