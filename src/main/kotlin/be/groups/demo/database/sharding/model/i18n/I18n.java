package be.groups.demo.database.sharding.model.i18n;

import be.groups.demo.database.sharding.model.i18n.validator.*;
import java.util.*;
import java.util.stream.*;
import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.*;

import static javax.persistence.GenerationType.*;

/**
 * This class represents a multilingual string. It has a set of {@link I18nItem}'s
 *
 * @author michotte
 */
@Entity
@Table(name = "MULTILINGUAL_STRING")
public class I18n {

  //region Mapped Fields
  /**
   * technical id
   */
  /* Persistence annotations */
  @Id
  @Column(name = "ID", nullable = false)
  @SequenceGenerator(name = "I18nSeqGenerator", sequenceName = "SQ_TB_MULTILINGUAL_STRING_ID", allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "I18nSeqGenerator")
  /* Validation annotations */
  @NotNull(message = "{be.groups.domains.utils.i18n.id.isNull}")
  private Long id = -1L;

  /* Persistence annotations */
  @Column(name = "NAME")
  private String name;

  /* Persistence annotations */
  @OneToMany(mappedBy = "i18n", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  /* Validation annotations */
  @NotEmpty(message = "{be.groups.domains.utils.i18n.items.isEmpty}")
  @ValidateI18nItemList
  private Set<@Valid I18nItem> items;
  //endregion

  //region Transient Fields
  /**
   * Immutable hashCode, generated on first call of {@link I18n#hashCode()}
   */
  @Transient
  private Integer computedHashCode;
  //endregion

  //region Constructor
  public I18n() {
    super();
  }

  private I18n(Builder builder) {
    setName(builder.name);
    setItems(builder.items);
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
   * Get the name
   *
   * @return teh name
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name
   *
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get the set of items
   *
   * @return the set of items
   */
  public Set<I18nItem> getItems() {
    if (items == null) items = new HashSet<>();
    return items;
  }

  /**
   * Set {@code items} as children of this :<br>
   * 1. reset parent of each {@link I18nItem} in {@link I18n#items} of
   * {@code this} if it is not null by calling
   * {@link I18nItem#setI18n(I18n)} with null argument<br>
   * 2. set {@link I18n#items} of {@code this} to {@code items}<br>
   * 3. set {@code this} as parent of each {@link I18nItem} in {@code items} if it
   * is not null by calling {@link I18nItem#setI18n(I18n)}
   *
   * @param items the {@link Set< I18nItem >} to set as children of this
   * @see I18nItem#setI18n(I18n)
   */
  public void setItems(Set<I18nItem> items) {
    if (this.items != null) {
      Iterator<I18nItem> itemsIts = this.items.iterator();
      while (itemsIts.hasNext()) {
        I18nItem item = itemsIts.next();
        // remove from set to avoid ConcurrentModificationException
        itemsIts.remove();
        item.setI18n(null);
      }
    }
    this.items = items;
    if (this.items != null) {
      this.items.forEach(v -> v.setI18n(this));
    }
  }

  /**
   * Add {@code item} as a child of {@code this} :<br>
   * If {@code item} is not null and not already contained in the list of
   * {@link I18n#items} of {@code this} :<br>
   * 1. Add {@code item} to the list of {@link I18n#items} of {@code this}<br>
   * 2. Set {@code this} as parent of {@code item} by calling
   * {@link I18nItem#setI18n(I18n)}
   *
   * @param item the {@link I18nItem} to add as a child of {@code this}
   * @see I18nItem#setI18n(I18n)
   */
  public void addItem(I18nItem item) {
    if (this.items == null) {
      this.items = new HashSet<>();
    }
    if (item != null && !this.items.contains(item)) {
      this.items.add(item);
      item.setI18n(this);
    }
  }

  /**
   * Remove {@code item} as a child of {@code this} :<br>
   * If {@code item} is not null and contained in the list of {@link I18n#items} of
   * {@code this} :<br>
   * 1. Remove {@code item} from the list of {@link I18n#items} of {@code this}<br>
   * 2. Reset parent of {@code item} by calling
   * {@link I18nItem#setI18n(I18n)} with null argument
   *
   * @param item the {@link I18nItem} to remove as a child of {@code this}
   * @see I18nItem#setI18n(I18n)
   */
  public void removeItem(I18nItem item) {
    if (item != null && this.items != null && this.items.contains(item)) {
      this.items.remove(item);
      item.setI18n(null);
    }
  }
  //endregion

  //region Helpers

  /**
   * Get the item for which the {@link I18nItem#getI18nLanguage()} equals the {@code i18nLanguage} passed if it
   * exists, null otherwise
   *
   * @param i18nLanguage the i18nLanguage of the item to be returned
   * @return the item for which the {@link I18nItem#getI18nLanguage()} equals the {@code i18nLanguage} passed if
   * it exists, null otherwise
   */
  public I18nItem getItem(I18nLanguage i18nLanguage) {
    return items.stream().filter(i -> i.getI18nLanguage().equals(i18nLanguage)).findFirst().orElse(null);
  }

  /*
   * Methods
   */

  /**
   * Get the text of the {@link I18nItem} whose {@link I18nItem#getI18nLanguage()} is {@link
   * I18nLanguage#EN} if it exists.<br>If not, returns the text of the first item arbitrarily if it exists. <br> If not,
   * returns an empty string.
   *
   * @return the text of the {@link I18nItem} whose {@link I18nItem#getI18nLanguage()} is {@link
   * I18nLanguage#EN} if it exists.<br>If not, returns the text of the first item arbitrarily if it exists. <br> If not, an
   * empty string.
   */
  public String asText() {
    String text = asText(I18nLanguage.EN);
    if (text.isEmpty()) {
      Optional<I18nItem> item = items.stream().findFirst();
      if (item.isPresent()) {
        text = item.get().getText();
      }
    }
    return text;
  }

  /**
   * Get the {@link I18nItem#text} whose {@link I18nItem#getI18nLanguage()} is equal to {@code
   * i18nLanguage} parameter if it exists.<br>If not, returns an empty string.
   *
   * @return the text of the {@link I18nItem} whose {@link I18nItem#getI18nLanguage()} is equal
   * to {@code i18nLanguage} parameter if it exists.<br>If not, an empty string.
   */
  public String asText(I18nLanguage i18nLanguage) {
    Optional<I18nItem> item = items.stream().filter(i -> i.getI18nLanguage() == i18nLanguage)
                                   .findFirst();
    if (item.isPresent()) {
      return item.get().getText();
    }
    return "";
  }

  /**
   * Add a {@link I18nItem} in {@link I18n#items} and add the reference {@link
   * I18n} as parent into {@link I18nItem#i18n}
   *
   * @param i18nLanguage the i18nLanguage of the item to be inserted
   * @param text the test of the item to be inserted
   */
  public void addItem(I18nLanguage i18nLanguage, String text) {
    addItem(new I18nItem(i18nLanguage, text));
  }

  /**
   * Duplicate the values of $this into a new instance of {@link I18n}
   *
   * @return a new instance of {@link I18n} with the non unique values of $this.
   *
   * TODO implement in a library
   */
  public I18n copy() {
    I18n newI18n = new I18n();

    newI18n.setId(-1L);
    newI18n.setName(getName());
    newI18n.setItems(
        getItems().stream().map(I18nItem::copy)
                  .collect(Collectors.toSet())
    );

    newI18n.getItems().forEach(
        item -> item.setI18n(newI18n));

    return newI18n;
  }

  //endregion

  //region Builder
  public static class Builder {
    Set<I18nItem> items = new HashSet<>();
    private String name;

    private Builder() {}

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder item(I18nItem item) {
      items.add(item);
      return this;
    }

    public I18n build() {
      return new I18n(this);
    }

  }
  //endregion

  //region toString / equals / hashCode
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("I18n{" + "name='" + name + '\'' + ", items=");
    for (I18nItem item : items) {
      sb.append("\n\t")
        .append(item.toString());
    }
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (!(o instanceof I18n)) { return false; }

    I18n other = (I18n) o;

    return id != null && Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    if (computedHashCode == null) {
      computedHashCode = id == null ? super.hashCode()
                                    : Objects.hashCode(id);
    }

    return computedHashCode;
  }
  //endregion
}
