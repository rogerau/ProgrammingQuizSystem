
//Create TFQuestion subclass that extends Question class

public class TFQuestion extends Question {

	// instance variables
	private boolean answer;

	// Constructors
	// Default constructor
	public TFQuestion() {

	}

	// Constructor with 3 parameters
	public TFQuestion(String qText, boolean answer, double point) {
		setqText(qText);
		setAnswer(String.valueOf(answer));
		setPoint(point);
	}

	// Instance methods (getters and setters)

	public boolean getAnswer() {
		return answer;
	}

	// Method that sets the correct answer base on what it is send from the
	// constructor
	public void setAnswer(String answer) {
		if (answer.equalsIgnoreCase("True")) {
			this.answer = true;
		} else {
			this.answer = false;
		}

	}

	@Override
	// Method that sets the points earned based if the answer entered by the user is
	// equal to the correct answer of the question
	public double grade(String answer) {
		if (answer == String.valueOf(getAnswer())) {
			return getPoint();
		} else {
			return 0;
		}
	}

	@Override
	// Method that displays the correct answer of the question
	public String getCorrectAnswer() {
		return String.valueOf(answer);
	}

	@Override
	// Method that display the question + points and also, every option
	// of each question with an alphabetic index
	public void displayAnswers() {
		System.out.println(getqText() + "(" + getPoint() + " Points" + ")");
	}
}
