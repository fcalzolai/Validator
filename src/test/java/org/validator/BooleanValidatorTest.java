package org.validator;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

public class BooleanValidatorTest {

  @Test
  public void getValidations() {
    List<Integer> items = List.of(1, 2, 3, 4);
    BooleanValidator<Integer> booleanValidator = new BooleanValidator<>(items, isGreaterThan(0));

    Validation<Seq<Boolean>, Seq<Boolean>> validations = booleanValidator.getValidations();
    Assert.assertEquals(items.size(), validations.get().size());
  }

  @Test
  public void getValidationsWithErrors() {
    List<Integer> items = List.of(1, 2, 3, 4);
    BooleanValidator<Integer> booleanValidator = new BooleanValidator<>(items, isGreaterThan(3));

    Validation<Seq<Boolean>, Seq<Boolean>> validations = booleanValidator.getValidations();
    Assert.assertEquals(3, validations.getError().size());
  }

  private static Function<Integer, Boolean> isGreaterThan(int greaterThan) {
    return i -> i > greaterThan;
  }

}