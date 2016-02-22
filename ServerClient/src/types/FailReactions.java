package types;

public enum FailReactions {
	SKIP("skip"),
	STOP("stop");
	
	private String reaction;
	
	private FailReactions(String reaction) {
		this.reaction = reaction;
	} 
	
	public static FailReactions defineFailReaction(String reaction){
		for(FailReactions reac : FailReactions.values()){
			if(reac.reaction.equals(reaction.toLowerCase())){
				return reac;
			}
		}
		return FailReactions.STOP;
	}
}
