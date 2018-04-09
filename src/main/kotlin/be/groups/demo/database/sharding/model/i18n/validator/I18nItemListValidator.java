package be.groups.demo.database.sharding.model.i18n.validator;

import be.groups.demo.database.sharding.model.i18n.*;
import java.util.*;
import javax.validation.*;

/**
 * Validator for {@link Set<I18nItem>}
 */
public class I18nItemListValidator
    implements ConstraintValidator<ValidateI18nItemList, Set<I18nItem>> {
  public boolean isValid(Set<I18nItem> i18nItemList, ConstraintValidatorContext context) {
    // Check for duplicate language
    if (i18nItemList.stream()
                    .map(I18nItem::getI18nLanguage)
                    .allMatch(new HashSet<>()::add)) { return true; }

    //disable existing violation message
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(
        "{be.groups.domains.utils.i18n.items.hasDuplicateLanguage}")
           .addConstraintViolation();

    return false;
  }
}
