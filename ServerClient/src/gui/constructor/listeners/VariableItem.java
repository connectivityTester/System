package gui.constructor.listeners;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import test.actions.Variable;

@SuppressWarnings("serial")
public class VariableItem extends JLabel{

	private final QuestionTextField variableName = new QuestionTextField("Name");
	private final QuestionTextField variableValue = new QuestionTextField("Value");
	private Variable variable = new Variable("", "");
	
	public VariableItem(){
		this.showDialog();
	}
	
	public String toString() throws NullPointerException{
		return "Name: " + this.variable.getName() + " Value: " + this.variable.getValue();
	}
	
	public void showDialog(){
		Object fieldsArray[] = {this.variableName, this.variableValue};
		int result = -1;
		do{
			result = JOptionPane.showConfirmDialog(null, fieldsArray, "Value description", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result == 0){
				if(this.variableName.getText().isEmpty() || this.variableValue.getText().isEmpty()){
					JOptionPane.showConfirmDialog(null, "Name and value can't be empty!", "Wrong input", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				}
				else{
					this.variable = new Variable(this.variableName.getText(), this.variableValue.getText());					
				}
			}
			else{
				return;
			}
		}
		while(this.variableName.getText().isEmpty() || this.variableValue.getText().isEmpty());
	}
	
	public Variable getVariable(){
		return this.variable;
	}
}
