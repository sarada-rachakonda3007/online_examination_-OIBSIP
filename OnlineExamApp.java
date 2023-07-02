import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

class LoginGUI extends JFrame implements ActionListener {
    JButton submitButton;
    JPanel panel;
    JLabel usernameLabel, passwordLabel;
    final JTextField usernameField, passwordField;

    LoginGUI() {
        usernameLabel = new JLabel();
        usernameLabel.setText("Username:");
        usernameField = new JTextField(15);
        passwordLabel = new JLabel();
        passwordLabel.setText("Password:");
        passwordField = new JPasswordField(8);
        submitButton = new JButton("Login");
        panel = new JPanel(new GridLayout(3, 1));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(submitButton);
        add(panel, BorderLayout.CENTER);
        submitButton.addActionListener(this);
        setTitle("Login Form");
    }

    public void actionPerformed(ActionEvent ae) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (!password.equals("")) {
            new OnlineExam(username);
        } else {
            passwordField.setText("Enter Password");
            actionPerformed(ae);
        }
    }
}

class OnlineExam extends JFrame implements ActionListener {
    JLabel questionLabel;
    JRadioButton[] options;
    JButton nextButton, saveButton, resultButton;
    ButtonGroup optionGroup;
    int count = 0, current = 0;
    String username;

    OnlineExam(String username) {
        super("Online Exam");
        this.username = username;

        questionLabel = new JLabel();
        add(questionLabel);

        options = new JRadioButton[4];
        optionGroup = new ButtonGroup();
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1)); // Use GridLayout for options
        for (int i = 0; i < options.length; i++) {
            options[i] = new JRadioButton();
            optionsPanel.add(options[i]);
            optionGroup.add(options[i]);
        }

        nextButton = new JButton("Next");
        saveButton = new JButton("Save for Later");
        resultButton = new JButton("Result");
        nextButton.addActionListener(this);
        saveButton.addActionListener(this);
        resultButton.addActionListener(this);

        setQuestion();

        JPanel buttonPanel = new JPanel(new FlowLayout()); // Use FlowLayout for buttons
        buttonPanel.add(nextButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(resultButton);

        setLayout(new BorderLayout());
        add(questionLabel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(250, 100);
        setVisible(true);
        pack();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            if (checkAnswer())
                count++;
            current++;
            setQuestion();
            if (current == 9) {
                nextButton.setEnabled(false);
                resultButton.setText("Result");
            }
        } else if (e.getSource() == saveButton) {
            JButton reviewButton = new JButton("Review");
            reviewButton.addActionListener(this);
            add(reviewButton);
            current++;
            setQuestion();
            if (current == 9)
                saveButton.setText("Result");
            setVisible(false);
            setVisible(true);
        } else if (e.getSource() instanceof JButton && ((JButton) e.getSource()).getText().equals("Review")) {
            if (checkAnswer())
                count++;
            int reviewIndex = Integer.parseInt(((JButton) e.getSource()).getText().substring(7));
            current = reviewIndex - 1;
            setQuestion();
            ((JButton) e.getSource()).setEnabled(false);
        } else if (e.getSource() == resultButton) {
            if (checkAnswer())
                count++;
            current++;
            JOptionPane.showMessageDialog(this, "Score: " + count);
            System.exit(0);
        }
    }

    void setQuestion() {
        questionLabel.setText("Question " + (current + 1) + ": " + getQuestion(current));
        String[] optionsArray = getOptions(current);
        for (int i = 0; i < options.length; i++) {
            options[i].setText(optionsArray[i]);
        }
    }

    boolean checkAnswer() {
        int correctOption = getCorrectOption(current);
        return options[correctOption].isSelected();
    }

    String getQuestion(int index) {
        String[] questions = {
                "Who is the father of Java?",
                "Number of primitive data types in Java are?",
                "Where is the System class defined?",
                "Expected created by try block is caught in which block?",
                "Which of the following is not an OOP concept in Java?",
                "Identify the infinite loop.",
                "When is the finalize() method called?",
                "What is the implicit return type of a constructor?",
                "The class at the top of the exception class hierarchy is?",
                "Which provides the runtime environment for Java bytecode to be executed?"
        };
        return questions[index];
    }

    String[] getOptions(int index) {
        String[][] options = {
                {"Charles Babbage", "James Gosling", "M.P. Java", "Blais Pascal"},
                {"6", "7", "8", "9"},
                {"java.lang package", "java.util package", "java.lo package", "None"},
                {"catch", "throw", "final", "thrown"},
                {"Polymorphism", "Inheritance", "Compilation", "Encapsulation"},
                {"for(;;)", "for() i = 0; j < 1; i--", "for(int = 0; i++)", "if(All of the above)"},
                {"Before garbage collection", "Before an object goes out of scope", "Before a variable goes out of scope", "None"},
                {"No return type", "A class object in which it is defined", "void", "None"},
                {"ArithmeticException", "Throwable", "Object", "Console"},
                {"JDK", "JVM", "JRE", "JAVAC"}
        };
        return options[index];
    }

    int getCorrectOption(int index) {
        int[] correctOptions = {1, 0, 0, 0, 2, 3, 1, 0, 1, 1};
        return correctOptions[index];
    }
}

class OnlineExamApp {
    public static void main(String[] args) {
        try {
            LoginGUI loginForm = new LoginGUI();
            loginForm.setSize(500, 250);
            loginForm.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
