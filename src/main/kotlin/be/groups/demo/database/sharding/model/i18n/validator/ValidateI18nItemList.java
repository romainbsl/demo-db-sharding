package be.groups.demo.database.sharding.model.i18n.validator;

import java.lang.annotation.*;
import javax.validation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = I18nItemListValidator.class)
public @interface ValidateI18nItemList {
  String message() default "{be.groups.domains.utils.i18n.items.message}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
