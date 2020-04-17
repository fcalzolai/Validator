package org.validator;

import io.vavr.Value;
import io.vavr.control.Option;
import io.vavr.control.Validation;

import java.util.function.Function;

public class OptionValidator<T, U>  extends Validator<T, Option<U>, Option<U>> {

  public OptionValidator(Value<T> items, Function<T, Option<U>> validator) {
    super(items, validator.andThen(b -> b.isDefined() ? Validation.valid(b) : Validation.invalid(b)));
  }

  public OptionValidator(Iterable<T> items, Function<T, Option<U>> validator) {
    super(items, validator.andThen(b -> b.isDefined() ? Validation.valid(b) : Validation.invalid(b)));
  }
}
