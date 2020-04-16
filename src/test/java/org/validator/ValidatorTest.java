package org.validator;

import io.vavr.collection.Seq;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

import static io.vavr.control.Validation.invalid;
import static io.vavr.control.Validation.valid;

public class ValidatorTest {

  private static final List<Integer> items = List.of(1, 2, 3, 4);

  private Validator<Integer, String, Boolean> bv1;
  private Validator<Integer, String, Boolean> bv2;

  @Before
  public void before() {
    bv1 = new Validator<>(items, isGreaterThan(0));
    bv2 = new Validator<>(items, isGreaterThan(3));
  }

  @Test
  public void getValidationsError() {
    Try<Seq<Boolean>> seqs = bv2.getValidations().toTry();
    System.out.println(seqs);
   }

  @Test
  public void getValidations() {
    Validation<Seq<String>, Seq<Boolean>> validation1 = bv1.getValidations();
    Validation<Seq<String>, Seq<Boolean>> validation2 = bv2.getValidations();
    System.out.println(validation1);;
  }

  private static Function<Integer, Validation<String, Boolean>> isGreaterThan(int greaterThan) {
    return i -> i > greaterThan
            ? valid(true)
            : invalid(String.format("%s is not greater than %s", i, greaterThan));
  }
}
