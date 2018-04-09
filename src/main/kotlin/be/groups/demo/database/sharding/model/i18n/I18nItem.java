package be.groups.demo.database.sharding.model.i18n;

import java.util.*;
import javafx.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

import static javax.persistence.GenerationType.*;

/**
 * This class represents an item of a {@link I18n} <br> It has a {@link I18nLanguage} and a text representing
 * the translation in that i18nLanguage
 *
 * @author michotte
 * @see I18n
 * @see I18nLanguage
 */
@Entity
@Table(name = "MULTILINGUAL_STRING_ITEM")
public class I18nItem {

  //region Mapped Fields
  /**
   * technical id
   */
  /* Persistence annotations */
  @Id
  @Column(name = "ID", nullable = false)
  @SequenceGenerator(name = "I18nItemSeqGenerator", sequenceName = "SQ_TB_MULTILINGUAL_STR_ITEM_ID", allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "I18nItemSeqGenerator")
  /* Validation annotations */
  @NotNull(message = "{be.groups.domains.utils.i18nItem.id.isNull}")
  private Long id = -1L;

  /* Persistence annotations */
  @ManyToOne
  @JoinColumn(name = "MULTILINGUAL_STRING_ID", referencedColumnName = "ID", nullable = false)
  private I18n i18n;

  /* Persistence annotations */
  @Column(name = "LANGUAGE", nullable = false)
  @Convert(converter = I18nLanguageConverter.class)
  /* Validation annotations */
  @NotNull(message = "{be.groups.domains.utils.i18nItem.i18nLanguage.isNull}")
  private I18nLanguage i18nLanguage;

  /* Persistence annotations */
  @Column(name = "TEXT")
  /* Validation annotations */
  @NotEmpty(message = "{be.groups.domains.utils.i18nItem.text.isEmpty}")
  private String text;
  //endregion

  //region Transient Fields
  /**
   * Immutable hashCode, generated on first call of {@link I18nItem#hashCode()}
   */
  @Transient
  private Integer computedHashCode;
  //endregion

  //region Constructor
  public I18nItem() {
    super();
  }

  public I18nItem(I18nLanguage i18nLanguage, String text) {
    this.i18nLanguage = i18nLanguage;
    this.text = text;
  }

  private I18nItem(Builder builder) {
    setI18nLanguage(builder.languageStringPair.getKey());
    setText(builder.languageStringPair.getValue());
  }

  public static Builder builder() {
    return new Builder();
  }
  //endregion

  //region Getter / Setter

  /**
   * Get the technical id
   *
   * @return the technical id
   */
  public Long getId() {
    return id;
  }

  /**
   * Set the technical id <br> This method is for Hibernate needs. It should never be used. <br> Technical ids are
   * auto-generated via a database sequence
   *
   * @param id the technical id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get the {@link I18n} that owns this
   *
   * @return the {@link I18n} that owns this
   */
  public I18n getI18n() {
    return i18n;
  }

  /**
   * Set {@code i18n} as parent of this :<br>
   * If {@link I18nItem#i18n} of {@code this} is null or if {@code
   * i18n} is not equal to it :<br>
   * 1. remove {@code this} as child of {@link I18nItem#i18n} of
   * {@code this} if it is not null by calling
   * {@link I18n#removeItem(I18nItem)}<<br>
   * 2. set {@link I18nItem#i18n} of {@code this} to {@code
   * i18n}<br>
   * 3. add {@code this} as child of {@code i18n} if it is not null by calling
   * {@link I18n#addItem(I18nItem)}<br>
   *
   * @param i18n the {@link I18n} to set as parent of this
   * @see I18n#removeItem(I18nItem)
   * @see I18n#addItem(I18nItem)
   */
  public void setI18n(I18n i18n) {
    if (this.i18n == null || !this.i18n.equals(i18n)) {
      if (this.i18n != null) {
        this.i18n.removeItem(this);
      }
      this.i18n = i18n;
      if (this.i18n != null) {
        this.i18n.addItem(this);
      }
    }
  }

  /**
   * Get the {@link I18nLanguage}
   *
   * @return the {@link I18nLanguage}
   */
  public I18nLanguage getI18nLanguage() {
    return i18nLanguage;
  }

  /**
   * Set the i18nLanguage
   *
   * @param i18nLanguage the i18nLanguage to set
   */
  public void setI18nLanguage(I18nLanguage i18nLanguage) {
    this.i18nLanguage = i18nLanguage;
  }

  /**
   * Get the text
   *
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * Set the text
   *
   * @param text the text to set
   */
  public void setText(String text) {
    this.text = text;
  }
  //endregion

  //region Helpers

  /**
   * Duplicate the values of $this into a new instance of {@link I18nItem}<br>
   * <b>WARNING</b> : The reference to the parent {@link I18n} of $this is not
   * duplicated into the new instance, in other words, the new instance
   * {@link I18nItem#i18n} is null
   *
   * @return a new instance of {@link I18nItem} with the non unique values of $this.
   */
  public I18nItem copy() {
    I18nItem newI18nItem = new I18nItem();

    newI18nItem.setId(-1L);
    newI18nItem.setI18nLanguage(getI18nLanguage());
    newI18nItem.setText(getText());

    return newI18nItem;
  }
  //endregion

  //region Builder
  public static class Builder {
    Pair<I18nLanguage, String> languageStringPair;

    private Builder() {}

    public Builder text(I18nLanguage i18nLanguage, String text) {
      languageStringPair = new Pair<>(i18nLanguage, text);
      return this;
    }

    public I18nItem build() {
      if (languageStringPair != null) {
        return new I18nItem(this);
      } else {
        return null;
      }
    }
  }
  //endregion

  //region tostring / equals / hashCode
  @Override
  public String toString() {
    return "I18nItem{" + "i18nLanguage=" + i18nLanguage + ", text='" + text + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (!(o instanceof I18nItem)) { return false; }

    I18nItem other = (I18nItem) o;

    return id != null &&
        Objects.equals(id, other.id) &&
        Objects.equals(i18nLanguage, other.i18nLanguage);
  }

  @Override
  public int hashCode() {
    if (computedHashCode == null) {
      computedHashCode = id == null ? super.hashCode()
                                    : Objects.hash(id, i18nLanguage);
    }

    return computedHashCode;
  }
  //endregion
}
