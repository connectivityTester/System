package utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.actions.Parameter;
import test.actions.Variable;

public interface Utils {
	
	public static boolean setValueToVariable(final List<Variable> variables, String patternstring, final String inputString){
		String [] splitParts = patternstring.split("#");
		for(Variable variable : variables){
			patternstring = patternstring.replaceAll("#"+variable.getName()+"#", "(.*)");
		}
		Pattern datePatt = Pattern.compile(patternstring);
		Matcher m = datePatt.matcher(inputString);
		if (m.matches()) {
			int groupIndex = 1;
			for(int k = 1; k < splitParts.length; k+=2){
				for(Variable variable : variables){
					if(variable.getName().equals(splitParts[k])){
						variable.setValue(m.group(groupIndex));
						groupIndex++;
						break;
					}
				}
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	public static List<String> prepareListParameterValues(final List<Parameter> parameters, 
															final List<Variable> testVariables)
	{
		utils.Utils.requireNonNull(parameters, testVariables);
		List<String> listParams = new LinkedList<String>();
		for(Parameter param : parameters){
			listParams.add(prepareSingleParameterValue(param, testVariables));
		}
		return listParams;
	}
	
	public static String prepareSingleParameterValue(final Parameter parameter, final List<Variable> testVariables){
		utils.Utils.requireNonNull(parameter, testVariables);
		if(parameter.isVariable()){
			for(Variable var: testVariables){
				if(var.getName().equals(parameter.getValue())){
					return var.getValue();
				}
			}
		}
		return parameter.getValue();
	}
	
	public static Parameter getSingleParameterByName(List<Parameter> parameters, String parameterName){
		utils.Utils.requireNonNull(parameters, parameterName);
		
		for(Parameter parameter : parameters){
			if(parameter.getName().equals(parameterName)){
				return parameter;
			}
		}
		return null;
	}
	
	public static List<Parameter> getListParameterValuesByName(final List<Parameter> parameters, final String parameterName){
		utils.Utils.requireNonNull(parameters, parameterName);
		
		List<Parameter> params = new LinkedList<Parameter>();
		for(Parameter parameter : parameters){
			if(parameter.getName().equals(parameterName)){
				params.add(parameter);
			}
		}
		return params;
	}

	public static Variable getVariableByName(final String variableName, final List<Variable> testVariables){
		for(Variable variable : testVariables){
			if(variable.getName().equals(variableName)){
				return variable;
			}
		}
		return null;
	}
	
	public static void requireNonNull(final Object... objectsToCheck){
		Arrays.asList(objectsToCheck).forEach(obj -> Objects.requireNonNull(obj));
	}
}
