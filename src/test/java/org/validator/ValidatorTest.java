package org.validator;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

import static io.vavr.control.Validation.invalid;
import static io.vavr.control.Validation.valid;
import static org.junit.Assert.fail;

public class ValidatorTest {

  private static final List<Integer> items = List.of(1, 2, 3, 4);

  private Validator<Integer, String, Boolean> booleanValidator1;
  private Validator<Integer, String, Boolean> booleanValidator2;

  @Before
  public void before() {
    booleanValidator1 = new Validator<>(items, isGreaterThan(0));
    booleanValidator2 = new Validator<>(items, isGreaterThan(3));
  }

//  @Test
  public void getValidationsError() {
    Validation<Seq<String>, Seq<Boolean>> validation2 = booleanValidator2.getValidations();
    Assert.assertEquals(3, validation2.getError().size());
   }

  @Test
  public void getValidations() {
    Validation<Seq<String>, Seq<Boolean>> validation1 = booleanValidator1.getValidations();
    Assert.assertEquals(items.size(), validation1.get().size());

    Validation<Seq<String>, Seq<Boolean>> validation2 = booleanValidator2.getValidations();
    Assert.assertEquals(3, validation2.getError().size());
  }

  @Test
  public void getOrElseThrow() {
    Seq<Boolean> res = null;
    try {
      res = booleanValidator1.getOrElseThrow((errors) -> new Exception());
    } catch (Throwable throwable) {
      fail(throwable.getMessage());
    }
    Assert.assertEquals(items.size(), res.size());

    try {
      booleanValidator1.getOrElseThrow((errors) -> new Exception());
      fail("Should have thrown an exception");
    } catch (Throwable throwable) {
    }
  }

  @Test
  public void getOrElseThrow2() {
    Seq<Boolean> res = null;
    try {
      res = booleanValidator1.getOrElseThrow2(Exception::new);
    } catch (Throwable throwable) {
      fail(throwable.getMessage());
    }
    Assert.assertEquals(items.size(), res.size());

    try {
      booleanValidator1.getOrElseThrow2(Exception::new);
      fail("Should have thrown an exception");
    } catch (Throwable throwable) {
    }
  }

  @Test
  public void testGetOrElseThrow() {
    Seq<Boolean> res = null;
    try {
      res = booleanValidator1.getOrElseThrow(Exception.class);
    } catch (Throwable throwable) {
      fail(throwable.getMessage());
    }
    Assert.assertEquals(items.size(), res.size());

    try {
      booleanValidator1.getOrElseThrow(Exception.class);
      fail("Should have thrown an exception");
    } catch (Throwable throwable) {
    }
  }

  private static Function<Integer, Validation<String, Boolean>> isGreaterThan(int greaterThan) {
    return i -> i > greaterThan
            ? valid(true)
            : invalid(String.format("%s is not greater than %s", i, greaterThan));
  }
}
