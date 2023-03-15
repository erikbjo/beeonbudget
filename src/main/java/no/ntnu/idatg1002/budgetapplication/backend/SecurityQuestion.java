package no.ntnu.idatg1002.budgetapplication.backend;

/**
 * The security questions that the users can choose from.
 */
public enum SecurityQuestion {
  NAME_OF_CITY("In what city were you born?"),
  FIRST_PET("What is the name of your first pet?"),
  MAIDEN_NAME("What is your mother's maiden name?"),
  HIGH_SCHOOL("What high school did you attend?"),
  ELEMENTARY_SCHOOL("What was the name of your elementary school?"),
  FATHER_BORN("What year was your father born?"),
  CAR_BRAND("What brand was your first car?"),
  FAVORITE_FOOD("What was your favorite food as a child?");

  private final String securityQuestionString;

  SecurityQuestion(String securityQuestionString) {
    this.securityQuestionString = securityQuestionString;
  }

  public String getSecurityQuestionString() {
    return securityQuestionString;
  }
}
