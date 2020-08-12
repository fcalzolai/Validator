package org.validator;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

import static io.vavr.control.Validation.invalid;
import static io.vavr.control.Validation.valid;
import static org.junit.Assert.fail;

public class ValidatorTest {

  @Test
  public void getValidationsError() {
    Validator<Integer, String, Boolean> booleanValidator = new Validator<>(List.of(1, 2, 3, 4), isGreaterThan(3));

    Validation<Seq<String>, Seq<Boolean>> validation = booleanValidator.getValidations();
    Assert.assertEquals(3, validation.getError().size());
  }

  @Test
  public void getValidations() {
    List<Integer> items = List.of(1, 2, 3, 4);
    Validator<Integer, String, Boolean> booleanValidator = new Validator<>(items, isGreaterThan(0));

    Validation<Seq<String>, Seq<Boolean>> validation = booleanValidator.getValidations();
    Assert.assertEquals(items.size(), validation.get().size());
  }

  @Test
  public void getOrElseThrow() {
    List<Integer> items = List.of(1, 2, 3, 4);
    Validator<Integer, String, Boolean> booleanValidator = new Validator<>(items, isGreaterThan(0));

    try {
      Seq<Boolean> res = booleanValidator.getOrElseThrow((errors) -> new Exception());
      Assert.assertEquals(items.size(), res.size());
    } catch (Throwable throwable) {
      fail(throwable.getMessage());
    }
  }

  @Test
  public void testGetOrElseThrow() {
    List<Integer> items = List.of(1, 2, 3, 4);
    Validator<Integer, String, Boolean> booleanValidator1 = new Validator<>(items, isGreaterThan(0));

    Seq<Boolean> res = null;
    try {
      res = booleanValidator1.getOrElseThrow(Exception.class);
    } catch (Throwable throwable) {
      fail(throwable.getMessage());
    }
    Assert.assertEquals(items.size(), res.size());

  }

  private static Function<Integer, Validation<String, Boolean>> isGreaterThan(int greaterThan) {
    return i -> i > greaterThan
            ? valid(true)
            : invalid(String.format("%s is not greater than %s", i, greaterThan));
  }
}
