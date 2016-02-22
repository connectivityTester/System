package types;

public enum FailReaction {
	SKIP("skip"),
	STOP("stop");
	
	private String reaction;
	
	private FailReaction(String failReactionString){
		this.reaction = failReactionString;
	}
	
	public static FailReaction defineFailReaction(String reactionString){
		for(FailReaction failReaction : FailReaction.values()){
			if(failReaction.reaction.toLowerCase().equals(reactionString)){
				return failReaction;
			}
		}
		return STOP;
	}
}
