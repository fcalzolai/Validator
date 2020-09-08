package org.validator;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

public class ThrowableValidatorTest {

  @Test
  public void getValidations() {
    ThrowableValidator<Integer, Integer> validator = new ThrowableValidator<>(List.of(1, 2, 3, 4), divededBy(3));

    Validation<Seq<Throwable>, Seq<Integer>> validations = validator.getValidations();
    Assert.assertEquals(4, validations.get().size());
  }

  @Test
  public void getValidationsWithError() {
    ThrowableValidator<Integer, Integer> validator = new ThrowableValidator<>(List.of(1, 2, 3, 4), divededBy(0));

    Validation<Seq<Throwable>, Seq<Integer>> validations = validator.getValidations();
    Assert.assertEquals(4, validations.getError().size());
  }

  @Test
  public void getValidationsWithOneError() {
    ThrowableValidator<Integer, Integer> validator = new ThrowableValidator<>(List.of(1, 2, 0, 4), numeratorDivededBy(3));

    Validation<Seq<Throwable>, Seq<Integer>> validations = validator.getValidations();
    Assert.assertEquals(1, validations.getError().size());
  }

  private static Function<Integer, Integer> divededBy(int denominator) {
    return i -> i / denominator;
  }

  private static Function<Integer, Integer> numeratorDivededBy(int numerator) {
    return i -> numerator / i;
  }

}