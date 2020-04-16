package org.validator;

import io.vavr.Value;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Try;
import io.vavr.control.Validation;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class Validators {

  public static <L, R, T> Validation<Seq<L>, Seq<R>> validate(
          Value<T> items,
          Function<T, Validation<L, R>> validator) {
    requireNonNull(items);
    requireNonNull(validator);
    Value<Validation<L, R>> validations = items.map(validator);
    return Validation.sequence(validations.map(validation -> validation.mapError(List::of)));
  }

  public static <L, R, T> Validation<Seq<L>, Seq<R>> validate(
          Iterable<T> items,
          Function<T, Validation<L, R>> validator) {
    return validate(List.ofAll(items), validator);
  }

  public static <R, T> Validation<Seq<Throwable>, Seq<R>> validateWithException(
          Value<T> items,
          Function<T, R> validator) {
    requireNonNull(items);
    requireNonNull(validator);

    Function<T, Validation<Throwable, R>> tryFunction = (T t) -> Try.ofSupplier(() -> validator.apply(t)).toValidation();

    Value<Validation<Throwable, R>> validations = items.map(tryFunction);
    return Validation.sequence(validations.map(validation -> validation.mapError(List::of)));
  }

  public static <R, T> Validation<Seq<Throwable>, Seq<R>> validateWithException(
          Iterable<T> items,
          Function<T, R> validator) {
    return validateWithException(List.ofAll(items), validator);
  }
}
