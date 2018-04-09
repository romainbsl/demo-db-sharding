package be.groups.demo.database.sharding.model.i18n;

import javax.persistence.*;

/**
 * Converter class for {@link I18nLanguage} enum
 *
 * @author michotte
 * @see I18nLanguage
 */
@Converter
public class I18nLanguageConverter implements AttributeConverter<I18nLanguage, String> {

  @Override
  public String convertToDatabaseColumn(I18nLanguage attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @Override
  public I18nLanguage convertToEntityAttribute(String dbData) {
    if (dbData != null) {
      return I18nLanguage.codeFromValue(dbData);
    } else {
      return null;
    }
  }
}
