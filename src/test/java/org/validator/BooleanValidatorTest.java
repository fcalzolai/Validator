package org.validator;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

public class BooleanValidatorTest {

  private static final List<Integer> ITEMS = List.of(1, 2, 3, 4);
  private static final BooleanValidator<Integer> GREATER_THAN_0 = new BooleanValidator<>(ITEMS, isGreaterThan(0));
  private static final BooleanValidator<Integer> GREATER_THAN_3 = new BooleanValidator<>(ITEMS, isGreaterThan(3));

  @Test
  public void getValidations() {
    Validation<Seq<Boolean>, Seq<Boolean>> validations = GREATER_THAN_0.getValidations();
    Assert.assertEquals(ITEMS.size(), validations.get().size());
  }

  @Test
  public void getValidationsWithErrors() {
    Validation<Seq<Boolean>, Seq<Boolean>> validations = GREATER_THAN_3.getValidations();
    Assert.assertEquals(3, validations.getError().size());
  }

  private static Function<Integer, Boolean> isGreaterThan(int greaterThan) {
    return i -> i > greaterThan;
  }

}