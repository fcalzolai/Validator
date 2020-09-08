package org.validator;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.fail;

public class OptionValidatorTest {

  private static final List<Integer> ITEMS = List.of(1, 2, 3, 4);
  private static final OptionValidator<Integer, String> GREATER_THAN_0 = new OptionValidator<>(ITEMS, isGreaterThan(0));
  private static final OptionValidator<Integer, String> GREATER_THAN_3 = new OptionValidator<>(ITEMS, isGreaterThan(3));

  @Test
  public void getValidationsError() {
    Validation<Seq<Option<String>>, Seq<Option<String>>> validation = GREATER_THAN_3.getValidations();
    Assert.assertEquals(3, validation.getError().size());
  }

  @Test
  public void getValidations() {
    Validation<Seq<Option<String>>, Seq<Option<String>>> validation = GREATER_THAN_0.getValidations();
    Assert.assertEquals(ITEMS.size(), validation.get().size());
  }

  @Test
  public void getOrElseThrow() {
    try {
      Seq<Option<String>> res = GREATER_THAN_0.getOrElseThrow((errors) -> new Exception());
      Assert.assertEquals(ITEMS.size(), res.size());
    } catch (Throwable throwable) {
      fail(throwable.getMessage());
    }
  }

  @Test
  public void testGetOrElseThrow() {
    Seq<Option<String>> res = null;
    try {
      res = GREATER_THAN_0.getOrElseThrow(Exception.class);
    } catch (Throwable throwable) {
      fail(throwable.getMessage());
    }
    Assert.assertEquals(ITEMS.size(), res.size());

  }

  private static Function<Integer, Option<String>> isGreaterThan(int greaterThan) {
    return i -> i > greaterThan
            ? Option.of(Integer.toString(i))
            : Option.none();
  }
}
