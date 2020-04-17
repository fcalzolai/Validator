package org.validator;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.fail;

public class OptionValidatorTest {

  private static final List<Integer> items = List.of(1, 2, 3, 4);

  private OptionValidator<Integer, String> booleanValidator1;
  private OptionValidator<Integer, String> booleanValidator2;

  @Before
  public void before() {
    booleanValidator1 = new OptionValidator<>(items, isGreaterThan(0));
    booleanValidator2 = new OptionValidator<>(items, isGreaterThan(3));
  }

//  @Test
  public void getValidationsError() {
    Validation<Seq<Option<String>>, Seq<Option<String>>> validation2 = booleanValidator2.getValidations();
    Assert.assertEquals(3, validation2.getError().size());
   }

  @Test
  public void getValidations() {
    Validation<Seq<Option<String>>, Seq<Option<String>>> validation1 = booleanValidator1.getValidations();
    Assert.assertEquals(items.size(), validation1.get().size());

    Validation<Seq<Option<String>>, Seq<Option<String>>> validation2 = booleanValidator2.getValidations();
    Assert.assertEquals(3, validation2.getError().size());
  }

  @Test
  public void getOrElseThrow() {
    Seq<Option<String>> res = null;
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
    Seq<Option<String>> res = null;
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
    Seq<Option<String>> res = null;
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

  private static Function<Integer, Option<String>> isGreaterThan(int greaterThan) {
    return i -> i > greaterThan
            ? Option.of(Integer.toString(i))
            : Option.none();
  }
}
