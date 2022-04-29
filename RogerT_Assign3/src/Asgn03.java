import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

//Create assignment 3 class
public class Asgn03 {

	// Define a scanner variable as static for all the program
	static Scanner input = new Scanner(System.in);
	// Define variables of connection, statement and resultSet for all the program
	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;

	// Define the driver as a static constant
	private static final String JDBC_DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";

	// Define the DB Location as a static constant
	private static final String MSA_FILE = "./Question.accdb";
	// Define the Protocol MS ACCESS as a static constant
	private static final String DB_URL = "jdbc:ucanaccess://" + MSA_FILE;

	public static void main(String[] args) {
		// Define a boolean variable to maintain the system in a loop
		Boolean again = true;
		// Display instructions
		displayInstructions();
		// Display user options
		displayOptions();
		// Validate user answers to the options of the system
		String validatedAnswer = validationAnswer(input.nextLine());

		// Define a loop to maintain the system running until the user decides to exit
		// the system
		while (again) {
			// Depending on what the user answers, execute the proper option
			if (validatedAnswer.equals("c")) {
				// Ask the user for the type of question
				System.out.print("Enter the type of question (MC or TF) >> ");
				String typeOfQuestion = input.nextLine();
				// Validate the user answer to the type of question
				validationAnswer(typeOfQuestion, 2);
				// Depending on what the user answers (about the type of question)
				// execute the proper action
				if (typeOfQuestion.equals("MC")) {
					// Ask user to enter the MC questions, number of options, and answers for
					// each question
					// Insert the questions entered into the DB
					mcqQuestion();
					// Display again user options
					displayOptions();
					// Validate the user answer to the options of the system
					// Allows the user to maintain in the program
					validatedAnswer = validationAnswer(input.nextLine());
				} else {
					// Ask user to enter the TF questions, is it true or false?, and answers for
					// each question
					// Insert the questions entered into the DB
					tfQuestion();
					// Display again user options
					displayOptions();
					// Validate the user answer to the options of the system
					// Allows the user to maintain in the program
					validatedAnswer = validationAnswer(input.nextLine());
				}
			} else if (validatedAnswer.equals("p")) {
				// Display all the questions entered previously (MC and/or TF)
				// Ask for the user answer to each question
				// Indicate if the user answer is wrong or correct and display the points
				// achieved.
				// Once the quiz is done or all the questions are done, display the total score
				// achieved.
				displayQuestions();
				// Display again user options
				displayOptions();
				// Validate the user answer to the options of the system
				// Allows the user to maintain in the program
				validatedAnswer = validationAnswer(input.nextLine());
			} else {
				// Before the user exits the system, delete all the records in the DB to allow
				// enter new
				// records if the program is run again
				deleteRecordsDB();
				// Exit the loop
				again = false;
				// Display exit message
				System.out.print("Goodbye!");
			}

		}

	}

	// Method to load the driver, create and set up connection
	public static void initDB() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(DB_URL);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Method to close every step to access the DB
	public static void closeDB() {
		try {
			// Close each step
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Method for writing MCQ questions entered by the user into the DB
	public static void mcqQuestion() { // write the mcqQuestions
		// Ask for questions to the user
		System.out.print("Enter the question text >> ");
		String question = input.nextLine();

		// Ask number of options to the user
		System.out.print("How many options? >> ");
		int numOptions = input.nextInt();
		input.nextLine();

		// Define an Array to hold each options for a particular question
		ArrayList<String> answerToOptionsList = new ArrayList<String>();
		String answerToOption;
		int counter = 1;
		// Ask the user to enter each option of the question and add each one to the
		// array list.
		for (char i = 'A'; i < 'Z'; i++) {
			if (counter <= numOptions) {
				System.out.printf("Enter Option %c (Start with * for correct answer) >> ", i);
				answerToOption = input.nextLine();
				answerToOptionsList.add(answerToOption);
			}
			counter++;
		}

		// Convert the array list in a string variable that holds all the
		// options, each one separated by ## and the correct answer starts with *
		String allOptions = "";
		for (int i = 0; i < answerToOptionsList.size(); i++) {
			if (i == answerToOptionsList.size() - 1) {
				allOptions += answerToOptionsList.get(i);
			} else {
				allOptions += answerToOptionsList.get(i) + "##";
			}
		}

		// Ask for the points for that question
		System.out.print("How many points? >> ");
		double totalPoints = input.nextDouble();
		input.nextLine();

		// Call the method to load the driver, get connection, create statement
		initDB();
		// Insert record to the DB
		String insertQuery = "INSERT INTO QUESTIONS (QTEXT, ANSWER, POINT, TYPE) VALUES (" + "'" + question + "'" + ","
				+ "'" + allOptions + "'" + "," + totalPoints + "," + "'" + "MC" + "'" + ")";
		try {

			statement.executeUpdate(insertQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Call the method to close connection of the DB
		closeDB();

	}

	// Method for writing TF questions entered by the user into the DB

	public static void tfQuestion() {
		// Ask for the questions to the user
		System.out.print("Enter the question text >> ");
		String question = input.nextLine();

		// Ask if the question is true or false
		System.out.print("Answer is True or False? >> ");
		String answer = input.nextLine();

		// Ask number of points for the question
		System.out.print("How many points? >> ");
		double totalPoints = input.nextDouble();
		input.nextLine();

		// Call the method to load the driver, get connection, create statement
		initDB();

		// Insert record to the DB
		String insertQuery = "INSERT INTO QUESTIONS (QTEXT, ANSWER, POINT, TYPE) VALUES (" + "'" + question + "'" + ","
				+ answer + "," + totalPoints + "," + "'" + "TF" + "'" + ")";
		try {
			statement.executeUpdate(insertQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Call the method to close connection of the DB
		closeDB();
	}

	// Method to delete records of the DB
	public static void deleteRecordsDB() {
		// Call the method to load the driver, get connection, create statement
		initDB();
		// Define delete query and send it to the DB
		String deleteRecordsQuery = "DELETE FROM QUESTIONS";
		try {
			statement.executeUpdate(deleteRecordsQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Call the method to close connection of the DB
		closeDB();
	}

	// Method to display each question with their particular options, ask user
	// answer, validate user answer
	// and display total points earned
	public static void displayQuestions() { // read the DB
		// Create a question list
		ArrayList<Question> questionList = new ArrayList<Question>();
		// Call the method to read each question from the DB
		readDB(questionList);
		String userAnswer = "";
		double accScores = 0;
		// Display each question with their options
		for (Question q : questionList) {
			q.displayAnswers();
			// Separate the type of user input expected by the type of object created
			if (q instanceof MCQuestion) {
				// Ask the user to enter their choice
				System.out.print("Enter your choice >> ");
				userAnswer = input.nextLine();
				// Validate user choice with the correct answer, if is it validated
				// assign points for that question, if not, do not assign points
				// and display which is the correct answer
				if (userAnswer.equals(q.getCorrectAnswer())) {
					System.out.println("You are correct!");
					accScores += q.grade(userAnswer);
				} else {
					System.out.println("You are wrong. The correct answer is " + q.getCorrectAnswer() + ".");
				}
			} else {
				// Ask the user to enter their choice
				System.out.print("True(T) or False(F) >> ");
				userAnswer = input.nextLine();
				// Convert user answer into proper string
				if (userAnswer.equals("T")) {
					userAnswer = "true";
				} else {
					userAnswer = "false";
				}
				// Validate user choice with the correct answer, if is it validated
				// assign points for that question, if not, do not assign points
				// and display which is the correct answer
				if (userAnswer.equals(q.getCorrectAnswer())) {
					System.out.println("You are correct!");
					accScores += q.grade(userAnswer);
				} else {
					System.out.println("You are wrong. The correct answer is " + q.getCorrectAnswer() + ".");
				}
			}

		}
		// Display total points earned by the user

		System.out.println("The quiz ends. Your score is " + String.format("%.1f", accScores));

	}

	// Method to read each record from the DB, create a object from each record and
	// add it to a question list
	public static void readDB(ArrayList<Question> anyList) {
		anyList.clear();
		// Call method to load the driver, initiate connection and create statement
		initDB();
		// Create read query and send it to the DB
		String readQUERY = "SELECT * FROM QUESTIONS";
		try {
			resultSet = statement.executeQuery(readQUERY);
			// Read each record from the DB, store each field in a particular variable
			// and with each variable instantiate every object
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String qText = resultSet.getString(2);
				String answer = resultSet.getString(3);
				double point = resultSet.getDouble(4);
				String type = resultSet.getString(5);
				Question aQuestion = null;
				// Depending of the type of question assign the correct values into the
				// instantiation, then add each object to the list
				if (type.equals("MC")) {
					aQuestion = new MCQuestion(qText, answer, point);
					anyList.add(aQuestion);
				} else {
					if (answer.equalsIgnoreCase("true")) {
						aQuestion = new TFQuestion(qText, true, point);
					} else {
						aQuestion = new TFQuestion(qText, false, point);
					}
					anyList.add(aQuestion);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Call the method to close connection
		closeDB();
	}

	// Overloaded method
	// Method to validate user answer between Yes or No
	public static void validationAnswer(String answer, int numOfValidation, String n) {
		while (!answer.equals("Y") && !answer.equals("N")) {
			System.out.print("Wrong answer!. Please select the correct answer (Y or N) >> ");
			answer = input.nextLine();
		}
	}

	// Overloaded method
	// Method to validate user answer between TC or MCQ questions
	public static void validationAnswer(String answer, int numOfValidation) {
		while (!answer.equals("MC") && !answer.equals("TF")) {
			System.out.print("Wrong answer!. Please select the correct answer (MC or TF) >> ");
			answer = input.nextLine();
		}
	}

	// Method to validate user answer between create question, preview or exit
	public static String validationAnswer(String answer) {
		while (!answer.equals("c") && !answer.equals("p") && !answer.equals("e")) {
			System.out.print("Wrong answer!. Please select the correct answer (c, p or e) >> ");
			answer = input.nextLine();
		}
		return answer;
	}

	// Method to display options for the user
	public static void displayOptions() {
		System.out.print("Please choose (c)reate a question, (p)review or (e)xit >> ");
	}

	// Method to display general information of the program
	public static void displayInstructions() {
		System.out.print("The following program allows customers to access a quiz system (MCQ/TF) " + "\n"
				+ "The program will give 3 options to the customer which are: " + "\n"
				+ "i) Create a question, ii) Preview the quiz, and iii) Exit " + "\n"
				+ "The program will require the following inputs from the customer: " + "\n" + "a. Type of question"
				+ "\n" + "b. Number of options per question" + "\n" + "c. Total points per question");
		System.out.println("\n");
	}

}
