package test.datahandlers;

import java.util.List;

import test.actions.Action;
import test.actions.ActionResult;
import test.actions.Variable;

public interface ActionDataHanlder {
	ActionResult handleActionData(List<Variable> testVariables, Action action);
}
