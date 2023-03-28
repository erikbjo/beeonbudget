package no.ntnu.idatg1002.budgetapplication.backend.savings;

import no.ntnu.idatg1002.budgetapplication.backend.SavingsPlan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SavingsPlanTest {

  SavingsPlan savingsPlan;

  @BeforeEach
  void setUp() {
    savingsPlan = new SavingsPlan("Test", 200, 100);
  }

  @Test
  void goalNameEqualTrue(){
    
  }

  @Test
  void goalNameEqualFalse(){

  }

  @AfterEach
  void tearDown() {
  }
}