package hmc_dis2.androidtracesource.commands;

import org.json.JSONException;
import org.json.JSONObject;

import hmc_dis2.androidtracesource.types.CommandResultTypes;

public class CommandResult {

    private CommandResultTypes type;
    private String answerData;
    private int deviceSourceId = -1;


    public CommandResult( CommandResultTypes resultType, String answerData){
        this.type = resultType;
        this.answerData = answerData;
    }

    public String getJSONString (){
        JSONObject result = new JSONObject();
        try {
            result.put("id", this.deviceSourceId+"");
        } catch (JSONException e) {

        }
        try {
            result.put("type", "TEXT");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(this.answerData == null){
            try {
                result.put("data", "Command was executed successfully");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                result.put("data", this.answerData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result.toString()+"\n";
    }

    public CommandResultTypes getType()  { return this.type;        }
    public String getErrorReason()       { return this.answerData; }
    public void setDeviceSourceId(int id){ this.deviceSourceId = id;}
}
