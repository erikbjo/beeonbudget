package no.ntnu.idatg1002.budgetapplication.backend;

/**
 * The security questions that the users can choose from.
 *
 * @author Simon Husås Houmb
 * @version 1.0 (2023-03-15)
 */
public enum SecurityQuestion {
  /**
   * Indicates the security question: "In what city were you born?".
   */
  NAME_OF_CITY("In what city were you born?"),
  /**
   * Indicates the security question: "What is the name of your first pet?".
   */
  FIRST_PET("What is the name of your first pet?"),
  /**
   * Indicates the security question: "What is your mother's maiden name?".
   */
  MAIDEN_NAME("What is your mother's maiden name?"),
  /**
   * Indicates the security question: "What high school did you attend?".
   */
  HIGH_SCHOOL("What high school did you attend?"),
  /**
   * Indicates the security question: "What was the name of your elementary school?".
   */
  ELEMENTARY_SCHOOL("What was the name of your elementary school?"),
  /**
   * Indicates the security question: "What year was your father born?".
   */
  FATHER_BORN("What year was your father born?"),
  /**
   * Indicates the security question: "What brand was your first car?".
   */
  CAR_BRAND("What brand was your first car?"),
  /**
   * Indicates the security question: "What was your favorite food as a child?".
   */
  FAVORITE_FOOD("What was your favorite food as a child?");

  private final String securityQuestionString;

  SecurityQuestion(String securityQuestionString) {
    this.securityQuestionString = securityQuestionString;
  }

  /**
   * Returns the text of a Security Question constant.
   *
   * @return The text of the Security Question as a String.
   */
  public String getSecurityQuestionString() {
    return securityQuestionString;
  }
}
