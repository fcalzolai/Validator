package org.validator;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

public class ThrowableValidatorTest {

  private static final List<Integer> ITEMS = List.of(1, 2, 3, 4);
  private static final List<Integer> ITEMS_WITH_ZERO = List.of(1, 2, 0, 4);
  private static final ThrowableValidator<Integer, Integer> DIVIDED_BY_3 = new ThrowableValidator<>(ITEMS, dividedBy(3));
  private static final ThrowableValidator<Integer, Integer> DIVIDED_BY_0 = new ThrowableValidator<>(ITEMS, dividedBy(0));
  private static final ThrowableValidator<Integer, Integer> NUMERATOR_DIVIDED_BY_3 = new ThrowableValidator<>(ITEMS_WITH_ZERO, numeratorDividedBy(3));

  @Test
  public void getValidations() {
    Validation<Seq<Throwable>, Seq<Integer>> validations = DIVIDED_BY_3.getValidations();
    Assert.assertEquals(4, validations.get().size());
  }

  @Test
  public void getValidationsWithError() {
    Validation<Seq<Throwable>, Seq<Integer>> validations = DIVIDED_BY_0.getValidations();
    Assert.assertEquals(4, validations.getError().size());
  }

  @Test
  public void getValidationsWithOneError() {
    Validation<Seq<Throwable>, Seq<Integer>> validations = NUMERATOR_DIVIDED_BY_3.getValidations();
    Assert.assertEquals(1, validations.getError().size());
  }

  private static Function<Integer, Integer> dividedBy(int denominator) {
    return i -> i / denominator;
  }

  private static Function<Integer, Integer> numeratorDividedBy(int numerator) {
    return i -> numerator / i;
  }

}