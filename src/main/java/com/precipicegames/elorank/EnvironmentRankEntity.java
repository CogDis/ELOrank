package com.precipicegames.elorank;

public class EnvironmentRankEntity extends RankEntity {
	public static double kFactor = 7;
	protected void save() {
		// Do nothing!

	}
	protected double calculate(double comparedrate, double result) {
		double mer = getRank();
		double delta = mer - comparedrate;
		double chance = 1.0/(1.0 +Math.pow(10, delta/400.0));
		double ratedelta = RankEntity.kFactor*(result - chance);
		if(ratedelta > kFactor)
			ratedelta = kFactor;
		if(ratedelta < -kFactor)
			ratedelta = -kFactor;
		return mer + ratedelta;
	}


}
