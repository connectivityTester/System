package execution;

import java.util.List;

import test.Action;
import test.ActionResult;
import test.Variable;

public interface ActionDataHanlder {
	ActionResult handleActionData(List<Variable> testVariables, Action action);
}
