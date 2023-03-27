package no.ntnu.idatg1002.budgetapplication.backend;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class SavingsPlanTest {
  private SavingsPlan goal;

  @BeforeEach
  void setUp(){
    goal = new SavingsPlan("test", 1000, 0);
  }

  @Test
  void testEstimateTime(){
    goal.setWantedMonthlySavingAmount(100);
    assertEquals(10, goal.estimateSavingTime());
  }

  @Test
  void testEstimateAmount(){
    goal.setWantedSavingTime(10);
    assertEquals(100, goal.estimateMonthlySavingAmount());
  }




  @AfterEach
  void tearDown(){}
}
