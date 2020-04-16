package org.validator;

import io.vavr.Value;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class Validator<T, L, R> {

  private final Function<T, Validation<L, R>> validator;
  private final Value<T> items;

  private Validation<Seq<L>, Seq<R>> validations;

  public Validator(Value<T> items, Function<T, Validation<L, R>> validator) {
    requireNonNull(validator);
    requireNonNull(items);
    this.validator = validator;
    this.items = items;
  }

  public Validator(Iterable<T> items, Function<T, Validation<L, R>> validator) {
    this(List.ofAll(items), validator);
  }

  public Validation<Seq<L>, Seq<R>> validate() {
    Value<Validation<L, R>> value = items.map(validator);
    validations = Validation.sequence(value.map(validation -> validation.mapError(List::of)));
    return validations;
  }

  public Validation<Seq<L>, Seq<R>> getValidations() {
    if (validations == null) {
      validate();
    }

    return validations;
  }

  public Seq<L> getError() {
    return getValidations().getError();
  }

  public Seq<R> get() {
    return getValidations().get();
  }

  public Seq<R> getOrElseThrow(Function<Seq<L>, Throwable> supplier) throws Throwable {
    requireNonNull(supplier, "Supplier is null");
    if (getValidations().isEmpty()) {
      throw supplier.apply(getError());
    } else {
      return get();
    }
  }

  public Seq<R> getOrElseThrow2(Function<String, Throwable> supplier) throws Throwable {
    requireNonNull(supplier, "Supplier is null");
    if (getValidations().isEmpty()) {
      String errMsg = getError()
              .mkString("[", ",", "]");
      throw supplier.apply(errMsg);
    } else {
      return get();
    }
  }

  public Seq<R> getOrElseThrow(Class<? extends Throwable> clazz) throws Throwable {
    requireNonNull(clazz);
    Function<String, Throwable> exceptionSupplier = (errMsg) -> {
      try {
        return clazz.getDeclaredConstructor().newInstance(errMsg);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    };
    return getOrElseThrow2(exceptionSupplier);
  }
}
