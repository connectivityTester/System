package test;

import java.util.List;

public interface ActionDataHanlder {
	ActionResult handleActionData(List<Variable> testVariables, Action action);
}
