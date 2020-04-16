package org.validator;

import io.vavr.Value;
import io.vavr.control.Try;

import java.util.function.Function;

public class ThrowableValidator<T, R> extends Validator<T, Throwable, R> {

  public ThrowableValidator(Value<T> items, Function<T, R> validator) {
    super(items, (T t) -> Try.ofSupplier(() -> validator.apply(t)).toValidation());
  }

  public ThrowableValidator(Iterable<T> items, Function<T, R> validator) {
    super(items, (T t) -> Try.ofSupplier(() -> validator.apply(t)).toValidation());
  }
}
