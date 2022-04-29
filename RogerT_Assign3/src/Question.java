//Create abstract class Question
public abstract class Question {

	// Instance variable
	private String qText;
	private double point;

	// Constructors
	// Default constructor
	public Question() {

	}

	// Constructor with 1 parameter
	public Question(String qText) {
		this.qText = qText;
	}

	// Instance methods (getters and setters)
	public String getqText() {
		return qText;
	}

	public void setqText(String qText) {
		this.qText = qText;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	// Abstract methods
	public abstract double grade(String answer);

	public abstract String getCorrectAnswer();

	public abstract void displayAnswers();

}
