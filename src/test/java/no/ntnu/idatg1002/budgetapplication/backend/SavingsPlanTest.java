package no.ntnu.idatg1002.budgetapplication.backend;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg1002.budgetapplication.backend.SavingsPlan;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class SavingsPlanTest {
  private SavingsPlan goal;

  @BeforeEach
  void setUp(){
    goal = new SavingsPlan("test");
  }
  @AfterEach
  void tearDown(){}

  @Nested
  class setGoalNameTest {
    @Test
    void goalNameIsNull() {
      Exception thrown = assertThrows(IllegalArgumentException.class,
          () -> goal.setGoalName(null));
      assertEquals("Goal name must not be empty or blank.", thrown.getMessage());
    }

    @Test
    void goalNameIsEmpty() {
      Exception thrown = assertThrows(IllegalArgumentException.class,
          () -> goal.setGoalName(""));
      assertEquals("Goal name must not be empty or blank.", thrown.getMessage());
    }

    @Test
    void goalNameIsValid() {
      assertDoesNotThrow(() -> goal.setGoalName("My Goal"));
      assertEquals("My Goal", goal.getGoalName());
    }
  }

  @Nested
  class setTotalGoalAmount {
    @Test
    void totalGoalAmountIsLessThanZero() {
      Exception thrown = assertThrows(IllegalArgumentException.class,
          () -> goal.setTotalGoalAmount(-1));
      assertEquals("Total goal amount must be above zero.", thrown.getMessage());
    }

    @Test
    void totalGoalAmountIsZero() {
      Exception thrown = assertThrows(IllegalArgumentException.class,
          () -> goal.setTotalGoalAmount(0));
      assertEquals("Total goal amount must be above zero.", thrown.getMessage());
    }

    @Test
    void totalGoalAmountIsAboveZero() {
      assertDoesNotThrow(() -> goal.setTotalGoalAmount(1));
      assertEquals(1, goal.getTotalGoalAmount());
    }
  }

  @Nested
  class setWantedSavingTime {
    @Test
    void wantedSavingTimeIsLessThanZero() {
      Exception thrown = assertThrows(IllegalArgumentException.class,
          () -> goal.setWantedSavingTime(-1));
      assertEquals("Wanted saving time amount must be above zero.", thrown.getMessage());
    }

    @Test
    void wantedSavingTimeIsZero() {
      Exception thrown = assertThrows(IllegalArgumentException.class,
          () -> goal.setWantedSavingTime(0));
      assertEquals("Wanted saving time amount must be above zero.", thrown.getMessage());
    }

    @Test
    void wantedSavingTimeIsAboveZero() {
      assertDoesNotThrow(() -> goal.setWantedSavingTime(1));
      assertEquals(1, goal.getWantedSavingTime());
    }

  }

  @Nested
  class setWantedMonthlySavingAmount {
    @Test
    void wantedMonthlySavingAmountIsLessThanZero() {
      Exception thrown = assertThrows(IllegalArgumentException.class,
          () -> goal.setWantedMonthlySavingAmount(-1));
      assertEquals("Wanted monthly saving amount must be above zero.", thrown.getMessage());
    }

    @Test
    void wantedMonthlySavingAmountIsZero() {
      Exception thrown = assertThrows(IllegalArgumentException.class,
          () -> goal.setWantedMonthlySavingAmount(0));
      assertEquals("Wanted monthly saving amount must be above zero.", thrown.getMessage());
    }

    @Test
    void wantedMonthlySavingAmountIsAboveZero() {
      assertDoesNotThrow(() -> goal.setWantedMonthlySavingAmount(1));
      assertEquals(1, goal.getWantedMonthlySavingAmount());
    }

  }

  @Test
  void testEstimateTime(){
    goal.setWantedMonthlySavingAmount(100);
    assertEquals(10, goal.getEstimatedSavingTime());
  }

  @Test
  void testEstimateAmount(){
    goal.setWantedSavingTime(10);
    assertEquals(100, goal.getEstimatedMonthlySavingAmount());
  }
}
