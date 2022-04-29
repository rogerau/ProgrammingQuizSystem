import java.util.ArrayList;

//Create MCQuestion subclass that extends Question class

public class MCQuestion extends Question {
	// instance variable
	private String answer; // saves the correct answer
	private ArrayList<String> options = new ArrayList<String>();

	// Constructors
	// Default constructor
	public MCQuestion() {

	}

	// Constructor with 3 parameters
	public MCQuestion(String qText, String options, double point) {
		setqText(qText);
		setOptions(options);
		setPoint(point);
	}

	// Instance methods (getters and setters)

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public ArrayList<String> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<String> options) {
		this.options = options;
	}

	// Overload setOptions method
	public void setOptions(String options) {
		// Divide each option of every question object
		// send by the constructor into an array (criteria ##)
		String[] tempArray = options.split("##");
		// Define an array of letters options to assign each letter
		// to every option of the question
		String[] optionInLetters = { "A", "B", "C", "D", "E" };
		int counter = 0;
		// Loop to add each option of the question into the options array
		// For the correct option do not consider *
		for (int i = 0; i < tempArray.length; i++) {
			char c = tempArray[i].charAt(0);
			if (c == '*') {
				// Add the correct option into the options array
				this.options.add(tempArray[i].substring(1, tempArray[i].length()));
				// Send the equivalent letter as answer
				setAnswer(optionInLetters[counter]);
			} else {
				// Add each option (no correct option) into the options array
				this.options.add(tempArray[i]);
			}
			counter++;
		}
	}

	@Override
	// Method that sets the points earned based if the answer entered by the user is
	// equal to the correct answer of the question
	public double grade(String answer) {
		if (answer.equals(getAnswer())) {
			return getPoint();
		} else {
			return 0;
		}
	}

	@Override
	// Method that displays the correct answer of the question
	public String getCorrectAnswer() {
		return answer;
	}

	@Override
	// Method that display the question + points and also, every option
	// of each question with an alphabetic index
	public void displayAnswers() {
		int counter = 0;
		System.out.println(getqText() + "(" + getPoint() + " Points" + ")");
		for (char i = 'A'; i < 'Z'; i++) {
			if (counter < options.size()) {
				System.out.printf("%c: ", i);
				System.out.println(options.get(counter));
			}
			counter++;
		}
	}

}
