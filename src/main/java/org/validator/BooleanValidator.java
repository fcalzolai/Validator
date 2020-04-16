package org.validator;

import io.vavr.Value;
import io.vavr.control.Validation;

import java.util.function.Function;

public class BooleanValidator<T>  extends Validator<T, Boolean, Boolean> {

  public BooleanValidator(Value<T> items, Function<T, Boolean> validator) {
    super(items, validator.andThen(b -> b ? Validation.valid(true) : Validation.invalid(false)));
  }

  public BooleanValidator(Iterable<T> items, Function<T, Boolean> validator) {
    super(items, validator.andThen(b -> b ? Validation.valid(true) : Validation.invalid(false)));
  }
}
